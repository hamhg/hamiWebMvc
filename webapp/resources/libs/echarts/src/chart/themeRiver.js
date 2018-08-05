define(function (require) {

    var echarts = require('../echarts');
    var zrUtil = require('zrender/sys/util');


    require('./themeRiver/ThemeRiverSeries');
    
    require('./themeRiver/ThemeRiverView');

    echarts.registerLayout(require('./themeRiver/themeRiverLayout'));

    echarts.registerVisualCoding('chart', require('./themeRiver/themeRiverVisual'));

    echarts.registerProcessor(
        'filter', zrUtil.curry(require('../processor/dataFilter'), 'themeRiver')
    );    
});
