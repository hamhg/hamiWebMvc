!function(e, t) {
    if ("function" == typeof define && define.amd)
        define(["module", "exports", "jquery", "px/util", "px/pixeladmin", "px-bootstrap/transition", "px-libs/perfect-scrollbar.jquery"], t);
    else if ("undefined" != typeof exports)
        t(module, exports, require("jquery"), require("px/util"), require("px/pixeladmin"), require("px-bootstrap/transition"), require("px-libs/perfect-scrollbar.jquery"));
    else {
        var n = {
            exports: {}
        };
        t(n, n.exports, e.jquery, e.util, e.pixeladmin, e.transition, e.perfectScrollbar),
            e.pxNav = n.exports
    }
}(this, function(e, t, n, o) {
    "use strict";
    function i(e) {
        return e && e.__esModule ? e : {
            default: e
        }
    }
    function s(e, t) {
        if (!(e instanceof t))
            throw new TypeError("Cannot call a class as a function")
    }
    Object.defineProperty(t, "__esModule", {
        value: !0
    });
    var a = i(n)
        , r = i(o)
        , l = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function(e) {
            return typeof e
        }
        : function(e) {
            return e && "function" == typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : typeof e
        }
        , d = function() {
        function e(e, t) {
            for (var n = 0; n < t.length; n++) {
                var o = t[n];
                o.enumerable = o.enumerable || !1,
                    o.configurable = !0,
                "value"in o && (o.writable = !0),
                    Object.defineProperty(e, o.key, o)
            }
        }
        return function(t, n, o) {
            return n && e(t.prototype, n),
            o && e(t, o),
                t
        }
    }()
        , p = function(e) {
        var t = "pxNav"
            , n = e.fn[t]
            , o = {
            accordion: !0,
            transitionDuration: 300,
            dropdownCloseDelay: 400,
            enableTooltips: !0,
            animate: !0,
            storeState: !0,
            storagePrefix: "px-nav.",
            modes: {
                phone: ["xs"],
                tablet: ["sm", "md"],
                desktop: ["lg", "xl"]
            }
        }
            , i = {
            NAV: "px-nav",
            NAV_LEFT: "px-nav-left",
            CONTENT: "px-nav-content",
            EXPAND: "px-nav-expand",
            STATIC: "px-nav-static",
            COLLAPSE: "px-nav-collapse",
            ANIMATE: "px-nav-animate",
            NAV_TRANSITIONING: "px-nav-transitioning",
            DIMMER: "px-nav-dimmer",
            FIXED: "px-nav-fixed",
            OFF_CANVAS: "px-nav-off-canvas",
            SCROLLABLE_AREA: "px-nav-scrollable-area",
            ITEM: "px-nav-item",
            TOOLTIP: "px-nav-tooltip",
            DROPDOWN: "px-nav-dropdown",
            DROPDOWN_MENU: "px-nav-dropdown-menu",
            DROPDOWN_MENU_TITLE: "px-nav-dropdown-menu-title",
            DROPDOWN_MENU_SHOW: "px-nav-dropdown-menu-show",
            DROPDOWN_MENU_WRAPPER: "px-nav-dropdown-menu-wrapper",
            DROPDOWN_MENU_TOP: "px-nav-dropdown-menu-top",
            OPEN: "px-open",
            SHOW: "px-show",
            FREEZE: "freeze",
            ACTIVE: "active",
            TRANSITIONING: "transitioning",
            PERFECT_SCROLLBAR_CONTAINER: "ps-container",
            NAVBAR_FIXED: "px-navbar-fixed"
        }
            , a = {
            DATA_TOGGLE: '[data-toggle="px-nav"]',
            CONTENT: ".px-nav-content",
            ITEM: "> .px-nav-item",
            ITEM_LABEL: "> a > .px-nav-label",
            ROOT_LINK: "> .px-nav-item:not(.px-nav-dropdown) > a",
            DROPDOWN_LINK: ".px-nav-dropdown > a",
            DROPDOWN_MENU: "> .px-nav-dropdown-menu",
            DROPDOWN_MENU_TITLE: "> .px-nav-dropdown-menu-title",
            OPENED_DROPDOWNS: "> .px-nav-dropdown.px-open",
            SHOWN_DROPDOWNS: "> .px-nav-dropdown.px-show",
            FROZEN_DROPDOWNS: ".px-nav-dropdown.freeze",
            SCROLLABLE_AREA: ".px-nav-scrollable-area",
            NEAR_NAVBAR: "~ .px-navbar"
        }
            , p = {
            CLICK_DATA_API: "click.px.nav.data-api",
            RESIZE: "resize.px.nav",
            CLICK: "click.px.nav",
            MOUSEENTER: "mouseenter.px.nav",
            MOUSELEAVE: "mouseleave.px.nav",
            SCROLL: "scroll.px.nav",
            INITIALIZED: "initialized",
            EXPAND: "expand.px.nav",
            EXPANDED: "expanded.px.nav",
            COLLAPSE: "collapse.px.nav",
            COLLAPSED: "collapsed.px.nav",
            DESTROY: "destroy.px.nav",
            DROPDOWN_OPEN: "dropdown-open.px.nav",
            DROPDOWN_OPENED: "dropdown-opened.px.nav",
            DROPDOWN_CLOSE: "dropdown-close.px.nav",
            DROPDOWN_CLOSED: "dropdown-closed.px.nav",
            DROPDOWN_FROZEN: "dropdown-frozen.px.nav",
            DROPDOWN_UNFROZEN: "dropdown-unfrozen.px.nav"
        }
            , h = {
            suppressScrollX: !0,
            wheelPropagation: !1,
            swipePropagation: !1
        }
            , u = function() {
            function n(t, o) {
                s(this, n),
                    this.uniqueId = r.default.generateUniqueId(),
                    this.element = t,
                    this.content = e(t).find(a.CONTENT)[0],
                    this.config = this._getConfig(o),
                    this._curMode = this._getMode(),
                    this._isCollapsed = this._getNavState(),
                    this._stateChanging = 0,
                    this._setupMarkup(),
                    this.dimmer = e(t).parent().find("> ." + i.DIMMER)[0],
                    this._setListeners(),
                    this._restoreNavState(),
                    this._detectActiveItem(),
                    this._enableAnimation(),
                    this._checkNavbarPosition(),
                    this._triggerEvent("INITIALIZED", t)
            }
            return d(n, [{
                key: "toggle",
                value: function() {
                    this["desktop" !== this._curMode && r.default.hasClass(this.element, i.EXPAND) || "desktop" === this._curMode && !r.default.hasClass(this.element, i.COLLAPSE) ? "collapse" : "expand"]()
                }
            }, {
                key: "expand",
                value: function() {
                    ("phone" === this._curMode || this.isCollapsed()) && ("phone" === this._curMode && r.default.hasClass(this.element, i.EXPAND) || this._triggerPreventableEvent("EXPAND", this.element) && ("phone" !== this._curMode && this.closeAllDropdowns(),
                    this.config.enableTooltips && this._clearTooltips(),
                        this._changeNavState(function() {
                            var n = this;
                            if ("desktop" !== this._curMode) {
                                var o = this;
                                e(this.element).parent().find("> ." + i.EXPAND).each(function() {
                                    this !== o.element && e(this)[t]("collapse")
                                }),
                                    e(this.dimmer).on(this.constructor.Event.CLICK, function() {
                                        return n.collapse()
                                    }),
                                    r.default.addClass(this.element, i.EXPAND)
                            } else {
                                r.default.removeClass(this.element, i.COLLAPSE);
                                $('.px-location').removeClass('px-location-collapse');
                            }
                            this._triggerEvent("EXPANDED", this.element);
                        })))
                }
            }, {
                key: "collapse",
                value: function() {
                    this.isCollapsed() || this._triggerPreventableEvent("COLLAPSE", this.element) && this._changeNavState(function() {
                        "desktop" !== this._curMode ? (e(this.dimmer).off("click"),
                            r.default.removeClass(this.element, i.EXPAND)) : r.default.addClass(this.element, i.COLLAPSE),
                            e(window).trigger("scroll"),
                            this._triggerEvent("COLLAPSED", this.element);
                            $('.px-location').addClass('px-location-collapse');
                    })
                }
            }, {
                key: "isFixed",
                value: function() {
                    return r.default.hasClass(this.element, i.FIXED)
                }
            }, {
                key: "isStatic",
                value: function() {
                    return r.default.hasClass(this.element, i.STATIC)
                }
            }, {
                key: "isCollapsed",
                value: function() {
                    return this._isCollapsed
                }
            }, {
                key: "activateItem",
                value: function(t) {
                    var n = this._getNode(t, i.ITEM);
                    if (!r.default.hasClass(n, i.DROPDOWN) && (e(this.element).find("." + i.ITEM + "." + i.ACTIVE).removeClass(i.ACTIVE),
                            r.default.addClass(n, i.ACTIVE),
                            !r.default.hasClass(n.parentNode, i.CONTENT)))
                        if (r.default.hasClass(n.parentNode, i.DROPDOWN_MENU_WRAPPER)) {
                            var o = e(n).parents("." + i.DROPDOWN_MENU).data("dropdown");
                            if (!o)
                                return;
                            o.addClass(i.ACTIVE)
                        } else {
                            var s = e(n).parents("." + i.DROPDOWN)[0]
                                , l = void 0;
                            for (this.openDropdown(s, !1); s; )
                                if (r.default.addClass(s, i.ACTIVE),
                                        r.default.hasClass(s.parentNode, i.DROPDOWN_MENU_WRAPPER)) {
                                    if (l = e(s).parents("." + i.DROPDOWN_MENU).data("dropdown"),
                                            s = null,
                                            !l)
                                        return;
                                    r.default.addClass(l, i.ACTIVE)
                                } else
                                    l = s,
                                        s = e(s).parents("." + i.DROPDOWN)[0];
                            this.isCollapsed() && (e(this.content).find(a.OPENED_DROPDOWNS).removeClass(i.OPEN),
                                r.default.addClass(l, i.OPEN))
                        }
                }
            }, {
                key: "openDropdown",
                value: function(t) {
                    var n = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1]
                        , o = this._getNode(t);
                    if ((!this.isStatic() || this._isFloatingDropdown(o)) && (this._isFloatingDropdown(o) && !n || this.isDropdownOpened(o) || this._triggerPreventableEvent("DROPDOWN_OPEN", o))) {
                        for (var s = this.isDropdownOpened(o) ? [] : [o], a = o; a = e(a).parents("." + i.DROPDOWN)[0]; )
                            this.isDropdownOpened(a) || s.push(a);
                        var r = s.pop();
                        if (r) {
                            for (var l = 0, d = s.length; l < d; l++)
                                this._expandDropdown(s[l], !1);
                            if (this._isFloatingDropdown(r)) {
                                if (!n)
                                    return;
                                this._showDropdown(r)
                            } else
                                this._expandDropdown(r, !0)
                        }
                    }
                }
            }, {
                key: "closeDropdown",
                value: function(e) {
                    var t = this._getNode(e);
                    this.isDropdownOpened(t) && (this.isStatic() && !this._isFloatingDropdown(t) || this._triggerPreventableEvent("DROPDOWN_CLOSE", t) && (this._isFloatingDropdown(t) ? this._hideDropdown(t) : this._collapseDropdown(t, !0)))
                }
            }, {
                key: "toggleDropdown",
                value: function(e) {
                    var t = this._getNode(e);
                    this[this.isDropdownOpened(t) ? "closeDropdown" : "openDropdown"](t)
                }
            }, {
                key: "closeAllDropdowns",
                value: function() {
                    var t = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : e(this.element).find("." + i.CONTENT);
                    this._closeAllDropdowns(this._getNode(t, null))
                }
            }, {
                key: "freezeDropdown",
                value: function(e) {
                    var t = this._getNode(e);
                    this._isFloatingDropdown(t) && this.isDropdownOpened(t) && (r.default.hasClass(t, i.FREEZE) || (r.default.addClass(t, i.FREEZE),
                        this._clearDropdownTimer(t),
                        this._triggerEvent("DROPDOWN_FROZEN", t)))
                }
            }, {
                key: "unfreezeDropdown",
                value: function(e) {
                    var t = this._getNode(e);
                    this._isFloatingDropdown(t) && this.isDropdownOpened(t) && r.default.hasClass(t, i.FREEZE) && (r.default.removeClass(t, i.FREEZE),
                        this._triggerEvent("DROPDOWN_UNFROZEN", t))
                }
            }, {
                key: "getDropdownContainer",
                value: function(t) {
                    var n = this._getNode(t);
                    return this._isFloatingDropdown(n) && this.isDropdownOpened(n) ? e(e(n).data("dropdown")).find("." + i.DROPDOWN_MENU_WRAPPER) : e(n).find(a.DROPDOWN_MENU)
                }
            }, {
                key: "isFloatingDropdown",
                value: function(e) {
                    return this._isFloatingDropdown(this._getNode(e))
                }
            }, {
                key: "isDropdownOpened",
                value: function(e) {
                    var t = this._getNode(e)
                        , n = this._isRootDropdown(t)
                        , o = this.isCollapsed();
                    return o && n && r.default.hasClass(t, i.SHOW) || o && !n && r.default.hasClass(t, i.OPEN) || !o && r.default.hasClass(t, i.OPEN)
                }
            }, {
                key: "isDropdownFrozen",
                value: function(e) {
                    return r.default.hasClass(this._getNode(e), i.FREEZE)
                }
            }, {
                key: "append",
                value: function(e) {
                    var t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : null;
                    return this.insert(e, null, t)
                }
            }, {
                key: "prepend",
                value: function(e) {
                    var t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : null;
                    return this.insert(e, 0, t)
                }
            }, {
                key: "insert",
                value: function(t, n) {
                    var o = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : null
                        , s = this._getNodeOrCreate(t, i.ITEM, !1);
                    if (s.hasClass(i.DROPDOWN) && !s.find(a.DROPDOWN_MENU).length)
                        throw new Error("The ." + i.DROPDOWN + " item(s) must contain the child ." + i.DROPDOWN_MENU + " element.");
                    var r = null === o ? e(this.content) : this._getNode(o, i.DROPDOWN, !1)
                        , l = void 0;
                    if (r.hasClass(i.CONTENT))
                        l = r;
                    else if (!(l = this._isFloatingDropdown(r[0]) && this.isDropdownOpened(r[0]) ? e(r.data("dropdown")).find("." + i.DROPDOWN_MENU_WRAPPER) : r.find(a.DROPDOWN_MENU)).length)
                        throw new Error("Targeted element is not found.");
                    var d = l.find(a.ITEM);
                    if (d.length)
                        if (null === n)
                            s.insertAfter(d.last());
                        else {
                            var p = d.eq(n);
                            p.length ? s.insertBefore(p) : s.insertAfter(d.last())
                        }
                    else
                        l.append(s);
                    return !this.isCollapsed() || r.hasClass(i.CONTENT) ? this._updateScrollbar(this.content) : l.hasClass(i.DROPDOWN_MENU_WRAPPER) ? this._updateScrollbar(l[0]) : this._updateScrollbar(l.parents("." + i.DROPDOWN_MENU_WRAPPER)[0]),
                        s
                }
            }, {
                key: "remove",
                value: function(t) {
                    var n = this._getNode(t, i.ITEM, !1)
                        , o = n.parent();
                    n.hasClass(i.DROPDOWN) && e(n.data("dropdown")).remove(),
                        n.remove(),
                        !this.isCollapsed() || o.hasClass(i.CONTENT) ? this._updateScrollbar(this.content) : o.hasClass(i.DROPDOWN_MENU_WRAPPER) ? this._updateScrollbar(o[0]) : this._updateScrollbar(o.parents("." + i.DROPDOWN_MENU_WRAPPER)[0])
                }
            }, {
                key: "destroy",
                value: function() {
                    if (this._triggerPreventableEvent("DESTROY", this.element)) {
                        this._unsetListeners(),
                            e(this.element).removeData("px.nav"),
                            r.default.removeClass(this.element, i.ANIMATE),
                            r.default.removeClass(this.element, i.TRANSITIONING),
                            r.default.removeClass(this.element, i.EXPAND),
                        this.isCollapsed() && this.closeAllDropdowns();
                        var t = 0;
                        e(this.element.parentNode).find("> ." + i.NAV).each(function() {
                            e(this).data("px.nav") && t++
                        }),
                        t || e(this.dimmer).remove(),
                            e(this.element).find("." + i.CONTENT).perfectScrollbar("destroy"),
                            e(this.content).unwrap(a.SCROLLABLE_AREA)
                    }
                }
            }, {
                key: "_getNode",
                value: function(t) {
                    var n = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : i.DROPDOWN
                        , o = !(arguments.length > 2 && void 0 !== arguments[2]) || arguments[2]
                        , s = "string" == typeof t ? e(this.element).find(t) : e(t);
                    if (!s.length)
                        throw new Error("Element is not found.");
                    if (n && !s.hasClass(n))
                        throw new Error("Element(s) must have the ." + n + " class.");
                    return o ? s[0] : s
                }
            }, {
                key: "_getNodeOrCreate",
                value: function(t) {
                    var n = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : i.DROPDOWN
                        , o = !(arguments.length > 2 && void 0 !== arguments[2]) || arguments[2];
                    return this._getNode("string" != typeof t || "#" !== t[0] && "." !== t[0] ? e(t) : t, n, o)
                }
            }, {
                key: "_detectActiveItem",
                value: function() {
                    var t = e(this.content).find("." + i.ITEM + "." + i.ACTIVE + ":not(." + i.DROPDOWN + ")");
                    t.length && this.activateItem(t.first())
                }
            }, {
                key: "_expandDropdown",
                value: function(t) {
                    function n() {
                        s.removeClass(i.TRANSITIONING).height(""),
                            this._updateScrollbar(this.isCollapsed() ? e(t).parents("." + i.DROPDOWN_MENU_WRAPPER)[0] : this.content),
                            this._triggerEvent("DROPDOWN_OPENED", t)
                    }
                    var o = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1];
                    if (!r.default.hasClass(t, i.OPEN)) {
                        var s = e(t).find(a.DROPDOWN_MENU);
                        if (this.config.accordion && this._closeAllDropdowns(t.parentNode, o, e(t)),
                                r.default.addClass(t, i.OPEN),
                            !e.support.transition || !o)
                            return n.call(this);
                        s.height(0).addClass(i.TRANSITIONING).one("bsTransitionEnd", e.proxy(n, this)).emulateTransitionEnd(this.config.transitionDuration).height(s[0].scrollHeight)
                    }
                }
            }, {
                key: "_collapseDropdown",
                value: function(t) {
                    function n() {
                        r.default.removeClass(t, i.OPEN),
                            s.removeClass(i.TRANSITIONING).height(""),
                            e(t).find("." + i.OPEN).removeClass(i.OPEN),
                            this._updateScrollbar(this.isCollapsed() ? e(t).parents("." + i.DROPDOWN_MENU_WRAPPER)[0] : this.content),
                            this._triggerEvent("DROPDOWN_CLOSED", t)
                    }
                    var o = !(arguments.length > 1 && void 0 !== arguments[1]) || arguments[1];
                    if (r.default.hasClass(t, i.OPEN)) {
                        var s = e(t).find(a.DROPDOWN_MENU);
                        if (!e.support.transition || !o)
                            return n.call(this);
                        s.height(s.height())[0].offsetHeight,
                            s.addClass(i.TRANSITIONING).height(0).one("bsTransitionEnd", e.proxy(n, this)).emulateTransitionEnd(this.config.transitionDuration)
                    }
                }
            }, {
                key: "_showDropdown",
                value: function(t) {
                    var n = this;
                    if (!r.default.hasClass(t, i.SHOW) && this._isRootDropdown(t)) {
                        var o = t.parentNode.parentNode
                            , s = e(t).find(a.DROPDOWN_MENU)[0];
                        if (s) {
                            this.closeAllDropdowns();
                            var l = t.parentNode.offsetTop
                                , d = t.offsetTop - t.parentNode.scrollTop
                                , p = e('<div class="' + i.DROPDOWN_MENU_TITLE + '"></div>').html(e(t).find(a.ITEM_LABEL).html()).prependTo(s);
                            r.default.addClass(t, i.SHOW),
                                r.default.addClass(s, i.SHOW),
                                o.appendChild(s);
                            var u = e(t).outerHeight()
                                , f = e(s).find(a.ITEM)
                                , c = f.first().find("> a").outerHeight()
                                , v = e(this.element).outerHeight() - l
                                , N = p.outerHeight()
                                , E = N + 3 * c
                                , D = e('<div class="' + i.DROPDOWN_MENU_WRAPPER + '"></div>').append(f).appendTo(s)[0]
                                , _ = void 0;
                            d + E > v ? (_ = d,
                                this.isFixed() || "tablet" === this._curMode ? s.style.bottom = v - d - u + "px" : s.style.bottom = "0px",
                                r.default.addClass(s, i.DROPDOWN_MENU_TOP),
                                s.appendChild(p[0])) : (_ = v - d - N,
                                s.style.top = l + d + "px",
                                s.insertBefore(p[0], s.firstChild)),
                                D.style.maxHeight = _ - 10 + "px",
                                e(D).perfectScrollbar(h),
                                e(s).on(this.constructor.Event.MOUSEENTER, function() {
                                    return n._clearDropdownTimer(t)
                                }).on(this.constructor.Event.MOUSELEAVE, function() {
                                    return n._setDropdownTimer(t)
                                }),
                                e(t).data("dropdown", s),
                                e(s).data("element", t),
                                this._updateScrollbar(t.parentNode),
                                this._triggerEvent("DROPDOWN_OPENED", t)
                        }
                    }
                }
            }, {
                key: "_hideDropdown",
                value: function(t) {
                    if (r.default.hasClass(t, i.SHOW)) {
                        var n = e(t).data("dropdown");
                        if (n) {
                            r.default.removeClass(t, [i.SHOW, i.FREEZE]),
                                r.default.removeClass(n, i.SHOW),
                                r.default.removeClass(n, i.DROPDOWN_MENU_TOP),
                                this.unfreezeDropdown(t);
                            var o = e(n).find("." + i.DROPDOWN_MENU_WRAPPER);
                            e(n).find("." + i.DROPDOWN_MENU_TITLE).remove(),
                                e(n).append(o.find(a.ITEM)),
                                o.perfectScrollbar("destroy").remove(),
                                n.setAttribute("style", ""),
                                t.appendChild(n),
                                e(t).data("dropdown", null),
                                e(n).data("element", null),
                                this._clearDropdownTimer(t),
                                e(n).off("mouseenter").off("mouseleave"),
                                this._updateScrollbar(t.parentNode),
                                this._triggerEvent("DROPDOWN_CLOSED", t)
                        }
                    }
                }
            }, {
                key: "_showTooltip",
                value: function(t) {
                    this._clearTooltips();
                    var n = e(t).find(".px-nav-label").contents().filter(function() {
                        return 3 === this.nodeType
                    }).text()
                        , o = e('<div class="' + i.TOOLTIP + '"></div>').text(n)[0]
                        , s = t.parentNode.offsetTop
                        , a = t.offsetTop - t.parentNode.scrollTop;
                    o.style.top = s + a + "px",
                        e(o).data("dropdown", t),
                        t.parentNode.parentNode.appendChild(o)
                }
            }, {
                key: "_updateTooltipPosition",
                value: function() {
                    var t = e(this.element).find("." + i.TOOLTIP)[0];
                    if (t) {
                        var n = e(t).data("dropdown");
                        if (n) {
                            var o = n.parentNode.offsetTop
                                , s = n.offsetTop - n.parentNode.scrollTop;
                            t.style.top = o + s + "px"
                        } else
                            e(t).remove()
                    }
                }
            }, {
                key: "_clearTooltips",
                value: function() {
                    e(this.element).find("." + i.TOOLTIP).remove()
                }
            }, {
                key: "_closeAllDropdowns",
                value: function(t, n) {
                    var o = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : null
                        , s = this
                        , l = void 0
                        , d = void 0
                        , p = t;
                    this.isCollapsed() && r.default.hasClass(p, i.CONTENT) ? (l = a.SHOWN_DROPDOWNS,
                        d = "_hideDropdown") : (this._isFloatingDropdown(p) && this.isDropdownOpened(p) ? p = e(e(p).data("dropdown")).find("." + i.DROPDOWN_MENU_WRAPPER)[0] : r.default.hasClass(p, i.DROPDOWN) && (p = e(p).find(a.DROPDOWN_MENU)[0]),
                        l = a.OPENED_DROPDOWNS,
                        d = "_collapseDropdown"),
                        e(p).find(l).each(function() {
                            o && o === e(this) || s[d](this, n)
                        })
                }
            }, {
                key: "_isRootDropdown",
                value: function(e) {
                    return r.default.hasClass(e.parentNode, i.CONTENT)
                }
            }, {
                key: "_isFloatingDropdown",
                value: function(e) {
                    return this.isCollapsed() && this._isRootDropdown(e)
                }
            }, {
                key: "_getNavState",
                value: function() {
                    return ("phone" === this._curMode || "tablet" === this._curMode) && !r.default.hasClass(this.element, i.EXPAND) || "desktop" === this._curMode && r.default.hasClass(this.element, i.COLLAPSE)
                }
            }, {
                key: "_setDropdownTimer",
                value: function(t) {
                    var n = this;
                    if (!this.isDropdownFrozen(t)) {
                        this._clearDropdownTimer(t);
                        var o = setTimeout(function() {
                            n.isDropdownFrozen(t) || n._hideDropdown(t)
                        }, this.config.dropdownCloseDelay);
                        e(t).data("timer", o)
                    }
                }
            }, {
                key: "_clearDropdownTimer",
                value: function(t) {
                    var n = e(t).data("timer");
                    n && clearTimeout(n)
                }
            }, {
                key: "_updateScrollbar",
                value: function(t) {
                    t && r.default.hasClass(t, i.PERFECT_SCROLLBAR_CONTAINER) && e(t).perfectScrollbar("update")
                }
            }, {
                key: "_changeNavState",
                value: function(t) {
                    function n() {
                        this._stateChanging = this._stateChanging < 2 ? 0 : this._stateChanging - 1,
                        this._stateChanging || r.default.removeClass(this.element, i.NAV_TRANSITIONING),
                            this._updateScrollbar(this.content),
                            r.default.triggerResizeEvent()
                    }
                    if (this._stateChanging++,
                        this.config.animate && e.support.transition && r.default.addClass(this.element, i.NAV_TRANSITIONING),
                            t.call(this),
                            this._isCollapsed = this._getNavState(),
                            this._storeNavState(),
                        !this.config.animate || !e.support.transition)
                        return n.call(this);
                    e(this.element).one("bsTransitionEnd", e.proxy(n, this)).emulateTransitionEnd(this.config.transitionDuration)
                }
            }, {
                key: "_getMode",
                value: function() {
                    var e = window.PixelAdmin.getScreenSize()
                        , t = void 0;
                    if (-1 !== this.config.modes.phone.indexOf(e))
                        t = "phone";
                    else if (-1 !== this.config.modes.tablet.indexOf(e))
                        t = "tablet";
                    else {
                        if (-1 === this.config.modes.desktop.indexOf(e))
                            throw new Error("Cannot determine PxNav mode.");
                        t = "desktop"
                    }
                    return t
                }
            }, {
                key: "_prefixStorageKey",
                value: function(e) {
                    return this.config.storagePrefix + (r.default.hasClass(this.element, i.NAV_LEFT) ? "left." : "right.") + e
                }
            }, {
                key: "_storeNavState",
                value: function() {
                    if (this.config.storeState) {
                        var e = this._prefixStorageKey("state")
                            , t = r.default.hasClass(this.element, i.COLLAPSE) ? "collapsed" : "expanded";
                        window.PixelAdmin.storage.set(e, t)
                    }
                }
            }, {
                key: "_restoreNavState",
                value: function() {
                    if (this.config.storeState) {
                        var e = this._prefixStorageKey("state")
                            , t = window.PixelAdmin.storage.get(e) || "expanded";
                        r.default["collapsed" === t ? "addClass" : "removeClass"](this.element, i.COLLAPSE),
                            this._isCollapsed = this._getNavState(),
                            r.default.triggerResizeEvent();
                        if(this._getNavState()) $('.px-location').addClass('px-location-collapse');
                    }
                }
            }, {
                key: "_checkNavbarPosition",
                value: function() {
                    if (this.isFixed()) {
                        var t = e(this.element).find(a.NEAR_NAVBAR)[0];
                        t && (r.default.hasClass(t.parentNode, i.NAVBAR_FIXED) || (console.warn("The " + (r.default.hasClass(this.element, i.NAV_LEFT) ? "left" : "right") + " .px-nav is fixed, but the coterminous .px-navbar isn't. You need to explicitly add the ." + i.NAVBAR_FIXED + " class to the parent element to fix the navbar."),
                            r.default.addClass(t.parentNode, i.NAVBAR_FIXED)))
                    }
                }
            }, {
                key: "_setupMarkup",
                value: function() {
                    var t = e(this.element).parent();
                    if (t.find("> ." + i.DIMMER).length || t.append('<div class="' + i.DIMMER + '"></div>'),
                            !e.fn.perfectScrollbar)
                        throw new Error('Scrolling feature requires the "perfect-scrollbar" plugin included.');
                    var n = e(this.content);
                    n.length && n.wrap('<div class="' + i.SCROLLABLE_AREA + '"></div>').perfectScrollbar(h)
                }
            }, {
                key: "_setListeners",
                value: function() {
                    var t = this
                        , n = this;
                    e(window).on(this.constructor.Event.RESIZE + "." + this.uniqueId, function() {
                        n._curMode = n._getMode(),
                            n._isCollapsed = n._getNavState(),
                        n.isCollapsed() && n.closeAllDropdowns(),
                        n.config.enableTooltips && n._clearTooltips(),
                            n._updateScrollbar(n.content)
                    }),
                        e(this.element).on(this.constructor.Event.CLICK, a.DROPDOWN_LINK, function(e) {
                            e.preventDefault();
                            var t = this.parentNode;
                            n._isFloatingDropdown(t) ? n.isDropdownOpened(t) ? n[n.isDropdownFrozen(t) ? "closeDropdown" : "freezeDropdown"](t) : (n.openDropdown(t),
                                n.freezeDropdown(t)) : n.toggleDropdown(t)
                        }),
                        e(this.content).on(this.constructor.Event.MOUSEENTER, a.DROPDOWN_LINK, function() {
                            if (!window.PixelAdmin.isMobile) {
                                var t = this.parentNode;
                                if (n._isFloatingDropdown(t) && !r.default.hasClass(n.element, i.OFF_CANVAS))
                                    if (n.isDropdownOpened(t))
                                        n._clearDropdownTimer(t);
                                    else {
                                        if (e(n.element).find(a.FROZEN_DROPDOWNS).length)
                                            return;
                                        n.openDropdown(t)
                                    }
                            }
                        }).on(this.constructor.Event.MOUSELEAVE, a.DROPDOWN_LINK, function() {
                            if (!window.PixelAdmin.isMobile) {
                                var e = this.parentNode;
                                n._isFloatingDropdown(e) && n.isDropdownOpened(e) && n._setDropdownTimer(e)
                            }
                        }).on(this.constructor.Event.MOUSEENTER, a.ROOT_LINK, function() {
                            window.PixelAdmin.isMobile || n.config.enableTooltips && n.isCollapsed() && !r.default.hasClass(n.element, i.OFF_CANVAS) && n._showTooltip(this.parentNode)
                        }).on(this.constructor.Event.MOUSELEAVE, a.ROOT_LINK, function() {
                            window.PixelAdmin.isMobile || n.config.enableTooltips && n._clearTooltips()
                        }).on(this.constructor.Event.SCROLL, function() {
                            t.isCollapsed() && (t.config.enableTooltips && t._updateTooltipPosition(),
                                t.closeAllDropdowns())
                        })
                }
            }, {
                key: "_unsetListeners",
                value: function() {
                    e(window).off(this.constructor.Event.RESIZE + "." + this.uniqueId),
                        e(this.element).off(".px.nav"),
                        e(this.content).off(".px.nav").find("." + i.DROPDOWN_MENU).off(".px.nav"),
                    "desktop" !== this._curMode && r.default.hasClass(this.element, i.EXPAND) && e(this.dimmer).off(".px.nav")
                }
            }, {
                key: "_enableAnimation",
                value: function() {
                    var e = this;
                    this.config.animate && (r.default.addClass(this.element, ["off", i.ANIMATE]),
                        setTimeout(function() {
                            r.default.removeClass(e.element, "off")
                        }, this.config.transitionDuration))
                }
            }, {
                key: "_triggerEvent",
                value: function(t, n) {
                    var o = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : {};
                    e(this.element).trigger(e.Event(this.constructor.Event[t], {
                        target: n
                    }), [o])
                }
            }, {
                key: "_triggerPreventableEvent",
                value: function(t, n) {
                    var o = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : {}
                        , i = e.Event(this.constructor.Event[t], {
                        target: n
                    });
                    return e(this.element).trigger(i, [o]),
                        !i.isDefaultPrevented()
                }
            }, {
                key: "_getConfig",
                value: function(t) {
                    return e.extend({}, this.constructor.Default, e(this.element).data(), t)
                }
            }], [{
                key: "_jQueryInterface",
                value: function(t) {
                    for (var o = arguments.length, i = Array(o > 1 ? o - 1 : 0), s = 1; s < o; s++)
                        i[s - 1] = arguments[s];
                    var a = void 0
                        , r = this.each(function() {
                        var o = e(this).data("px.nav")
                            , s = "object" === (void 0 === t ? "undefined" : l(t)) ? t : null;
                        if (o || (o = new n(this,s),
                                e(this).data("px.nav", o)),
                            "string" == typeof t) {
                            if (!o[t])
                                throw new Error('No method named "' + t + '"');
                            a = o[t].apply(o, i)
                        }
                    });
                    return void 0 !== a ? a : r
                }
            }, {
                key: "Default",
                get: function() {
                    return o
                }
            }, {
                key: "NAME",
                get: function() {
                    return t
                }
            }, {
                key: "DATA_KEY",
                get: function() {
                    return "px.nav"
                }
            }, {
                key: "Event",
                get: function() {
                    return p
                }
            }, {
                key: "EVENT_KEY",
                get: function() {
                    return ".px.nav"
                }
            }]),
                n
        }();
        return e(document).on(p.CLICK_DATA_API, a.DATA_TOGGLE, function(t) {
            t.preventDefault();
            var n = e(e(this).data("target"));
            n.length || (n = e(this).parents("." + i.NAV)),
            n.length && (n.data("px.nav") || u._jQueryInterface.call(n, e(this).data()),
                u._jQueryInterface.call(n, "toggle"))
        }),
            e.fn[t] = u._jQueryInterface,
            e.fn[t].Constructor = u,
            e.fn[t].noConflict = function() {
                return e.fn[t] = n,
                    u._jQueryInterface
            }
            ,
            u
    }(a.default);
    t.default = p,
        e.exports = t.default
});
