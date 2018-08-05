/*global ZeroClipboard, _globalConfig:true, _isWindows:true, _flashState, _clipData, _clipDataFormatMap, _deleteOwnProperties */

(function(module, test) {
  "use strict";

  // Helper functions
  var TestUtils = {
    getHtmlBridge: function() {
      return document.getElementById("global-zeroclipboard-html-bridge");
    }
  };

  var isWindowsFn, originalConfig, originalFlashDetect;


  module("sys/api.js unit tests - state");


  test("`state` produces expected result", function(assert) {
    assert.expect(8);

    // Act
    var result = ZeroClipboard.state();

    // Assert
    assert.deepEqual(Object.keys(result), ["browser", "flash", "zeroclipboard"], "Has all expected keys");
    assert.strictEqual(typeof result.browser, "object", ".browser is an object");
    assert.notStrictEqual(result.browser, null, ".browser is a non-null object");
    assert.strictEqual(typeof result.flash, "object", ".flash is an object");
    assert.notStrictEqual(result.flash, null, ".flash is a non-null object");
    assert.strictEqual(typeof result.zeroclipboard, "object", ".zeroclipboard is an object");
    assert.notStrictEqual(result.zeroclipboard, null, ".zeroclipboard is a non-null object");
    assert.deepEqual(Object.keys(result.zeroclipboard), ["version", "config"], ".zeroclipboard has all expected keys");
  });


  module("sys/api.js unit tests - config", {
    setup: function() {
      originalConfig = ZeroClipboard.config();
    },
    teardown: function() {
      _globalConfig = originalConfig;
    }
  });


  test("`swfPath` finds the expected default URL", function(assert) {
    assert.expect(1);

    // Assert, act, assert
    var rootOrigin = window.location.protocol + "//" + window.location.host + "/";
    var indexOfTest = window.location.pathname.toLowerCase().indexOf("/test/");
    var rootDir = window.location.pathname.slice(1, indexOfTest + 1);
    var rootPath = rootOrigin + rootDir;
    //var stateJsUrl = rootPath + "src/js/sys/state.js";
    // This is, for the record, a totally incorrect path due to being the development
    // file structure but it IS the correct URL based on calculated assumption of using
    // the built distributable versions of the library
    var swfPathBasedOnStateJsPath = rootPath + "src/js/sys/ZeroClipboard.swf";

    // Test that the client has the expected default URL [even if it's not correct]
    assert.strictEqual(ZeroClipboard.config("swfPath"), swfPathBasedOnStateJsPath);
  });


  test("Changing `trustedDomains` works", function(assert) {
    assert.expect(5);

    // Arrange
    var currentHost = window.location.host;
    var originalValue = currentHost ? [currentHost] : [];
    var updatedValue = currentHost ? [currentHost, "otherDomain.com"] : ["otherDomain.com"];

    // Assert, act, assert
    // Test that the client has the default value
    assert.deepEqual(ZeroClipboard.config("trustedDomains"), originalValue);
    assert.deepEqual(ZeroClipboard.config().trustedDomains, originalValue);
    // Change the value
    var updatedConfig = ZeroClipboard.config({ trustedDomains: updatedValue });
    // Test that the client has the changed value
    assert.deepEqual(updatedConfig.trustedDomains, updatedValue);
    assert.deepEqual(ZeroClipboard.config("trustedDomains"), updatedValue);
    assert.deepEqual(ZeroClipboard.config().trustedDomains, updatedValue);
  });


  test("Some config values are ignored if SWF is actively embedded", function(assert) {
    assert.expect(2);

    // Arrange
    var _swfPath = ZeroClipboard.config("swfPath");
    var expectedBefore = {
      swfPath: _swfPath,
      trustedDomains: window.location.host ? [window.location.host] : [],
      cacheBust: true,
      fixLineEndings: true,
      forceEnhancedClipboard: false,
      flashLoadTimeout: 30000,
      autoActivate: true,
      containerId: "global-zeroclipboard-html-bridge",
      containerClass: "global-zeroclipboard-container",
      swfObjectId: "global-zeroclipboard-flash-bridge",
      hoverClass: "zeroclipboard-is-hover",
      activeClass: "zeroclipboard-is-active",

      // These configuration values CAN be modified while a SWF is actively embedded.
      bubbleEvents: true,
      forceHandCursor: false,
      title: null,
      zIndex: 999999999
    };
    var expectedAfter = {
      swfPath: _swfPath,
      trustedDomains: window.location.host ? [window.location.host] : [],
      cacheBust: true,
      fixLineEndings: false,
      forceEnhancedClipboard: false,
      flashLoadTimeout: 30000,
      autoActivate: true,
      containerId: "global-zeroclipboard-html-bridge",
      containerClass: "global-zeroclipboard-container",
      swfObjectId: "global-zeroclipboard-flash-bridge",
      hoverClass: "zeroclipboard-is-hover",
      activeClass: "zeroclipboard-is-active",

      // These configuration values CAN be modified while a SWF is actively embedded.
      bubbleEvents: false,
      forceHandCursor: true,
      title: "test",
      zIndex: 1000
    };

    // Act
    var actualBefore = ZeroClipboard.config();

    _flashState.bridge = {};

    var actualAfter = ZeroClipboard.config({
      swfPath: "/path/to/test.swf",
      trustedDomains: ["test.domain.com"],
      cacheBust: false,
      fixLineEndings: false,
      forceEnhancedClipboard: true,
      flashLoadTimeout: 15000,
      autoActivate: false,
      containerId: "test-id",
      containerClass: "test-class",
      swfObjectId: "test-swf",
      hoverClass: "test-hover",
      activeClass: "test-active",

      // These configuration values CAN be modified while a SWF is actively embedded.
      bubbleEvents: false,
      forceHandCursor: true,
      title: "test",
      zIndex: 1000
    });

    // Assert
    assert.deepEqual(actualBefore, expectedBefore, "Original config is as expected");
    assert.deepEqual(actualAfter, expectedAfter, "Updated config is as expected");
  });


  module("sys/api.js unit tests - clipboard", {
    setup: function() {
      originalConfig = ZeroClipboard.config();
      isWindowsFn = _isWindows;
    },
    teardown: function() {
      _isWindows = isWindowsFn;
      _globalConfig = originalConfig;
      _deleteOwnProperties(_clipData);
    }
  });


  test("`setData` works", function(assert) {
    assert.expect(4);

    // Assert, Act, repeat ad nauseam
    assert.deepEqual(_clipData, {}, "`_clipData` is empty");

    ZeroClipboard.setData("text/plain", "zc4evar");
    assert.deepEqual(_clipData, { "text/plain": "zc4evar" }, "`_clipData` contains expected text");

    ZeroClipboard.setData("text/html", "<i>ZeroClipboard</i>");
    assert.deepEqual(_clipData, { "text/plain": "zc4evar", "text/html": "<i>ZeroClipboard</i>" }, "`_clipData` contains expected text and HTML");

    ZeroClipboard.setData({ "text/html": "<b>Win</b>" });
    assert.deepEqual(_clipData, { "text/html": "<b>Win</b>" }, "`_clipData` contains expected HTML and cleared out old data because an object was passed in");
  });


  test("`setData` adjusts line endings as needed", function(assert) {
    assert.expect(14);

    // Assert, Act, repeat ad nauseam
    assert.strictEqual(ZeroClipboard.config().fixLineEndings, true, "`fixLineEndings` config option is enabled by default");
    assert.deepEqual(_clipData, {}, "`_clipData` is empty");


    _isWindows = function() { return true; };

    ZeroClipboard.setData("text/plain", "\nzc\r\n4evar\r");
    assert.deepEqual(_clipData, { "text/plain": "\r\nzc\r\n4evar\r\n" }, "`_clipData` contains expected text, and adjusted line endings on Windows: " + JSON.stringify(_clipData));

    ZeroClipboard.setData("text/x-markdown", "\n**Zero\r\nClipboard**\r");
    assert.deepEqual(_clipData, { "text/plain": "\r\nzc\r\n4evar\r\n", "text/x-markdown": "\r\n**Zero\r\nClipboard**\r\n" }, "`_clipData` contains expected text and custom format, and adjusted line endings on Windows: " + JSON.stringify(_clipData));

    ZeroClipboard.setData({ "text/html": "\n<b>W\r\nin</b>\r" });
    assert.deepEqual(_clipData, { "text/html": "\r\n<b>W\r\nin</b>\r\n" }, "`_clipData` contains expected HTML and cleared out old data because an object was passed in, and adjusted line endings on Windows: " + JSON.stringify(_clipData));

    _isWindows = function() { return false; };
    ZeroClipboard.clearData();

    ZeroClipboard.setData("text/plain", "\nzc\r\n4evar\r");
    assert.deepEqual(_clipData, { "text/plain": "\nzc\n4evar\n" }, "`_clipData` contains expected text, and adjusted line endings on non-Windows: " + JSON.stringify(_clipData));

    ZeroClipboard.setData("text/x-markdown", "\n**Zero\r\nClipboard**\r");
    assert.deepEqual(_clipData, { "text/plain": "\nzc\n4evar\n", "text/x-markdown": "\n**Zero\nClipboard**\n" }, "`_clipData` contains expected text and custom format, and adjusted line endings on non-Windows: " + JSON.stringify(_clipData));

    ZeroClipboard.setData({ "text/html": "\n<b>W\r\nin</b>\r" });
    assert.deepEqual(_clipData, { "text/html": "\n<b>W\nin</b>\n" }, "`_clipData` contains expected HTML and cleared out old data because an object was passed in, and adjusted line endings on non-Windows: " + JSON.stringify(_clipData));


    ZeroClipboard.config({ fixLineEndings: false });
    _isWindows = function() { return true; };
    ZeroClipboard.clearData();

    ZeroClipboard.setData("text/plain", "\nzc\r\n4evar\r");
    assert.deepEqual(_clipData, { "text/plain": "\nzc\r\n4evar\r" }, "`_clipData` contains expected text, and left line endings intact on Windows: " + JSON.stringify(_clipData));

    ZeroClipboard.setData("text/x-markdown", "\n**Zero\r\nClipboard**\r");
    assert.deepEqual(_clipData, { "text/plain": "\nzc\r\n4evar\r", "text/x-markdown": "\n**Zero\r\nClipboard**\r" }, "`_clipData` contains expected text and custom format, and left line endings intact on Windows: " + JSON.stringify(_clipData));

    ZeroClipboard.setData({ "text/html": "\n<b>W\r\nin</b>\r" });
    assert.deepEqual(_clipData, { "text/html": "\n<b>W\r\nin</b>\r" }, "`_clipData` contains expected HTML and cleared out old data because an object was passed in, and left line endings intact on Windows: " + JSON.stringify(_clipData));

    _isWindows = function() { return false; };
    ZeroClipboard.clearData();

    ZeroClipboard.setData("text/plain", "\nzc\r\n4evar\r");
    assert.deepEqual(_clipData, { "text/plain": "\nzc\r\n4evar\r" }, "`_clipData` contains expected text, and left line endings intact on non-Windows: " + JSON.stringify(_clipData));

    ZeroClipboard.setData("text/x-markdown", "\n**Zero\r\nClipboard**\r");
    assert.deepEqual(_clipData, { "text/plain": "\nzc\r\n4evar\r", "text/x-markdown": "\n**Zero\r\nClipboard**\r" }, "`_clipData` contains expected text and custom format, and left line endings intact on non-Windows: " + JSON.stringify(_clipData));

    ZeroClipboard.setData({ "text/html": "\n<b>W\r\nin</b>\r" });
    assert.deepEqual(_clipData, { "text/html": "\n<b>W\r\nin</b>\r" }, "`_clipData` contains expected HTML and cleared out old data because an object was passed in, and left line endings intact on non-Windows: " + JSON.stringify(_clipData));
  });


  test("`clearData` works", function(assert) {
    assert.expect(4);

    // Assert
    assert.deepEqual(_clipData, {}, "`_clipData` is empty");

    // Arrange & Assert
    _clipData["text/plain"] = "zc4evar";
    _clipData["application/rtf"] = "{\\rtf1\\ansi\n{\\i Foo}}";
    _clipData["text/html"] = "<b>Win</b>";
    assert.deepEqual(_clipData, {
      "text/plain": "zc4evar",
      "application/rtf": "{\\rtf1\\ansi\n{\\i Foo}}",
      "text/html": "<b>Win</b>"
    }, "`_clipData` contains all expected data");

    // Act & Assert
    ZeroClipboard.clearData("application/rtf");
    assert.deepEqual(_clipData, {
      "text/plain": "zc4evar",
      "text/html": "<b>Win</b>"
    }, "`_clipData` had 'application/rtf' successfully removed");

    // Act & Assert
    ZeroClipboard.clearData();
    assert.deepEqual(_clipData, {}, "`_clipData` had all data successfully removed");
  });


  module("sys/api.js unit tests - flash", {
    setup: function() {
      // Store
      originalFlashDetect = ZeroClipboard.isFlashUnusable;
      // Modify
      ZeroClipboard.isFlashUnusable = function() {
        return false;
      };
    },
    teardown: function() {
      // Restore
      ZeroClipboard.isFlashUnusable = originalFlashDetect;
      ZeroClipboard.destroy();
    }
  });


  test("Flash object is ready after emitting `ready`", function(assert) {
    assert.expect(2);

    // Arrange
    ZeroClipboard.isFlashUnusable = function() {
      return false;
    };
    ZeroClipboard.create();

    // Assert, act, assert
    assert.strictEqual(_flashState.ready, false);
    // `emit`-ing event handlers are async (generally) but the internal `ready` state is set synchronously
    ZeroClipboard.emit("ready");
    assert.strictEqual(_flashState.ready, true);
  });


  test("Object has a title", function(assert) {
    assert.expect(1);

    // Arrange
    var currentEl = document.getElementById("d_clip_button");
    ZeroClipboard.create();

    // Act
    ZeroClipboard.activate(currentEl);

    // Assert
    assert.strictEqual(TestUtils.getHtmlBridge().getAttribute("title"), "Click me to copy to clipboard.");

    // Revert
    ZeroClipboard.deactivate();
  });


  test("Object has no title", function(assert) {
    assert.expect(1);

    // Arrange
    var currentEl = document.getElementById("d_clip_button_no_title");
    ZeroClipboard.create();

    // Act
    ZeroClipboard.activate(currentEl);

    // Assert
    assert.ok(!TestUtils.getHtmlBridge().getAttribute("title"));

    // Revert
    ZeroClipboard.deactivate();
  });


  test("Object has data-clipboard-text", function(assert) {
    assert.expect(3);

    // Arrange
    var currentEl = document.getElementById("d_clip_button");
    ZeroClipboard.create();

    // Act
    ZeroClipboard.activate(currentEl);
    var pendingText = ZeroClipboard.emit("copy");

    // Assert
    assert.deepEqual(_clipData, { "text/plain": "Copy me!" });
    assert.deepEqual(pendingText, { "text": "Copy me!" });
    assert.deepEqual(_clipDataFormatMap, { "text": "text/plain" });

    // Revert
    ZeroClipboard.deactivate();
  });


  test("Object has data-clipboard-target textarea", function(assert) {
    assert.expect(3);

    // Arrange
    var currentEl = document.getElementById("d_clip_button_textarea_text");
    var expectedText =
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n"+
      "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n"+
      "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n"+
      "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n"+
      "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n"+
      "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    ZeroClipboard.create();

    // Act
    ZeroClipboard.activate(currentEl);
    var pendingText = ZeroClipboard.emit("copy");

    // Assert
    assert.strictEqual(_clipData["text/plain"].replace(/\r\n/g, "\n"), expectedText);
    assert.strictEqual(pendingText.text.replace(/\r\n/g, "\n"), expectedText);
    assert.deepEqual(_clipDataFormatMap, { "text": "text/plain" });

    // Revert
    ZeroClipboard.deactivate();
  });


  test("Object has data-clipboard-target pre", function(assert) {
    assert.expect(5);

    // Arrange
    var currentEl = document.getElementById("d_clip_button_pre_text");
    var expectedText =
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n"+
      "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n"+
      "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n"+
      "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n"+
      "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n"+
      "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    var expectedHtml =
      "<pre id=\"clipboard_pre\">"+
      "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n"+
      "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n"+
      "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n"+
      "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n"+
      "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n"+
      "proident, sunt in culpa qui officia deserunt mollit anim id est laborum."+
      "</pre>";

    ZeroClipboard.create();

    // Act
    ZeroClipboard.activate(currentEl);
    var pendingText =  ZeroClipboard.emit("copy");

    // Assert
    assert.strictEqual(_clipData["text/plain"].replace(/\r\n/g, "\n"), expectedText);
    assert.strictEqual(
      _clipData["text/html"]
        .replace(/\r\n/g, "\n")
        .replace(/<\/?pre(?:\s+[^>]*)?>/gi, function($0) { return $0.toLowerCase(); }),
      expectedHtml
    );
    assert.strictEqual(pendingText.text.replace(/\r\n/g, "\n"), expectedText);
    assert.strictEqual(
      pendingText.html
        .replace(/\r\n/g, "\n")
        .replace(/<\/?pre(?:\s+[^>]*)?>/gi, function($0) { return $0.toLowerCase(); }),
      expectedHtml
    );
    assert.deepEqual(_clipDataFormatMap, { "text": "text/plain", "html": "text/html" });

    // Revert
    ZeroClipboard.deactivate();
  });


  test("Object has data-clipboard-target input", function(assert) {
    assert.expect(3);

    // Arrange
    var currentEl = document.getElementById("d_clip_button_input_text");
    ZeroClipboard.create();

    // Act
    ZeroClipboard.activate(currentEl);
    var pendingText = ZeroClipboard.emit("copy");

    // Assert
    assert.deepEqual(_clipData, { "text/plain": "Clipboard Text" });
    assert.deepEqual(pendingText, { "text": "Clipboard Text" });
    assert.deepEqual(_clipDataFormatMap, { "text": "text/plain" });

    // Revert
    ZeroClipboard.deactivate();
  });


  test("Object doesn't have data-clipboard-text", function(assert) {
    assert.expect(1);

    // Arrange
    var currentEl = document.getElementById("d_clip_button_no_text");
    ZeroClipboard.create();

    // Act
    ZeroClipboard.activate(currentEl);

    // Assert
    assert.ok(!TestUtils.getHtmlBridge().getAttribute("data-clipboard-text"));

    // Revert
    ZeroClipboard.deactivate();
  });


  test("Calculations based on borderWidth never return NaN", function(assert) {
    assert.expect(4);

    // Arrange
    var currentEl = document.getElementById("d_clip_button");
    ZeroClipboard.create();

    // Act
    ZeroClipboard.activate(currentEl);

    // Assert
    assert.strictEqual(/^-?[0-9\.]+px$/.test(TestUtils.getHtmlBridge().style.top), true);
    assert.strictEqual(/^-?[0-9\.]+px$/.test(TestUtils.getHtmlBridge().style.left), true);
    assert.strictEqual(/^-?[0-9\.]+px$/.test(TestUtils.getHtmlBridge().style.width), true);
    assert.strictEqual(/^-?[0-9\.]+px$/.test(TestUtils.getHtmlBridge().style.height), true);
  });

})(QUnit.module, QUnit.test);
