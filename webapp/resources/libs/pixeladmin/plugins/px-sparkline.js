!function(e, t) {
    if ("function" == typeof define && define.amd)
        define(["module", "exports", "jquery", "px/util", "px/libs/jquery.sparkline"], t);
    else if ("undefined" != typeof exports)
        t(module, exports, require("jquery"), require("px/util"), require("px/plugins/jquery.sparkline"));
    else {
        var n = {
            exports: {}
        };
        t(n, n.exports, e.jquery, e.util, e.jquery),
            e.pxSparkline = n.exports
    }
}(this, function(e, t, n, r) {
    "use strict";
    function i(e) {
        return e && e.__esModule ? e : {
            default: e
        }
    }
    function a(e, t) {
        if (!(e instanceof t))
            throw new TypeError("Cannot call a class as a function")
    }
    Object.defineProperty(t, "__esModule", {
        value: !0
    });
    var u = i(n)
        , s = i(r)
        , o = function() {
        function e(e, t) {
            for (var n = 0; n < t.length; n++) {
                var r = t[n];
                r.enumerable = r.enumerable || !1,
                    r.configurable = !0,
                "value"in r && (r.writable = !0),
                    Object.defineProperty(e, r.key, r)
            }
        }
        return function(t, n, r) {
            return n && e(t.prototype, n),
            r && e(t, r),
                t
        }
    }()
        , l = function(e) {
        if (!e.fn.sparkline)
            throw new Error("jquery.sparkline.js required.");
        var t = "pxSparkline"
            , n = "px.sparkline"
            , r = e.fn[t]
            , i = {
            RESIZE: "resize.px.sparkline"
        }
            , u = function() {
            function r(t, n, i) {
                a(this, r),
                    this.uniqueId = s.default.generateUniqueId(),
                    this.element = t,
                    this.$parent = e(t.parentNode),
                    this.update(n, i),
                    this._setListeners()
            }
            return o(r, [{
                key: "update",
                value: function(t, n) {
                    null !== t && (this._values = t),
                    null !== n && ("100%" !== n.width || "bar" !== n.type && "tristate" !== n.type || void 0 !== n.barSpacing || (n.barSpacing = "2px"),
                        this.config = n);
                    var r = e.extend(!0, {}, this.config);
                    "100%" === r.width && ("bar" === r.type || "tristate" === r.type ? r.barWidth = this._getBarWidth(this.$parent, this._values.length, r.barSpacing) : r.width = Math.floor(this.$parent.width())),
                        e(this.element).sparkline(this._values, r)
                }
            }, {
                key: "destroy",
                value: function() {
                    this._unsetListeners(),
                        e(this.element).removeData(n).removeData("_jqs_mhandler").removeData("_jqs_vcanvas").off().find("canvas").remove()
                }
            }, {
                key: "_getBarWidth",
                value: function(e, t, n) {
                    var r = e.width()
                        , i = parseInt(n, 10) * (t - 1);
                    return Math.floor((r - i) / t)
                }
            }, {
                key: "_setListeners",
                value: function() {
                    var t = this;
                    e(window).on(this.constructor.Event.RESIZE + "." + this.uniqueId, function() {
                        if ("100%" === t.config.width) {
                            var n = e.extend(!0, {}, t.config);
                            "bar" === n.type || "tristate" === n.type ? n.barWidth = t._getBarWidth(t.$parent, t._values.length, n.barSpacing) : n.width = Math.floor(t.$parent.width()),
                                e(t.element).sparkline(t._values, n)
                        }
                    })
                }
            }, {
                key: "_unsetListeners",
                value: function() {
                    e(window).off(this.constructor.Event.RESIZE + "." + this.uniqueId)
                }
            }], [{
                key: "_parseArgs",
                value: function(t, n) {
                    var r = void 0
                        , i = void 0;
                    return "[object Array]" === Object.prototype.toString.call(n[0]) || "html" === n[0] || null === n[0] ? (r = n[0],
                        i = n[1] || null) : i = n[0] || null,
                    "html" !== r && void 0 !== r || null === r || (void 0 !== (r = t.getAttribute("values")) && null !== r || (r = e(t).html()),
                        r = r.replace(/(^\s*<!--)|(-->\s*$)|\s+/g, "").split(",")),
                    r && "[object Array]" === Object.prototype.toString.call(r) && 0 !== r.length || (r = null),
                        {
                            values: r,
                            config: i
                        }
                }
            }, {
                key: "_jQueryInterface",
                value: function() {
                    for (var t = arguments.length, i = Array(t), a = 0; a < t; a++)
                        i[a] = arguments[a];
                    return this.each(function() {
                        var t = e(this).data(n)
                            , a = "update" === i[0] || "destroy" === i[0] ? i[0] : null
                            , u = r._parseArgs(this, a ? i.slice(1) : i)
                            , s = u.values
                            , o = u.config;
                        t ? s && t.update(s, o) : (t = new r(this,s || [],o || {}),
                            e(this).data(n, t)),
                            "update" === a ? t.update(s, o) : "destroy" === a && t.destroy()
                    })
                }
            }, {
                key: "NAME",
                get: function() {
                    return t
                }
            }, {
                key: "DATA_KEY",
                get: function() {
                    return n
                }
            }, {
                key: "Event",
                get: function() {
                    return i
                }
            }, {
                key: "EVENT_KEY",
                get: function() {
                    return ".px.sparkline"
                }
            }]),
                r
        }();
        return e.fn[t] = u._jQueryInterface,
            e.fn[t].Constructor = u,
            e.fn[t].noConflict = function() {
                return e.fn[t] = r,
                    u._jQueryInterface
            }
            ,
            u
    }(u.default);
    t.default = l,
        e.exports = t.default
});
