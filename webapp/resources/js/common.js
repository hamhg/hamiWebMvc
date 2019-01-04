(function(root, factory) {
    if (typeof define === 'function' && define.amd) {
        define([], factory);
    } else if (typeof exports === 'object') {
        module.exports = factory();
    } else {
        root.pxCom = factory();
    }
}(this, function() {
    'use strict';

	function attachOnLoadHandler(cb) {
	    if (window.attachEvent) {
	        window.attachEvent('onload', cb);
	    } else if (window.onload) {
	        var curronload = window.onload;
	
		    window.onload = function(evt) {
		        curronload(evt);
		        cb(evt);
		    };
	    } else {
	        window.onload = cb;
	    }
	}
	
	var pxCom = (function() {
	
	    // Constants
		var ESCAPE_HTML_MAP = {
	        '&': '&amp;',
	        '"': '&quot;',
	        '\'': '&#39;',
	        '<': '&lt;',
	        '>': '&gt;'
		};  
		  
	    var COLORS = [
	        '#0288D1',
	        '#FF4081',
	        '#4CAF50',
	        '#D32F2F',
	        '#FFC107',
	        '#673AB7',
	        '#FF5722',
	        '#CDDC39',
	        '#795548',
	        '#607D8B',
	        '#009688',
	        '#E91E63',
	        '#9E9E9E',
	        '#E040FB',
	        '#00BCD4'
	    ];
	
	    var BACKGROUNDS = [
	        'img/common/bgs/1.jpg',
	        'img/common/bgs/2.jpg',
	        'img/common/bgs/3.jpg',
	        'img/common/bgs/4.jpg',
	        'img/common/bgs/5.jpg',
	        'img/common/bgs/6.jpg',
	        'img/common/bgs/7.jpg',
	        'img/common/bgs/8.jpg',
	        'img/common/bgs/9.jpg'
	    ];
	
	    var THEMES = [
  	        'default',
	        'asphalt',
	        'purple-hills',
	        'adminflare',
	        'dust',
	        'frost',
	        'fresh',
	        'silver',
	        'clean',
	        'white',
	        'candy-black',
	        'candy-blue',
	        'candy-red',
	        'candy-orange',
	        'candy-green',
	        'candy-cyan'
	    ];
	
	    var comSettings = (function loadcomSettings() {
	        var result = {
	            fixed_navbar:  '0',
	            fixed_nav:     '1',
	            right_nav:     '0',
	            offcanvas_nav: '0',
	            rtl:           '0',
	            footer:        'bottom',
	            theme:         THEMES[6]
	        };
	
	        var cookie = ';' + document.cookie + ';';
	
	        var re;
	        var found;
	
	        for (var key in result) {
	            if (Object.prototype.hasOwnProperty.call(result, key)) {
		            re = new RegExp(';\\s*' + encodeURIComponent('px-' + key) + '\\s*=\\s*([^;]+)\\s*;');
		            found = cookie.match(re);
		
		            if (found) {
		                result[key] = decodeURIComponent(found[1]);
		            }
	            }
	        }
	
	        // Guards
	        result.fixed_navbar  = [ '0', '1' ].indexOf(result.fixed_navbar) !== -1 ? result.fixed_navbar : '0';
	        result.fixed_nav     = [ '0', '1' ].indexOf(result.fixed_nav) !== -1 ? result.fixed_nav : '0';
	        result.right_nav     = [ '0', '1' ].indexOf(result.right_nav) !== -1 ? result.right_nav : '0';
	        result.offcanvas_nav = [ '0', '1' ].indexOf(result.offcanvas_nav) !== -1 ? result.offcanvas_nav : '0';
	        result.rtl           = [ '0', '1' ].indexOf(result.rtl) !== -1 ? result.rtl : '0';
	        result.footer        = [ 'static', 'bottom', 'fixed' ].indexOf(result.footer) !== -1 ? result.footer : 'bottom';
	        result.theme         = THEMES.indexOf(result.theme) !== -1 ? result.theme : THEMES[0];
	
	        return result;
	    })();
	
	    var CURRENT_THEME = comSettings.theme;
	
	    function setSidebarState(state) {
	        $('#px-sidebar input').prop('disabled', state === 'disabled');
	        $('#px-sidebar-loader')[CURRENT_THEME.indexOf('dark') === -1 ? 'removeClass': 'addClass']('form-loading-inverted');
	        $('#px-sidebar-loader')[state === 'disabled' ? 'show': 'hide']();
	    }
	
	    // Private
	
	    function updateSettings(settings) {
	        $.extend(comSettings, settings);
	
	        for (var key in comSettings) {
	            if (Object.prototype.hasOwnProperty.call(comSettings, key)) {
	                document.cookie = encodeURIComponent('px-' + key) + '=' + encodeURIComponent(comSettings[key]);
	            }
	        }
	    }
	
	    function _createStylesheetLink(href, className, cb) {
	        var head = document.getElementsByTagName('head')[0];
	        var link = document.createElement('link');
	
	        link.className = className;
	        link.type      = 'text/css';
	        link.rel       = 'stylesheet';
	        link.href      = href;
	
	        var r = false;
	
	        link.onload = link.onreadystatechange = function() {
		        if (!r && (!this.readyState || this.readyState === 'complete')) {
		            r = true;
		
		            var links = document.getElementsByClassName(className);
		
		            if (links.length > 1) {
			            for (var i = 1, l = links.length; i < l; i++) {
			                head.removeChild(links[i]);
			            }
		            }
		
		            document.documentElement.className = document.documentElement.className.replace(/\s*px-no-transition/, '');
		        }
		
		        if (cb) { cb(); }
	        };
	
	        document.documentElement.className += ' px-no-transition';
	
	        return link;
	    }
	
	    function setTheme(themeName) {
	        if (themeName === CURRENT_THEME) { return; }
	
	        CURRENT_THEME = themeName;
	
	        var _isDark   = themeName.indexOf('dark') !== -1;
	        var _isRtl    = document.getElementsByTagName('html')[0].getAttribute('dir') === 'rtl';
	        var themePath = '/css/themes/' + themeName + (_isRtl ? '.rtl' : '') + '.min.css';
	
	        var linksToLoad = [];
	 
	        // Switch between light and dark assets
	 
	        var _assetCls = [ 'px-stylesheet-bs', 'px-stylesheet-sys', 'px-stylesheet-widgets' ];
	        var _assetLink;
	
	        function _assetReplacer(match, path, name, suffix) {
	        	return path + name.replace('-dark', '') + (_isDark ? '-dark' : '') + suffix;
	        }
	
	        for (var _i = 0, _l = _assetCls.length; _i < _l; _i++) {
		        _assetLink = (document.getElementsByClassName(_assetCls[_i]) || [])[0] || null;
		
		        if (_assetLink) {
		            linksToLoad.push([ _assetLink.getAttribute('href').replace(/^(.*?)([^\/\.]+)((?:\.rtl)?(?:\.min)?\.css)$/, _assetReplacer), _assetCls[_i]]);
		        }
	        }
	
	        linksToLoad.push([ themePath, 'px-stylesheet-theme' ]);
	
	        var linksContainer = document.createDocumentFragment();
	        var loadedLinks = 0;
	
	        function _cb() {
		        loadedLinks++;
		
		        if (loadedLinks < linksToLoad.length) { return; }
		
		        setSidebarState('enabled');
	        }
	
	        for (var i = 0, l = linksToLoad.length; i < l; i++) {
	        	linksContainer.appendChild(_createStylesheetLink(linksToLoad[i][0], linksToLoad[i][1], _cb));
	        }
	
	        document.getElementsByTagName('head')[0].insertBefore(
		        linksContainer,
		        document.getElementsByClassName('px-stylesheet-bs')[0]
	        );
	    }
	
	    function loadTheme() {
	        setTheme(comSettings.theme);
	    }
	
	    function loadRtl() {
	        if (comSettings.rtl !== '1') { return; }
	
	        document.getElementsByTagName("html")[0].setAttribute('dir', 'rtl');
	    }
	
	    function placeNav(side) {
	        var navEl  = document.querySelector('body > .px-nav') || document.querySelector('body > ui-view > .px-nav');
	
	        navEl.className = navEl.className
	            .replace(new RegExp("^\\s*px-nav-(?:left|right)\\s*", 'i'), '')
	            .replace(new RegExp("\\s*px-nav-(?:left|right)\\s*$", 'i'), '')
	            .replace(new RegExp("\\s+px-nav-(?:left|right)\\s+", 'ig'), ' ') + ' px-nav-' + side;
	    }
	
	    function setFooterPosition(pos) {
	        var footer  = document.querySelector('body > .px-footer') || document.querySelector('body > ui-view > .px-footer');
	
	        if (!footer) { return; }
	
	        footer.className = footer.className
		        .replace(/^\s*px-footer-(?:bottom|fixed)\s*/i, '')
		        .replace(/\s*px-footer-(?:bottom|fixed)\s*$/i, '')
		        .replace(/\s+px-footer-(?:bottom|fixed)\s+/gi, ' ') + ((pos === 'bottom' || pos === 'fixed') ? (' px-footer-' + pos) : '');
	    }
	
	    function capitalizeAllLetters(str, splitter) {
	        var parts = str.split(splitter || ' ');
	
	        for (var i = 0, l = parts.length; i < l; i++) {
	        	parts[i] = parts[i].charAt(0).toUpperCase() + parts[i].slice(1);
	        }
	
	        return parts.join(' ');
	    }
	
	    // Public
	
	    function shuffle(a) {
	        var j;
	        var x;
	        var i;
	
	        for (i = a.length; i; i -= 1) {
		        j = Math.floor(Math.random() * i);
		        x = a[i - 1];
		        a[i - 1] = a[j];
		        a[j] = x;
	        }
	    }
	
	    function getRandomData(max, min) {
	        return Math.floor(Math.random() * ((max || 100) - (min || 0))) + (min || 0);
	    }
	
	    function getRandomColors(count) {
	        if (count && count > COLORS.length) {
	            throw new Error('Have not enough colors');
	        }
	
	        var clrLeft = count || COLORS.length;
	        var source  = [].concat(COLORS);
	        var result  = [];
	
	        while (clrLeft-- > 0) {
		        result.unshift(source[source.length > 1 ? getRandomData(source.length - 1) : 0]);
		        source.splice(source.indexOf(result[0]), 1);
	        }
	
	        shuffle(result);
	
	        return result;
	    }
	
	    function initialize() {
	        $('input#px-fixed-navbar-toggler').on('change', function() {
		    	updateSettings({
		            fixed_navbar: $(this).is(':checked') ? '1' : '0'
		        });
		
		        $(document.querySelector('body > ui-view') || document.body)[
		            $(this).is(':checked') ? 'addClass' : 'removeClass'
		        ]('px-navbar-fixed');
		
		        var $fixedNavToggler = $('input#px-fixed-nav-toggler');
		
		        if (!$(this).is(':checked') && $fixedNavToggler.is(':checked')) {
		            $fixedNavToggler.click();
		        }
	        });
	
	        $('input#px-fixed-nav-toggler').on('change', function() {
	    	    updateSettings({
	    		    fixed_nav: $(this).is(':checked') ? '1' : '0'
	            });
	
	            $('body > .px-nav, body > ui-view > .px-nav')[ $(this).is(':checked') ? 'addClass' : 'removeClass' ]('px-nav-fixed');
	
	            var $fixedNavbarToggler = $('input#px-fixed-navbar-toggler');
	
	            if ($(this).is(':checked') && !$fixedNavbarToggler.is(':checked')) {
	                $fixedNavbarToggler.click();
	            }
	
	            $(window).trigger('scroll');
	        });
	
	        $('input#px-nav-right-toggler').on('change', function() {
		    	updateSettings({
		            right_nav: $(this).is(':checked') ? '1' : '0'
		        });
		
		        placeNav($(this).is(':checked') ? 'right' : 'left');
	        });
	
	        $('input#px-nav-off-canvas-toggler').on('change', function() {
		    	updateSettings({
		            offcanvas_nav: $(this).is(':checked') ? '1' : '0'
		        });
		
		        $('body > .px-nav, body > ui-view > .px-nav')[
		            $(this).is(':checked') ? 'addClass' : 'removeClass'
		        ]('px-nav-off-canvas');
		
		        $(window).trigger('resize');
	        });
	
	        $('input#px-nav-rtl-toggler').on('change', function() {
		        setSidebarState('disabled');
		
		        updateSettings({
		            rtl: $(this).is(':checked') ? '1' : '0'
		        });
		
		        document.location.reload();
	        });
	
	        $('select#px-footer-position-select').on('change', function() {
		    	updateSettings({
		            footer: $(this).val()
		        });
		
		        setFooterPosition($(this).val());
		
		        $(window).trigger('resize');
	        });
	
	        $('input[name="px-current-theme"]').on('change', function() {
		        setSidebarState('disabled');
		
		        var themeName = THEMES.indexOf(this.value) !== -1 ? this.value : THEMES[0];
		
		        updateSettings({ theme: themeName });
		        setTheme(themeName);
	        });
	
	
	        // Initialize "close" button
	
	        $('.px-nav')
		        .off('click.px-nav-box')
		        .on('click.px-nav-box', '#px-nav-box .close', function(e) {
		            e.preventDefault();
		
		            var $box     = $(this).parents('.px-nav-box').addClass('no-animation');
		            var $wrapper = $('<div></div>').css({ overflow: 'hidden' });
		
		            // Remove close button
		            $(this).remove();
		
		            $wrapper.insertBefore($box)
			            .append($box)
			            .animate( {opacity: 0, height: 'toggle'}, 400, function() { $wrapper.remove(); } );
		        });
	    }
	
	    function initializeBgs(selector, defaultBgIndex, overlay, afterCall) {
	        var isBgSet = false;
	
	        if (defaultBgIndex) {
		        $(selector).pxResponsiveBg({
		            backgroundImage: BACKGROUNDS[defaultBgIndex - 1],
		            overlay:         overlay,
		        });
		
		        isBgSet = true;
		
		        if (afterCall) { afterCall(isBgSet); }
	        }
	
	        var elementsHtml = '<a href="#" class="px-bgs-container px-bgs-clear">&times;</a>';
	
	        for (var i = 0, l = BACKGROUNDS.length; i < l; i++) {
	            elementsHtml += '<a href="#" class="px-bgs-container"><img src="' + BACKGROUNDS[i] + '" alt=""></a>';
	        }
	
	        var $block = $('<div class="px-bgs">' + elementsHtml + '</div>');
	
	        $block.on('click', '.px-bgs-container', function(e) {
		        e.preventDefault();
		
		        var $container = $(this);
		
		        if ($container.hasClass('px-bgs-clear')) {
		            if (!isBgSet) { return; }
		
		            $(selector).pxResponsiveBg('destroy', true);
		
		            isBgSet = false;
		
		            if (afterCall) { afterCall(isBgSet); }
		        } else {
		            if (isBgSet || $(selector).data('px.responsiveBg')) { $(selector).pxResponsiveBg('destroy'); }
		
		            $(selector).pxResponsiveBg({
			            backgroundImage: $container.find('> img').attr('src'),
			            overlay:         overlay
		            });
		
		            isBgSet = true;
		
		            if (afterCall) { afterCall(isBgSet); }
		        }
	        });
	
	        $('body').append($block);
	    }
	
	    function destroyBgs(selector) {
	        if (!$.fn.pxResponsiveBg) { return; }
	        $(selector).pxResponsiveBg('destroy', true);
	        $('.px-bgs').off().remove();
	    }
	
	    function initializeSidebar(container, skipFooter) {
	        var sidebarEl = document.createElement('DIV');
	
	        sidebarEl.id          = 'px-sidebar';
	        sidebarEl.className   = 'px-sidebar-right bg-primary';
	        sidebarEl.style.width = '242px';
	        sidebarEl.innerHTML   = '<div id="px-sidebar-loader" class="form-loading form-loading-inverted"></div>';
	
	        var contentEl = document.createElement('DIV');
	
	        contentEl.className = 'px-sidebar-content';
	        sidebarEl.appendChild(contentEl);
	
	        var content  = '';
	        var navEl  = document.querySelector('body > .px-nav') || document.querySelector('body > ui-view > .px-nav');
	
	        content += '<div id="px-togglers">';
	
	        content += '<h6 class="px-sidebar-header b-y-1 bg-primary darker">SETTINGS</h6>';
	
	        // Togglers
	        content += '<div><div class="box m-a-0 border-radius-0 bg-transparent">';
	
	        (document.querySelector('body > ui-view') || document.body).className += ' px-navbar-fixed';
	
	        // Fixed nav
	        content +=
	            '<div class="box-row"' + (navEl ? '' : 'style="display: none;"') + '>' +
		            '<div class="box-cell bg-primary p-l-3"><label for="px-fixed-nav-toggler">Fixed nav</label></div>' +
		            '<div class="box-cell bg-primary p-r-3" style="width: 70px;">' +
		                '<label for="px-fixed-nav-toggler" class="switcher switcher-blank switcher-sm"><input type="checkbox" id="px-fixed-nav-toggler"' + (comSettings.fixed_nav === '1' ? ' checked' : '') + '><div class="switcher-indicator"><div class="switcher-yes bg-primary darker"><i class="fa fa-check"></i></div><div class="switcher-no"><i class="fa fa-close"></i></div></div></label>' +
		            '</div>' +
	            '</div>';
	
	        if (navEl && comSettings.fixed_nav === '1') {
	        	navEl.className += ' px-nav-fixed';
	        }
	
	        if (navEl) {
	        	placeNav(comSettings.right_nav === '1' ? 'right' : 'left');
	        }
	
	        // Footer
	        var hasFooter = document.querySelector('body > .px-footer') || document.querySelector('body > ui-view > .px-footer');
	
	        if (!skipFooter) {
		        content +=
		            '<div id="px-footer-position"' + (hasFooter ? '' : 'style="display: none;"') + '><div class="box m-a-0 border-radius-0 bg-transparent">' +
			            '<div class="box-row">' +
			                '<div class="box-cell bg-primary p-l-3"><label for="px-footer-position-select">Footer</label></div>' +
			                '<div class="box-cell bg-primary p-r-3">' +
			                    '<select class="custom-select form-control input-sm bg-primary darker" id="px-footer-position-select"><option value="static"' + (comSettings.footer === 'static' ? ' selected' : '') + '>Static</option><option value="bottom"' + (comSettings.footer === 'bottom' ? ' selected' : '') + '>Bottom</option><option value="fixed"' + (comSettings.footer === 'fixed' ? ' selected' : '') + '>Fixed</option></select>' +
			                '</div>' +
			            '</div>' +
		            '</div></div>';
		
		        if (hasFooter) {
		            setFooterPosition(comSettings.footer);
		        }
		    }
	
	        content += '</div>';
	
	        // Themes
	        content += '<h6 class="px-sidebar-header bg-primary darker b-y-1">THEMES</h6>';
	        content += '<div class="px-themes-list clearfix bg-primary">';
	
	        for (var i = 0, l = THEMES.length; i < l; i++) {
		        content += '<label class="px-themes-item">';
		
		        content += '<input type="radio" class="px-themes-toggler" name="px-current-theme" value="' + THEMES[i] + '"' + (comSettings.theme === THEMES[i] ? ' checked' : '') + '>';
		        content += '<img src="img/common/themes/' + THEMES[i] + '.png" class="px-themes-thumbnail">';
		        content += '<div class="px-themes-title font-weight-semibold"><span class="text-white">' + capitalizeAllLetters(THEMES[i], '-') + '</span><div class="bg-primary"></div></div>';
		
		        content += '</label>';
		    }
	
	        content += '</div>';
	
	        contentEl.innerHTML = content;
	        (container ? document.querySelector(container) : document.body).appendChild(sidebarEl);
	    }
	
	    function setLeftMenu(mdul, menuId) {
	        if(mdul){
	        	window.mdulId = $(mdul).attr('mdulId');
	        }
	
	        $(".px-nav-content > .px-nav-item").each(function(){
	            $(this).remove();
	        });
	
	        var navHtml = '';
	        var navLV = 0;
	        var navOpen = 0;
	        $(navData).each(function(idx, item){
	            if( window.mdulId == item.MDUL_ID ){
	            	navOpen ++;
	                if(navLV < item.LV) {
	                    navLV = item.LV;
	                }
	                if(navLV > item.LV){
	                    navHtml += '</ul>';
	                    navHtml += '</li>';
	                    navLV = 0;
	                }
	                if(item.LV == 1 && item.NODE_END_YN == 0){
	                    var expCss = (navOpen==1)?'px-open':'';
	                    navHtml += '<li class="px-nav-item px-nav-dropdown '+expCss+'">';
	                    navHtml += '<a href="#"><i class="px-nav-icon ion-ios-paper"></i><span class="px-nav-label">'+item.MENU_NM+'</span></a>';
	                    navHtml += '<ul class="px-nav-dropdown-menu">';
	                } else if(item.NODE_END_YN == 1){
	                    var activeCss = (menuId == item.MENU_ID)?'active':'';
	                    navHtml += '<li class="px-nav-item '+activeCss+'"><a href="#"><span class="px-nav-label" pgmId="'+item.PGM_ID+'" menuId="'+item.MENU_ID+'" onclick="pxCom.pgmOpen(this)">'+item.MENU_NM+'</span></a></li>';
	                }
	            } 
	        });
	        navHtml += '</ul>';
	        navHtml += '</li>';
	        //console.log(navHtml);
	        $(".px-nav-content").append(navHtml);
	
	    }
	
	    function pgmOpen(menu){
	        var tabNm = $(menu).text()||$(menu).attr('tabNm');
	        if(tabCheck(menu)) return;
	
	        $('.px-nav-content .px-nav-item').each(function(i, el){
	            $(el).removeClass('active');
	        });
	        $(menu).parent().parent().addClass('active');
	        
	        var tabId = "tid_"+new Date().getTime();
	        var tabHtml = '<li nm="'+tabNm+'"><a href="#'+tabId+'" data-toggle="tab">'+tabNm+'<button type="button" class="close" onclick="pxCom.tabClose(this)" tab="'+tabId+'" style="margin:-2px -5px 0px 10px" aria-label="Close">×</button></a></li>';
	        var paneHtml = '<div class="tab-pane" id="'+tabId+'"></div>';
	
	        $('#biz-pane').append(paneHtml);
	        $('#top-tab > ul').append(tabHtml).find('a').trigger('click');
	
	        $("#"+tabId).load("/pgm/"+$(menu).attr('pgmId'),"menuId="+$(menu).attr('menuId'),
	            function (responseText, textStatus, XMLHttpRequest) {
	            // XMLHttpRequest.responseText has the error info you want.
	            //console.log(responseText);
	            //console.log(textStatus);
	            //console.log(XMLHttpRequest);
				if (XMLHttpRequest.responseText.trim().startsWith("<html id='LOGIN'")) {
					console.log("redirect to login page");
					window.location.href = "/";
				}
	            if(XMLHttpRequest.status != '200'){
	                var msg = '프로그램 Load 오류' ;//XMLHttpRequest.responseText
	                var title = '';
	                toastr['info'](msg, title, {positionClass: 'toast-bottom-full-width'});
	            }
	        });
	    }
	
	    function tabCheck(menu) {
	        var flag = false;
	        var maxCnt = 10;
	        var menuNm = $(menu).attr('tabNm')||$(menu).html();
	        $('#top-tab > ul li').each(function () {
	            var tabNm = $(this).attr('nm');
	            if (tabNm == menuNm) {
	                $(this).find('a').trigger('click');
	                $('.px-nav-content .px-nav-item').each(function(i, el){
	                    $(el).removeClass('active');
	                });
	                $(menu).parent().parent().addClass('active');
	                flag = true;
	            }
	        });
	        if (!flag && $('#top-tab > ul li').not('.dropdown').size() > maxCnt) {
	            var msg = '메뉴는 최대 '+maxCnt+'개까지 열수 있습니다. 사용하지 않는 탭을 닫아주세요!';
	            var title = '';
	            toastr['info'](msg, title, {positionClass: 'toast-bottom-full-width'});
	            flag = true;
	        }
	        return flag;
	    }
	
	    function tabClose(el) {
	        var $el = $(el);
	        var $bizWrap = $('#' + $el.attr('tab'));
	        var isActive = $el.closest('li').hasClass('active');
	        var idx = $el.closest('li').index();
	        var $preLi = $(el).closest('ul').find('li').eq(idx - 1);
	        $bizWrap.find("div[id*='tGrid']").each(function(idx, item){
	        	Grids[$(item).find("div:first-child").attr("id")].Dispose();
	        });
	        $bizWrap.remove();
	        $el.closest('li').remove();
	        if (isActive) {
	            $preLi.find('a').trigger('click');
	        }
	    }    
	    
	    function setLocation(programId, menuId) {
	        if(!navData) return;
	        var $el = $("#"+programId).find('.tab-pane').first();
	        var $tmpl = $(document.createDocumentFragment());
	
	        $(navData).each(function(idx, item){
	            if( menuId == item.MENU_ID ){
	                var locTxt = item.NM_PATH;
	                var locArr = locTxt.split('|');
	                var locArrLen = locArr.length;
	
	                $tmpl.append('<div class="row">'
	                    +'<ol class="breadcrumb page-breadcrumb">'
	                    +'<h1><i class="ion-android-locate"></i>&nbsp;&nbsp;'+locArr[locArrLen-1]+'</h1>'
	                    +'<span class="pull-right">'
	                    +'<a href="#" onclick="pxCom.tabCheck(\'home\')">Home</a>'
	                    +' / '+item.MDUL_NM
	                    +'</span>'
	                    +'</ol>'
	                    +'</div>');
	
	                $(locArr).each(function(idx){
	                    if(idx == locArrLen-1){
	                        $tmpl.find('.pull-right').append(' / <span class="active">'+locArr[idx]+'</span>');
	                    } else if(idx > 0){
	                        $tmpl.find('.pull-right').append(' / '+locArr[idx]);
	                    }
	                });
	                return false;
	            }
	        });
	
	        if($el.find('.breadcrumb')){
	            $("#"+programId).find('.tab-pane div:first-child').first().html($tmpl.find('.row').html());
	        } else {
	            $el.prepend($tmpl);
	        }
	    }
	    
	    // Return
	    return {
	        COLORS: COLORS,
	
	        shuffle:         shuffle,
	        getRandomData:   getRandomData,
	        getRandomColors: getRandomColors,
	
	        initialize:        initialize,
	        initializeBgs:     initializeBgs,
	        initializeSidebar: initializeSidebar,
	        destroyBgs:        destroyBgs,
	
	        loadTheme: loadTheme,
	        loadRtl:   loadRtl,
	
	        setLeftMenu:   setLeftMenu,
	        setLocation:   setLocation,
	        pgmOpen:       pgmOpen,
	        tabCheck:      tabCheck,
	        tabClose:      tabClose
	    };
	})();
	
	return pxCom;

}));