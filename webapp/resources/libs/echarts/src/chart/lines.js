define(function (require) {

    require('./lines/LinesSeries');
    require('./lines/LinesView');

    var zrUtil = require('zrender/sys/util');
    var echarts = require('../echarts');
    echarts.registerLayout(
        require('./lines/linesLayout')
    );

    echarts.registerVisualCoding(
        'chart', zrUtil.curry(require('../visual/seriesColor'), 'lines', 'lineStyle')
    );
});
