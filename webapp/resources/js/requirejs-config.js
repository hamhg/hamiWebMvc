/**
 * Copyright (c) 2017 hami.
 * Require JS Main
 * Version 1.0
 */
'use strict';

require.config({
    baseUrl: "/libs",
    paths: {
        jquery: 'jquery/jquery',
        'jquery-ui': 'jquery-ui/jquery-ui',
        Vue: 'vue/vue',
        Axios: 'axios/axios.min',
        backbone: 'backbone/backbone',
        underscore: 'underscore/underscore',
        Handsontable: 'handsontable/handsontable',
        //s: OJ Lib
        knockout: 'knockout/knockout-3.4.2.min',
        'jqueryui-amd': 'oraclejet/js/jquery/jqueryui-amd-1.12.0.min',
        promise: 'oraclejet/js/es6-promise/es6-promise.min',
        ojs: 'oraclejet/js/oj/v4.0.0/min',
        ojL10n: 'oraclejet/js/oj/v4.0.0/ojL10n',
        ojtranslations: 'oraclejet/js/oj/v4.0.0/resources',
        signals: 'oraclejet/js/js-signals/signals.min',
        text: 'oraclejet/js/require/text',
        hammerjs: 'oraclejet/js/hammer/hammer-2.0.8.min',
        ojdnd: 'oraclejet/js/dnd-polyfill/dnd-polyfill-1.0.0.min',
        customElements: 'oraclejet/js/webcomponents/CustomElements',
        //e: OJ Lib
        bootstrap: 'bootstrap/js/bootstrap',
        clipboard: 'clipboard/clipboard.min',
        onoffcanvas: 'onoffcanvas/onoffcanvas',
        screenfull: 'screenfull/screenfull',
        nprogress: 'nprogress/nprogress',
        chartJs: 'Chart.js/dist/Chart.min',
        gauge: 'gauge.js/dist/gauge.min',
        skycons: 'skycons/skycons',
        dateJS: 'DateJS/build/date',
        moment: 'handsontable/moment/moment',
        pikaday: 'handsontable/pikaday/pikaday',
        numbro: 'handsontable/numbro/numbro',
        zeroclipboard: 'zeroclipboard/ZeroClipboard',
        pace: 'pace/pace.min',
        iCheck: 'iCheck/icheck.min',
        fastclick: 'fastclick/lib/fastclick',
        progressbar: 'bootstrap-progressbar/bootstrap-progressbar.min',
        daterangepicker: 'bootstrap-daterangepicker/daterangepicker',
        px: "pixeladmin",
        "px-libs": "pixeladmin/libs",
        "px-bootstrap": "pixeladmin/bootstrap",
        morris: 'pixeladmin/libs/morris',
        Chartist: "pixeladmin/libs/chartist",
        c3: "pixeladmin/libs/c3",
        d3: "pixeladmin/libs/d3",
        "datatables.net": "pixeladmin/libs/jquery.dataTables"
    },
    shim: {
        'underscore': { exports: '_' },
        'bootstrap': {
            deps: ['jquery'],
            exports: 'bootstrap'
        },
        'Handsontable': {
            deps: ['jquery', 'moment', 'pikaday', 'zeroclipboard'],
            exports: 'Handsontable'
        }
    },
    flot: ['jquery']
});
//require(["custom"], function (){});