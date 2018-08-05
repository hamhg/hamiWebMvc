define(function(require) {

    'use strict';

    var zrUtil = require('zrender/sys/util');

    var echartsAPIList = [
        'getDom', 'getZr', 'getWidth', 'getHeight', 'dispatchAction',
        'on', 'off', 'getDataURL', 'getConnectedDataURL', 'getModel', 'getOption'
    ];

    function ExtensionAPI(chartInstance) {
        zrUtil.each(echartsAPIList, function (name) {
            this[name] = zrUtil.bind(chartInstance[name], chartInstance);
        }, this);
    }

    return ExtensionAPI;
});
