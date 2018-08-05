// GruntFile for building the final compiled files from the core.
// Run using NodeJS and the Grunt module
var fs = require("fs");
var dirs = {
	core: "src/sys",
	i18n: "src/i18n",
	build: "build"
};
var getI18NFiles = function () {
	return fs.readdirSync(dirs.i18n);
};

var buildMinifyFileList = function (dev) {
	var output_path = dev ? "" : "production/";
	var output_ext = dev ? "." : ".min.";
	var files = getI18NFiles();
	var output = {};
	files.map(function(item){
		var file_core_name = "date-" + item.replace(".js", "");
		var dest = dirs.build + "/"+output_path + file_core_name + output_ext + "js";
		output[dest] = [dirs.build + "/" + file_core_name + ".js"];
		return dest;
	});
	output[dirs.build + "/"+output_path + "date"+output_ext+"js"] = [dirs.build + "/" + "date.js"];
	return output;
};

var banner = "/** \n" +
			" * @overview <%= pkg.name %>\n" +
			" * @version <%= pkg.version %>\n" +
			" * @author <%= pkg.author.name %> <<%= pkg.author.email %>>\n" +
			" * @copyright <%= grunt.template.today('yyyy') %> <%= pkg.author.name %>\n" +
			" * @license <%= pkg.license %>\n" +
			" * @homepage <%= pkg.homepage %>\n" +
			" */";

module.exports = function(grunt) {
	// Project configuration.
	grunt.initConfig({
		pkg: grunt.file.readJSON("package.json"),
		dirs: dirs,
		build_dev: {
			description: "Builds files designed for easy debugging on dev enviroments (non-minified)"
		},
		build_prod: {
			description: "Builds production ready files (minified)"
		},
		closurecompiler: {
			minify: {
				files: buildMinifyFileList(),
				options: {
					"compilation_level": "SIMPLE_OPTIMIZATIONS",
					"max_processes": 5,
					"banner": banner
				}
			}
		},
		concat: {
			options: {
				separator: "\n",
				banner: banner,
				nonull: true
			},
			core: {
				src: [
					"<%= dirs.sys %>/i18n.js",
					"<%= dirs.sys %>/sys.js",
					"<%= dirs.sys %>/sys-prototypes.js",
					"<%= dirs.sys %>/sugarpak.js",
					"<%= dirs.sys %>/format_parser.js",
					"<%= dirs.sys %>/parsing_operators.js",
					"<%= dirs.sys %>/parsing_translator.js",
					"<%= dirs.sys %>/parsing_grammar.js",
					"<%= dirs.sys %>/parser.js",
					"<%= dirs.sys %>/extras.js",
					"<%= dirs.sys %>/time_span.js",
					"<%= dirs.sys %>/time_period.js"
				],
				dest: "<%= dirs.build %>/date-sys.js"
			},
			basic: {
				src: [
					"<%= dirs.sys %>/i18n.js",
					"<%= dirs.sys %>/sys.js",
					"<%= dirs.sys %>/sys-prototypes.js",
					"<%= dirs.sys %>/sugarpak.js",
					"<%= dirs.sys %>/format_parser.js",
					"<%= dirs.sys %>/parsing_operators.js",
					"<%= dirs.sys %>/parsing_translator.js",
					"<%= dirs.sys %>/parsing_grammar.js",
					"<%= dirs.sys %>/parser.js",
					"<%= dirs.sys %>/extras.js",
					"<%= dirs.sys %>/time_span.js",
					"<%= dirs.sys %>/time_period.js"
				],
				dest: "<%= dirs.build %>/date.js"
			}
		},
		i18n: {
			core: {
				core: "<%= dirs.build %>/date-sys.js",
				src: ["<%= dirs.i18n %>/*.js"],
				dest: "<%= dirs.build %>/"   // destination *directory*, probably better than specifying same file names twice
			}
		},
		shell: {
			updateCodeClimate: {
				command: "codeclimate < reports/lcov.info",
				options: {
					stdout: true,
					stderr: true,
					failOnError: true
				}
			}
		},
		jasmine : {
			src : [
				"src/sys/i18n.js",
				"src/sys/sys.js",
				"src/sys/sys-prototypes.js",
				"src/sys/sugarpak.js",
				"src/sys/format_parser.js",
				"src/sys/parsing_operators.js",
				"src/sys/parsing_translator.js",
				"src/sys/parsing_grammar.js",
				"src/sys/parser.js",
				"src/sys/extras.js",
				"src/sys/time_period.js",
				"src/sys/time_span.js"
			],
			options : {
				specs : "specs/*-spec.js",
				template : require("grunt-template-jasmine-istanbul"),
				templateOptions: {
					template: "specs/jasmine-2.0.3/specrunner.tmpl",
					coverage: "reports/coverage.json",
					report: {
						type: "lcov",
						options: {
							replace: true,
							dir: "reports/"
						}
					}
				}
			}
		},

	});

	grunt.registerMultiTask("i18n", "Wraps DateJS sys with Internationalization info.", function() {
		var data = this.data,
			path = require("path"),
			dest = grunt.template.process(data.dest),
			files = grunt.file.expand(data.src),
			core = grunt.file.read(grunt.template.process(data.core)),
			sep = grunt.util.linefeed,
			banner_compiled = grunt.template.process(banner);

		files.forEach(function(f) {
			var p = dest + "/" + "date-" + path.basename(f),
				contents = grunt.file.read(f);

			grunt.file.write(p, banner_compiled + sep + contents + sep + core );
			grunt.log.writeln("File \"" + p + "\" created.");
		});
		grunt.file.delete(dirs.build+"/date-sys.js");
	});
	grunt.registerMultiTask("build_dev", "Builds compiled, non-minfied, files for development enviroments", function() {
		grunt.task.run(["concat:sys", "concat:basic", "i18n:sys"]);
	});
	grunt.registerMultiTask("build_prod", "Rebuilds dev and minifies files for production enviroments", function() {
		grunt.task.run(["concat:sys", "concat:basic", "i18n:sys", "closurecompiler:minify"]);
	});

	grunt.loadNpmTasks("grunt-contrib-jasmine");

	// now set the default
	grunt.registerTask("default", ["build_dev"]);
	// Load the plugin that provides the "minify" task.
	grunt.loadNpmTasks("grunt-shell");
	grunt.loadNpmTasks("grunt-closurecompiler");
	grunt.loadNpmTasks("grunt-contrib-concat");
	grunt.registerTask("test", ["jasmine", "shell:updateCodeClimate"]);
};