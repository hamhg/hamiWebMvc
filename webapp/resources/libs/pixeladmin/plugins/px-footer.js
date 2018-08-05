!function(e, t) {
    if ("function" == typeof define && define.amd)
        define(["module", "exports", "jquery", "px/util", "px/pixeladmin"], t);
    else if ("undefined" != typeof exports)
        t(module, exports, require("jquery"), require("px/util"), require("px/pixeladmin"));
    else {
        var n = {
            exports: {}
        };
        t(n, n.exports, e.jquery, e.util, e.pixeladmin),
            e.pxFooter = n.exports
    }
}(this, function(e, t, n, i) {
    "use strict";
    function o(e) {
        return e && e.__esModule ? e : {
            default: e
        }
    }
    function r(e, t) {
        if (!(e instanceof t))
            throw new TypeError("Cannot call a class as a function")
    }
    Object.defineProperty(t, "__esModule", {
        value: !0
    });
    var u = o(n)
        , s = o(i)
        , d = function() {
        function e(e, t) {
            for (var n = 0; n < t.length; n++) {
                var i = t[n];
                i.enumerable = i.enumerable || !1,
                    i.configurable = !0,
                "value"in i && (i.writable = !0),
                    Object.defineProperty(e, i.key, i)
            }
        }
        return function(t, n, i) {
            return n && e(t.prototype, n),
            i && e(t, i),
                t
        }
    }()
        , a = function(e) {
        var t = "pxFooter"
            , n = e.fn[t]
            , i = {
            CONTENT: "px-content",
            BOTTOM: "px-footer-bottom",
            FIXED: "px-footer-fixed"
        }
            , o = {
            RESIZE: "resize.px.footer",
            SCROLL: "scroll.px.footer",
            NAV_EXPANDED: "expanded.px.nav",
            NAV_COLLAPSED: "collapsed.px.nav",
            DROPDOWN_OPENED: "dropdown-opened.px.nav",
            DROPDOWN_CLOSED: "dropdown-closed.px.nav"
        }
            , u = function() {
            function n(e) {
                r(this, n),
                    this.uniqueId = s.default.generateUniqueId(),
                    this.element = e,
                    this.parent = this._getParent(e),
                    this._setListeners(),
                    this.update()
            }
            return d(n, [{
                key: "update",
                value: function() {
                    this.parent === document.body && (this._curScreenSize = window.PixelAdmin.getScreenSize(),
                        this._updateBodyMinHeight());
                    var t = e(this.element.parentNode).find("> ." + i.CONTENT)[0];
                    s.default.hasClass(this.element, i.BOTTOM) || s.default.hasClass(this.element, i.FIXED) ? t.style.paddingBottom = e(this.element).outerHeight() + 20 + "px" : t.style.paddingBottom = t.setAttribute("style", (t.getAttribute("style") || "").replace(/\s*padding-bottom:\s*\d+px\s*;?/i))
                }
            }, {
                key: "destroy",
                value: function() {
                    this._unsetListeners(),
                        e(this.element).removeData("px.footer"),
                        e(document.body).css("min-height", "");
                    var t = e(this.element.parentNode).find("> ." + i.CONTENT)[0];
                    t.style.paddingBottom = t.setAttribute("style", (t.getAttribute("style") || "").replace(/\s*padding-bottom:\s*\d+px\s*;?/i))
                }
            }, {
                key: "_getParent",
                value: function(e) {
                    for (var t = e.parentNode; "ui-view" === t.nodeName.toLowerCase(); )
                        t = t.parentNode;
                    return t
                }
            }, {
                key: "_updateBodyMinHeight",
                value: function() {
                    document.body.style.minHeight && (document.body.style.minHeight = null),
                    "lg" !== this._curScreenSize && "xl" !== this._curScreenSize || !s.default.hasClass(this.element, i.BOTTOM) || e(document.body).height() >= document.body.scrollHeight || (document.body.style.minHeight = document.body.scrollHeight + "px")
                }
            }, {
                key: "_setListeners",
                value: function() {
                    e(window).on(this.constructor.Event.RESIZE + "." + this.uniqueId, e.proxy(this.update, this)).on(this.constructor.Event.SCROLL + "." + this.uniqueId, e.proxy(this._updateBodyMinHeight, this)).on(this.constructor.Event.NAV_EXPANDED + "." + this.uniqueId + " " + this.constructor.Event.NAV_COLLAPSED + "." + this.uniqueId, ".px-nav", e.proxy(this._updateBodyMinHeight, this)),
                    this.parent === document.body && e(".px-nav").on(this.constructor.Event.DROPDOWN_OPENED + "." + this.uniqueId + " " + this.constructor.Event.DROPDOWN_CLOSED + "." + this.uniqueId, ".px-nav-dropdown", e.proxy(this._updateBodyMinHeight, this))
                }
            }, {
                key: "_unsetListeners",
                value: function() {
                    e(window).off(this.constructor.Event.RESIZE + "." + this.uniqueId + " " + this.constructor.Event.SCROLL + "." + this.uniqueId).off(this.constructor.Event.NAV_EXPANDED + "." + this.uniqueId + " " + this.constructor.Event.NAV_COLLAPSED + "." + this.uniqueId),
                        e(".px-nav").off(this.constructor.Event.DROPDOWN_OPENED + "." + this.uniqueId + " " + this.constructor.Event.DROPDOWN_CLOSED + "." + this.uniqueId)
                }
            }], [{
                key: "_jQueryInterface",
                value: function(t) {
                    return this.each(function() {
                        var i = e(this).data("px.footer");
                        if (i || (i = new n(this),
                                e(this).data("px.footer", i)),
                            "string" == typeof t) {
                            if (!i[t])
                                throw new Error('No method named "' + t + '"');
                            i[t]()
                        }
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
                    return "px.footer"
                }
            }, {
                key: "Event",
                get: function() {
                    return o
                }
            }, {
                key: "EVENT_KEY",
                get: function() {
                    return ".px.footer"
                }
            }]),
                n
        }();
        return e.fn[t] = u._jQueryInterface,
            e.fn[t].Constructor = u,
            e.fn[t].noConflict = function() {
                return e.fn[t] = n,
                    u._jQueryInterface
            }
            ,
            u
    }(u.default);
    t.default = a,
        e.exports = t.default
});
