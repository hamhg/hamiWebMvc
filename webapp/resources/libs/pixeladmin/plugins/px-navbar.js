!function(e, t) {
    if ("function" == typeof define && define.amd)
        define(["module", "exports", "jquery", "px/util", "px-bootstrap/transition", "px-bootstrap/collapse", "px-bootstrap/dropdown", "px-libs/perfect-scrollbar.jquery"], t);
    else if ("undefined" != typeof exports)
        t(module, exports, require("jquery"), require("px/util"), require("px-bootstrap/transition"), require("px-bootstrap/collapse"), require("px-bootstrap/dropdown"), require("px-libs/perfect-scrollbar.jquery"));
    else {
        var r = {
            exports: {}
        };
        t(r, r.exports, e.jquery, e.util, e.transition, e.collapse, e.dropdown, e.perfectScrollbar),
            e.pxNavbar = r.exports
    }
}(this, function(e, t, r, n) {
    "use strict";
    function o(e) {
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
    var l = o(r)
        , i = o(n)
        , s = function() {
        function e(e, t) {
            for (var r = 0; r < t.length; r++) {
                var n = t[r];
                n.enumerable = n.enumerable || !1,
                    n.configurable = !0,
                "value"in n && (n.writable = !0),
                    Object.defineProperty(e, n.key, n)
            }
        }
        return function(t, r, n) {
            return r && e(t.prototype, r),
            n && e(t, n),
                t
        }
    }()
        , c = function(e) {
        var t = "pxNavbar"
            , r = ".px.navbar"
            , n = e.fn[t]
            , o = {
            NAVBAR: "px-navbar",
            INNER: "px-navbar-collapse-inner",
            IN: "in",
            COLLAPSED: "collapsed"
        }
            , l = {
            DATA_TOGGLE: '.navbar-toggle[data-toggle="collapse"]',
            DROPDOWN_TOGGLE: '.dropdown-toggle[data-toggle="dropdown"]',
            COLLAPSE: ".navbar-collapse",
            DROPDOWN: ".dropdown"
        }
            , c = {
            CLICK_DATA_API: "click.px.navbar.data-api",
            RESIZE: "resize.px.navbar",
            CLICK: "click.px.navbar",
            MOUSEDOWN: "mousedown.px.navbar",
            COLLAPSE_SHOW: "show.bs.collapse.px.navbar",
            COLLAPSE_SHOWN: "shown.bs.collapse.px.navbar",
            COLLAPSE_HIDDEN: "hidden.bs.collapse.px.navbar",
            DROPDOWN_SHOWN: "shown.bs.dropdown.px.navbar",
            DROPDOWN_HIDDEN: "hidden.bs.dropdown.px.navbar"
        }
            , u = function() {
            function n(t) {
                if (a(this, n),
                        !e.fn.perfectScrollbar)
                    throw new Error('Scrolling feature requires the "perfect-scrollbar" plugin included.');
                this.uniqueId = i.default.generateUniqueId(),
                    this.element = t,
                    this.$collapse = e(t).find(l.COLLAPSE),
                    this.$toggle = e(t).find(l.DATA_TOGGLE),
                    this._scrollbarEnabled = 0,
                    this._curScrollTop = 0,
                this.$collapse.length && this.$toggle.length && (this.$inner = this._setupInnerContainer(),
                    this._setListeners())
            }
            return s(n, [{
                key: "updateScrollbar",
                value: function() {
                    this._scrollbarEnabled && (this._updateHeight(),
                        this.$inner.scrollTop(this._curScrollTop).perfectScrollbar("update"))
                }
            }, {
                key: "destroy",
                value: function() {
                    this._unsetListeners(),
                        this._disableScrollbar(),
                        this.$collapse.append(this.$inner.find("> *")),
                        this.$inner.remove(),
                        e(this.element).removeData("px.navbar")
                }
            }, {
                key: "_updateHeight",
                value: function() {
                    var t = e(window).height() - this.$collapse[0].offsetTop;
                    this.$collapse.height(""),
                    this.$collapse.height() > t && this.$collapse.height(t + "px")
                }
            }, {
                key: "_enableScrollbar",
                value: function() {
                    this._scrollbarEnabled || (this._updateHeight(),
                        this.$inner.perfectScrollbar({
                            suppressScrollX: !0
                        }),
                        this._scrollbarEnabled = 1)
                }
            }, {
                key: "_disableScrollbar",
                value: function() {
                    this._scrollbarEnabled && (this.$collapse.height(""),
                        this.$inner.perfectScrollbar("destroy"),
                        this._scrollbarEnabled = 0)
                }
            }, {
                key: "_setupInnerContainer",
                value: function() {
                    var t = e('<div class="' + o.INNER + '"></div>');
                    return t.append(this.$collapse.find("> *")),
                        this.$collapse.append(t),
                        t
                }
            }, {
                key: "_setListeners",
                value: function() {
                    var t = this
                        , r = this;
                    e(window).on(this.constructor.Event.RESIZE + "." + this.uniqueId, function() {
                        t._scrollbarEnabled && (t.$toggle.is(":visible") ? (t._curScrollTop = t.$inner[0].scrollTop,
                            t.updateScrollbar()) : (t._disableScrollbar(),
                            t.$collapse.removeClass(o.IN),
                            t.$toggle.addClass(o.COLLAPSED),
                            t.$collapse.attr("aria-expanded", "false"),
                            t.$toggle.attr("aria-expanded", "false")))
                    }),
                        e(this.element).on(this.constructor.Event.COLLAPSE_SHOW, l.COLLAPSE, function() {
                            t.$collapse.find(".dropdown.open").removeClass("open")
                        }).on(this.constructor.Event.COLLAPSE_SHOWN, l.COLLAPSE, function() {
                            t._enableScrollbar()
                        }).on(this.constructor.Event.COLLAPSE_HIDDEN, l.COLLAPSE, function() {
                            t._disableScrollbar()
                        }).on(this.constructor.Event.DROPDOWN_SHOWN + " " + this.constructor.Event.DROPDOWN_HIDDEN, l.DROPDOWN, function() {
                            t.updateScrollbar()
                        }).on(this.constructor.Event.MOUSEDOWN, l.DROPDOWN_TOGGLE, function() {
                            if (!t._scrollbarEnabled)
                                return !0;
                            t._curScrollTop = t.$inner[0].scrollTop
                        }).on(this.constructor.Event.CLICK, l.DROPDOWN_TOGGLE, function(e) {
                            return !r._scrollbarEnabled || (!this.getAttribute("href") || "#" === this.getAttribute("href") || (e.preventDefault(),
                                e.stopPropagation(),
                                this.removeAttribute("data-toggle"),
                                this.click(),
                                void this.setAttribute("data-toggle", "dropdown")))
                        })
                }
            }, {
                key: "_unsetListeners",
                value: function() {
                    e(window).off(this.constructor.Event.RESIZE + "." + this.uniqueId),
                        e(this.element).off(r)
                }
            }], [{
                key: "_jQueryInterface",
                value: function(t) {
                    for (var r = arguments.length, o = Array(r > 1 ? r - 1 : 0), a = 1; a < r; a++)
                        o[a - 1] = arguments[a];
                    return this.each(function() {
                        var r = e(this).data("px.navbar");
                        if (r || (r = new n(this),
                                e(this).data("px.navbar", r),
                            e.support.transition || "true" !== e(this).find(l.DATA_TOGGLE).attr("aria-expanded") || r._enableScrollbar()),
                            "string" == typeof t) {
                            if (!r[t])
                                throw new Error('No method named "' + t + '"');
                            r[t].apply(r, o)
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
                    return "px.navbar"
                }
            }, {
                key: "Event",
                get: function() {
                    return c
                }
            }, {
                key: "EVENT_KEY",
                get: function() {
                    return r
                }
            }]),
                n
        }();
        return e(document).on(c.CLICK_DATA_API, "." + o.NAVBAR + " " + l.DATA_TOGGLE, function(t) {
            t.preventDefault();
            var r = e(this).parents("." + o.NAVBAR);
            r.length && (r.data("px.navbar") || u._jQueryInterface.call(r))
        }),
            e.fn[t] = u._jQueryInterface,
            e.fn[t].Constructor = u,
            e.fn[t].noConflict = function() {
                return e.fn[t] = n,
                    u._jQueryInterface
            }
            ,
            u
    }(l.default);
    t.default = c,
        e.exports = t.default
});
