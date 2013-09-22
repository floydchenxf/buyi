// usage: log('inside coolFunc', this, arguments);
// paulirish.com/2009/log-a-lightweight-wrapper-for-consolelog/
window.log = function f(){ log.history = log.history || []; log.history.push(arguments); if(this.console) { var args = arguments, newarr; args.callee = args.callee.caller; newarr = [].slice.call(args); if (typeof console.log === 'object') log.apply.call(console.log, console, newarr); else console.log.apply(console, newarr);}};

/* make it safe to use console.log always
(function(a){function b(){}for(var c="assert,count,debug,dir,dirxml,error,exception,group,groupCollapsed,groupEnd,info,log,markTimeline,profile,profileEnd,time,timeEnd,trace,warn".split(","),d;!!(d=c.pop());){a[d]=a[d]||b;}})
(function(){try{console.log();return window.console;}catch(a){return (window.console={});}}());
*/
// see https://musculahq.appspot.com/app error logging saas

// place any jQuery/helper plugins in here, instead of separate, slower script files.

/*!
* jQuery Cookie Plugin
* https://github.com/carhartl/jquery-cookie
*
* Copyright 2011, Klaus Hartl
* Dual licensed under the MIT or GPL Version 2 licenses.
* http://www.opensource.org/licenses/mit-license.php
* http://www.opensource.org/licenses/GPL-2.0
*/
(function($) {
	$.cookie = function(key, value, options) {

		// key and at least value given, set cookie...
		if (arguments.length > 1 && (!/Object/.test(Object.prototype.toString.call(value)) || value === null || value === undefined)) {
			options = $.extend({}, options);

			if (value === null || value === undefined) {
				options.expires = -1;
			}

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setDate(t.getDate() + days);
			}

			value = String(value);

			return (document.cookie = [
				encodeURIComponent(key), '=', options.raw ? value : encodeURIComponent(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path ? '; path=' + options.path : '',
				options.domain ? '; domain=' + options.domain : '',
				options.secure ? '; secure' : ''
			].join(''));
		}

		// key and possibly options given, get cookie...
		options = value || {};
		var decode = options.raw ? function(s) { return s; } : decodeURIComponent;

		var pairs = document.cookie.split('; ');
		for (var i = 0, pair; pair = pairs[i] && pairs[i].split('='); i++) {
			if (decode(pair[0]) === key) return decode(pair[1] || ''); // IE saves cookies with empty string as "c; ", e.g. without "=" as opposed to EOMB, thus pair[1] may be undefined
		}
		return null;
	};
})(jQuery);

/**
 * jQuery HTML5form by http://www.matiasmancini.com.ar/jquery-plugin-ajax-form-validation-html5.html
 */
// (function($){$.fn.html5form=function(options){$(this).each(function(){var defaults={async:true,method:$(this).attr('method'),responseDiv:null,labels:'show',colorOn:'#000000',colorOff:'#a1a1a1',action:$(this).attr('action'),messages:false,emptyMessage:false,emailMessage:false,allBrowsers:true};var opts=$.extend({},defaults,options);if(!opts.allBrowsers){if($.browser.webkit&&parseInt($.browser.version)>=533){return false}if($.browser.mozilla&&parseInt($.browser.version)>=2){return false}if($.browser.opera&&parseInt($.browser.version)>=11){return false}}var form=$(this);var required=new Array();var email=new Array();function fillInput(input){if(input.attr('placeholder')&&input.attr('type')!='password'){input.val(input.attr('placeholder'));input.css('color',opts.colorOff)}else{if(!input.data('value')){if(input.val()!=''){input.data('value',input.val())}}else{input.val(input.data('value'))}input.css('color',opts.colorOn)}}if(opts.labels=='hide'){$(this).find('label').hide()}$.each($('select',this),function(){$(this).css('color',opts.colorOff);$(this).change(function(){$(this).css('color',opts.colorOn)})});$.each($(':input:visible:not(:button, :submit, :radio, :checkbox, select)',form),function(i){fillInput($(this));if(this.getAttribute('required')!=null){required[i]=$(this)}if(this.getAttribute('type')=='email'){email[i]=$(this)}$(this).bind('focus',function(ev){ev.preventDefault();if(this.value==$(this).attr('placeholder')){if(this.getAttribute('type')!='url'){$(this).attr('value','')}}$(this).css('color',opts.colorOn)});$(this).bind('blur',function(ev){ev.preventDefault();if(this.value==''){fillInput($(this))}else{if((this.getAttribute('type')=='url')&&($(this).val()==$(this).attr('placeholder'))){fillInput($(this))}}});$('textarea').filter(this).each(function(){if($(this).attr('maxlength')>0){$(this).keypress(function(ev){var cc=ev.charCode||ev.keyCode;if(cc==37||cc==39){return true}if(cc==8||cc==46){return true}if(this.value.length>=$(this).attr('maxlength')){return false}else{return true}})}})});$.each($('input:submit, input:image, input:button',this),function(){$(this).bind('click',function(ev){var emptyInput=null;var emailError=null;var input=$(':input:visible:not(:button, :submit, :radio, :checkbox, select)',form);$(required).each(function(key,value){if(value==undefined){return true}if(($(this).val()==$(this).attr('placeholder'))||($(this).val()=='')){emptyInput=$(this);if(opts.emptyMessage){$(opts.responseDiv).html('<p>'+opts.emptyMessage+'</p>')}else if(opts.messages=='es'){$(opts.responseDiv).html('<p>El campo '+$(this).attr('title')+' es requerido.</p>')}else if(opts.messages=='en'){$(opts.responseDiv).html('<p>The '+$(this).attr('title')+' field is required.</p>')}else if(opts.messages=='it'){$(opts.responseDiv).html('<p>Il campo '+$(this).attr('title')+' &eacute; richiesto.</p>')}else if(opts.messages=='de'){$(opts.responseDiv).html('<p>'+$(this).attr('title')+' ist ein Pflichtfeld.</p>')}else if(opts.messages=='fr'){$(opts.responseDiv).html('<p>Le champ '+$(this).attr('title')+' est requis.</p>')}else if(opts.messages=='nl'||opts.messages=='be'){$(opts.responseDiv).html('<p>'+$(this).attr('title')+' is een verplicht veld.</p>')}else if(opts.messages=='br'){$(opts.responseDiv).html('<p>O campo '+$(this).attr('title')+' &eacute; obrigat&oacute;rio.</p>')}else if(opts.messages=='br'){$(opts.responseDiv).html("<p>Insira um email v&aacute;lido por favor.</p>")}return false}return emptyInput});$(email).each(function(key,value){if(value==undefined){return true}if($(this).val().search(/[\w-\.]{3,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/i)){emailError=$(this);return false}return emailError});if(!emptyInput&&!emailError){$(input).each(function(){if($(this).val()==$(this).attr('placeholder')){$(this).val('')}});if(opts.async){var formData=$(form).serialize();$.ajax({url:opts.action,type:opts.method,data:formData,success:function(data){if(opts.responseDiv){$(opts.responseDiv).html(data)}$(input).val('');$.each(form[0],function(){fillInput($(this).not(':hidden, :button, :submit, :radio, :checkbox, select'));$('select',form).each(function(){$(this).css('color',opts.colorOff);$(this).children('option:eq(0)').attr('selected','selected')});$(':radio, :checkbox',form).removeAttr('checked')})}})}else{$(form).submit()}}else{if(emptyInput){$(emptyInput).focus().select()}else if(emailError){if(opts.emailMessage){$(opts.responseDiv).html('<p>'+opts.emailMessage+'</p>')}else if(opts.messages=='es'){$(opts.responseDiv).html('<p>Ingrese una direcci&oacute;n de correo v&aacute;lida por favor.</p>')}else if(opts.messages=='en'){$(opts.responseDiv).html('<p>Please type a valid email address.</p>')}else if(opts.messages=='it'){$(opts.responseDiv).html("<p>L'indirizzo e-mail non &eacute; valido.</p>")}else if(opts.messages=='de'){$(opts.responseDiv).html("<p>Bitte eine g&uuml;ltige E-Mail-Adresse eintragen.</p>")}else if(opts.messages=='fr'){$(opts.responseDiv).html("<p>Entrez une adresse email valide s&rsquo;il vous plait.</p>")}else if(opts.messages=='nl'||opts.messages=='be'){$(opts.responseDiv).html('<p>Voert u alstublieft een geldig email adres in.</p>')}$(emailError).select()}else{alert('Unknown Error')}}return false})})})}})(jQuery);


/*
 * jqModal - Minimalist Modaling with jQuery
 *   (http://dev.iceburg.net/jquery/jqModal/)
 *
 * Copyright (c) 2007,2008 Brice Burgess <bhb@iceburg.net>
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 * 
 * $Version: 03/01/2009 +r14
 * 回调函数参数说明，例如 onLoad(h)：
 * h.w: (jQuery object) The dialog element
 * h.c: (object) The config object (dialog's parameters)
 * h.o: (jQuery object) The overlay
 * h.t: (DOM object) The triggering element
 */
(function($) {
$.fn.jqm=function(o){
var p={
overlay: 50,
overlayClass: 'jqmOverlay',
closeClass: 'jqmClose',
trigger: '.jqModal',
ajax: F,
ajaxText: '',
target: F,
modal: F,
toTop: F,
onShow: F,
onHide: F,
onLoad: F // {w:jquery包的dialog容器, t: the trigger element, o: 泽当·遮挡层, c: options object}
};
return this.each(function(){if(this._jqm)return H[this._jqm].c=$.extend({},H[this._jqm].c,o);s++;this._jqm=s;
H[s]={c:$.extend(p,$.jqm.params,o),a:F,w:$(this).addClass('jqmID'+s),s:s};
if(p.trigger)$(this).jqmAddTrigger(p.trigger);
});};

$.fn.jqmAddClose=function(e){return hs(this,e,'jqmHide');};
$.fn.jqmAddTrigger=function(e){return hs(this,e,'jqmShow');};
$.fn.jqmShow=function(t){return this.each(function(){t=t||window.event;$.jqm.open(this._jqm,t);});};
$.fn.jqmHide=function(t){return this.each(function(){t=t||window.event;$.jqm.close(this._jqm,t)});};

$.jqm = {
hash:{},
open:function(s,t){var h=H[s],c=h.c,cc='.'+c.closeClass,z=(parseInt(h.w.css('z-index'))),z=(z>0)?z:3000,o=$('<div></div>').css({height:'100%',width:'100%',position:'fixed',left:0,top:0,'z-index':z-1,opacity:c.overlay/100});if(h.a)return F;h.t=t;h.a=true;h.w.css('z-index',z);
 if(c.modal) {if(!A[0])L('bind');A.push(s);}
 else if(c.overlay > 0)h.w.jqmAddClose(o);
 else o=F;

 h.o=(o)?o.addClass(c.overlayClass).prependTo('body'):F;
 if(ie6){$('html,body').css({height:'100%',width:'100%'});if(o){o=o.css({position:'absolute'})[0];for(var y in {Top:1,Left:1})o.style.setExpression(y.toLowerCase(),"(_=(document.documentElement.scroll"+y+" || document.body.scroll"+y+"))+'px'");}}

 if(c.ajax) {var r=c.target||h.w,u=c.ajax,r=(typeof r == 'string')?$(r,h.w):$(r),u=(u.substr(0,1) == '@')?$(t).attr(u.substring(1)):u;
  r.html(c.ajaxText).load(u,function(){if(c.onLoad)c.onLoad.call(this,h);if(cc)h.w.jqmAddClose($(cc,h.w));e(h);});}
 else if(cc)h.w.jqmAddClose($(cc,h.w));

 if(c.toTop&&h.o)h.w.before('<span id="jqmP'+h.w[0]._jqm+'"></span>').insertAfter(h.o);	
 (c.onShow)?c.onShow(h):h.w.show();e(h);return F;
},
close:function(s){var h=H[s];if(!h.a)return F;h.a=F;
 if(A[0]){A.pop();if(!A[0])L('unbind');}
 if(h.c.toTop&&h.o)$('#jqmP'+h.w[0]._jqm).after(h.w).remove();
 if(h.c.onHide)h.c.onHide(h);else{h.w.hide();if(h.o)h.o.remove();} return F;
},
params:{}};
var s=0,H=$.jqm.hash,A=[],ie6=$.browser.msie&&($.browser.version == "6.0"),F=false,
i=$('<iframe src="javascript:false;document.write(\'\');" class="jqm"></iframe>').css({opacity:0}),
e=function(h){if(ie6)if(h.o)h.o.html('<p style="width:100%;height:100%"/>').prepend(i);else if(!$('iframe.jqm',h.w)[0])h.w.prepend(i); f(h);},
f=function(h){try{$(':input:visible',h.w)[0].focus();}catch(_){}},
L=function(t){$(this)[t]("keypress",m)[t]("keydown",m)[t]("mousedown",m);},
m=function(e){var h=H[A[A.length-1]],r=(!$(e.target).parents('.jqmID'+h.s)[0]);if(r)f(h);return !r;},
hs=function(w,t,c){return w.each(function(){var s1=this._jqm;var tt=$(t);
var clicked=function(){if(!this[c]){this[c]=[];/*fixme:may not pushed*/this[c].push(s1);}for(var i in {jqmShow:1,jqmHide:1})for(var s in this[i])if(H[this[i][s]])H[this[i][s]].w[i](this);return F;};
if(tt.selector){tt.live('click',clicked);}else{tt.click(clicked);}});};
})(jQuery);

/*
 * jqDnR - Minimalistic Drag'n'Resize for jQuery.
 *
 * Copyright (c) 2007 Brice Burgess <bhb@iceburg.net>, http://www.iceburg.net
 * Licensed under the MIT License:
 * http://www.opensource.org/licenses/mit-license.php
 * 
 * @see http://dev.iceburg.net/jquery/jqDnR/ for example
 * $Version: 2007.08.19 +r2
 */
(function($){
$.fn.jqDrag=function(h){return i(this,h,'d');};
$.fn.jqResize=function(h){return i(this,h,'r');};
$.jqDnR={dnr:{},e:0,
drag:function(v){
 if(M.k == 'd')E.css({left:M.X+v.pageX-M.pX,top:M.Y+v.pageY-M.pY});
 else E.css({width:Math.max(v.pageX-M.pX+M.W,0),height:Math.max(v.pageY-M.pY+M.H,0)});
  return false;},
stop:function(){E.css('opacity',M.o);$(this).unbind('mousemove',J.drag).unbind('mouseup',J.stop);}
};
var J=$.jqDnR,M=J.dnr,E=J.e,
i=function(e,h,k){return e.each(function(){h=(h)?$(h,e):e;
 h.bind('mousedown',{e:e,k:k},function(v){var d=v.data,p={};E=d.e;
 // attempt utilization of dimensions plugin to fix IE issues
 if(E.css('position') != 'relative'){try{E.position(p);}catch(e){}}
 M={X:p.left||f('left')||0,Y:p.top||f('top')||0,W:f('width')||E[0].scrollWidth||0,H:f('height')||E[0].scrollHeight||0,pX:v.pageX,pY:v.pageY,k:d.k,o:E.css('opacity')};
 E.css({opacity:0.8});$(this).mousemove($.jqDnR.drag).mouseup($.jqDnR.stop);
 return false;
 });
});},
f=function(k){return parseInt(E.css(k))||false;};
})(jQuery);

/**
* hoverIntent is similar to jQuery's built-in "hover" function except that
* instead of firing the onMouseOver event immediately, hoverIntent checks
* to see if the user's mouse has slowed down (beneath the sensitivity
* threshold) before firing the onMouseOver event.
* 
* hoverIntent r6 // 2011.02.26 // jQuery 1.5.1+
* <http://cherne.net/brian/resources/jquery.hoverIntent.html>
* 
* hoverIntent is currently available for use in all personal or commercial 
* projects under both MIT and GPL licenses. This means that you can choose 
* the license that best suits your project, and use it accordingly.
* 
* // basic usage (just like .hover) receives onMouseOver and onMouseOut functions
* $("ul li").hoverIntent( showNav , hideNav );
* 
* // advanced usage receives configuration object only
* $("ul li").hoverIntent({
*	sensitivity: 7, // number = sensitivity threshold (must be 1 or higher)
*	interval: 100,   // number = milliseconds of polling interval
*	over: showNav,  // function = onMouseOver callback (required)
*	timeout: 0,   // number = milliseconds delay before onMouseOut function call
*	out: hideNav	// function = onMouseOut callback (required)
* });
* 
* @param  f  onMouseOver function || An object with configuration options
* @param  g  onMouseOut function  || Nothing (use configuration options object)
* @author	Brian Cherne brian(at)cherne(dot)net
*/
(function($) {
	$.fn.hoverIntent = function(f,g) {
		// default configuration options
		var cfg = {
			sensitivity: 7,
			interval: 100,
			timeout: 0
		};
		// override configuration options with user supplied object
		cfg = $.extend(cfg, g ? { over: f, out: g } : f );

		// instantiate variables
		// cX, cY = current X and Y position of mouse, updated by mousemove event
		// pX, pY = previous X and Y position of mouse, set by mouseover and polling interval
		var cX, cY, pX, pY;

		// A private function for getting mouse position
		var track = function(ev) {
			cX = ev.pageX;
			cY = ev.pageY;
		};

		// A private function for comparing current and previous mouse position
		var compare = function(ev,ob) {
			ob.hoverIntent_t = clearTimeout(ob.hoverIntent_t);
			// compare mouse positions to see if they've crossed the threshold
			if ( ( Math.abs(pX-cX) + Math.abs(pY-cY) ) < cfg.sensitivity ) {
				$(ob).unbind("mousemove",track);
				// set hoverIntent state to true (so mouseOut can be called)
				ob.hoverIntent_s = 1;
				return cfg.over.apply(ob,[ev]);
			} else {
				// set previous coordinates for next time
				pX = cX; pY = cY;
				// use self-calling timeout, guarantees intervals are spaced out properly (avoids JavaScript timer bugs)
				ob.hoverIntent_t = setTimeout( function(){compare(ev, ob);} , cfg.interval );
			}
		};

		// A private function for delaying the mouseOut function
		var delay = function(ev,ob) {
			ob.hoverIntent_t = clearTimeout(ob.hoverIntent_t);
			ob.hoverIntent_s = 0;
			return cfg.out.apply(ob,[ev]);
		};

		// A private function for handling mouse 'hovering'
		var handleHover = function(e) {
			// copy objects to be passed into t (required for event object to be passed in IE)
			var ev = jQuery.extend({},e);
			var ob = this;

			// cancel hoverIntent timer if it exists
			if (ob.hoverIntent_t) { ob.hoverIntent_t = clearTimeout(ob.hoverIntent_t); }

			// if e.type == "mouseenter"
			if (e.type == "mouseenter") {
				// set "previous" X and Y position based on initial entry point
				pX = ev.pageX; pY = ev.pageY;
				// update "current" X and Y position based on mousemove
				$(ob).bind("mousemove",track);
				// start polling interval (self-calling timeout) to compare mouse coordinates over time
				if (ob.hoverIntent_s != 1) { ob.hoverIntent_t = setTimeout( function(){compare(ev,ob);} , cfg.interval );}

			// else e.type == "mouseleave"
			} else {
				// unbind expensive mousemove event
				$(ob).unbind("mousemove",track);
				// if hoverIntent state is true, then call the mouseOut function after the specified delay
				if (ob.hoverIntent_s == 1) { ob.hoverIntent_t = setTimeout( function(){delay(ev,ob);} , cfg.timeout );}
			}
		};

		// bind the function to the two event listeners
		return this.bind('mouseenter',handleHover).bind('mouseleave',handleHover);
	};
})(jQuery);

(function($){
	$.stopPropagation = function(event) {
		event=event?event:window.event;
		if ($.browser.msie) {
			event.cancelBubble = true;
		} else {
			event.stopPropagation();
		}
	}
})(jQuery);

(function($) {

	function calcPositionCss (target, lr_padding)
	{
	  var op = $(target).offsetParent().offset();
	  var ot = $(target).offset();
	  if(!ot)
		  ot = {top:0,left:0};

	  if(!lr_padding)
		  lr_padding = 4;
	  return {
		top: ot.top - op.top,
		left: ot.left - op.left + lr_padding,
		width: $(target).width() - lr_padding
	  };
	};

	/**
	 * 重新计算placehoder的位置
	 */
	$.fn.placeholder = function() {
		return this.each(function() {
			var $this = $(this);
			var target = $this.data('target');
			if(target)
				$this.css(calcPositionCss(target));
		});
	}

	// @todo Document this.
  $.extend($,{ placeholder: {
	  browser_supported: function() {
		return this._supported !== undefined ?
		  this._supported :
		  ( this._supported = !!('placeholder' in $('<input type="text">')[0]) );
	  },
	  shim: function(opts) {
		var config = {
		  color: '#888',
		  cls: 'placeholder',
		  lr_padding:4,
		  selector: 'input[placeholder], textarea[placeholder]'
		};
		$.extend(config,opts);
		!this.browser_supported() && $(config.selector)._placeholder_shim(config);
	  }
  }});

  $.extend($.fn,{
	_placeholder_shim: function(config) {
	  return this.each(function() {
		var $this = $(this);
		
		if( $this.data('placeholder') ) {
		  var $ol = $this.data('placeholder');
		  $ol.css(calcPositionCss($this));
		  return true;
		}

		var possible_line_height = {};
		if( !$this.is('textarea') && $this.css('height') != 'auto') {
		  possible_line_height = { whiteSpace: 'nowrap' };
		  if($this.height()>0)
			  possible_line_height.lineHeight = $this.css('height');
		}

		var ol = $('<label />')
		  .text($this.attr('placeholder'))
		  .addClass(config.cls)
		  .css($.extend({
			'position':'absolute',
			'display': 'inline',
			'float':'none',
			'overflow':'hidden',
			'textAlign': 'left',
			'color': config.color,
			'cursor': 'text',
			'paddingTop': $this.css('padding-top'),
			'paddingLeft': $this.css('padding-left'),
			'fontSize': $this.css('font-size'),
			'fontFamily': $this.css('font-family'),
			'fontStyle': $this.css('font-style'),
			'fontWeight': $this.css('font-weight'),
			'textTransform': $this.css('text-transform'),
			'zIndex': 99
		  }, possible_line_height))
		  .css(calcPositionCss(this))
		  .attr('for', this.id)
		  .data('target',$this)
		  .click(function(){
			$(this).data('target').focus()
		  })
		  .insertBefore(this);
		$this
		  .data('placeholder',ol)
		  .focus(function(){
			ol.hide();
		  }).blur(function() {
			ol[$this.val().length ? 'hide' : 'show']();
		  });
		ol[$this.val().length ? 'hide' : 'show']();
		$(window)
		  .resize(function() {
			var $target = ol.data('target')
			ol.css(calcPositionCss($target))
		  });
	  });
	}
  });
})(jQuery);

/**
 * get text selection range: {start, end}
 */
(function($) {
	$.fn.getSelection = function() {
		var start = 0, end = 0, normalizedValue, range,
			textInputRange, len, endRange;
		var input = this[0];

		if (typeof input.selectionStart == "number" && typeof input.selectionEnd == "number") {
			start = input.selectionStart;
			end = input.selectionEnd;
		} else {
			range = document.selection.createRange();

			if (range && range.parentElement() == input) {
				len = input.value.length;
				normalizedValue = input.value.replace(/\r\n/g, "\n");

				// Create a working TextRange that lives only in the input
				textInputRange = input.createTextRange();
				textInputRange.moveToBookmark(range.getBookmark());

				// Check if the start and end of the selection are at the very end
				// of the input, since moveStart/moveEnd doesn't return what we want
				// in those cases
				endRange = input.createTextRange();
				endRange.collapse(false);

				if (textInputRange.compareEndPoints("StartToEnd", endRange) > -1) {
					start = end = len;
				} else {
					start = -textInputRange.moveStart("character", -len);
					start += normalizedValue.slice(0, start).split("\n").length - 1;

					if (textInputRange.compareEndPoints("EndToEnd", endRange) > -1) {
						end = len;
					} else {
						end = -textInputRange.moveEnd("character", -len);
						end += normalizedValue.slice(0, end).split("\n").length - 1;
					}
				}
			}
		}
		return { start: start, end: end};
	}

	$.fn.insertAtCaret = function(text, offset) {
		if(!offset)offset = 0;

		var sel = this.getSelection(), val = this.val();
		this.val(val.substring(0, sel.start + offset) + text + val.substring(sel.end));
		sel.start += text.length + offset;

		var thiz = this[0];
		var scrollPos = thiz.scrollTop;
		if ((thiz.selectionStart || thiz.selectionStart == '0')) {
			thiz.selectionStart = sel.start;
			thiz.selectionEnd = sel.start;
			thiz.focus();
		} else if (document.selection) { 
			thiz.focus();
			var range = document.selection.createRange();
			range.moveStart ('character', -thiz.value.length);
			range.moveStart ('character', sel.start);
			range.moveEnd ('character', 0);
			range.select();
		}
		thiz.scrollTop = scrollPos;

		this.trigger('mousedown');
		this.trigger('mouseup');
	}
})(jQuery);

/**
 * retrieves caret coordinates: {top, offset}
 */
(function($) {
	var primaryStyles = ['fontFamily', 'fontSize', 'fontWeight', 'fontVariant', 'fontStyle',
					'paddingLeft', 'paddingTop', 'paddingBottom', 'paddingRight',
					'marginLeft', 'marginTop', 'marginBottom', 'marginRight',
					'borderLeftColor', 'borderTopColor', 'borderBottomColor', 'borderRightColor',
					'borderLeftStyle', 'borderTopStyle', 'borderBottomStyle', 'borderRightStyle',
					'borderLeftWidth', 'borderTopWidth', 'borderBottomWidth', 'borderRightWidth',
					'line-height', 'outline'];

	/**
	 * @param offset 偏移几个字符，可负数
	 * @param withFontHeight 若true，则top会加上fontSize行高
	 * @return {top, left}
	 */
	$.fn.getCaretOffset = function(offset, withFontHeight) {
		var thiz = this[0], target = this, elementOffset = target.offset();
		if(!offset)offset = 0;

		// IE has easy way to get caret offset position
		if ($.browser.msie) {
			// must get focus first
			thiz.focus();
			var range = document.selection.createRange();
			return {
				left: range.boundingLeft,
				top: parseInt(range.boundingTop) + thiz.scrollTop
					+ document.documentElement.scrollTop + parseInt(target.css("fontSize"))
			};
		}

		var shadow = this.data('caret_pos_shadow');
		if(!shadow) {
			shadow = $('<div class="input_shadow"/>').css({
				'position': 'absolute',
				'white-space': 'pre-wrap',
				'*white-space': 'pre',
				'word-wrap': 'break-word',
				'overflow-x': 'hidden',
				'overflow-y': 'auto',
				'visibility': 'hidden'
			}).appendTo(document.body);
			this.data('caret_pos_shadow', shadow);

			// clone primary static styles to imitate textarea
			$.each(primaryStyles, function(index, styleName) {
				var styleVal = target.css(styleName);
				if (!!styleVal) {
					shadow.css(styleName, styleVal);
				}
			});
		}
		shadow.empty();
		// caculate width and height
		shadow.css({
			'top': elementOffset.top,
			'left': elementOffset.left,
			'width': target.width(),
			'height': target.height()
		});

		var value = target.val(), cursorPos = target.getSelection().start + offset;
		var beforeText = value.substring(0, cursorPos),
			afterText = value.substring(cursorPos);

		var before = $('<span class="shadow_cur_before"/>').text(beforeText),
			focus = $('<span class="shadow_cur_focus">&nbsp;</span>'),
			after = $('<span class="shadow_cur_after"/>').text(afterText);

		shadow.append(before).append(focus).append(after);
		var focusOffset = focus.offset();
		return {
			top: focusOffset.top - thiz.scrollTop
			// calculate and add the font height except Firefox
			+ (withFontHeight ?parseInt(target.css("fontSize")) : 0),
			left: focusOffset.left - thiz.scrollLeft
		};
	}
})(jQuery);

dynamicScripts_last.placeholder = function() {
	$.placeholder.shim();
	$('.placeholder').mouseup(function(e){
		e.stopPropagation();
	});
};

/*
var path = new $.path.bezier({
	start: {x:10, y:10, angle: 20, length: 0.3},
	end: {x:20, y:30, angle: -20, length: 0.2}
})
$("myobj").animate({path: path}, duration)
*/
(function($){
	$.path = {};
	var V = {
			rotate: function(p, degrees) {
				var radians = degrees * 3.141592654 / 180
				var c = Math.cos(radians), s = Math.sin(radians)
				return [c*p[0] - s*p[1], s*p[0] + c*p[1] ]
			},
			scale: function(p, n) {
				return [n*p[0], n*p[1]]
			},
			add: function(a, b) {
				return [a[0]+b[0], a[1]+b[1]]
			},
			minus: function(a, b) {
				return [a[0]-b[0], a[1]-b[1]]
			}
	};
	$.path.bezier = function( params ) {
		params.start = $.extend({angle: 0, length: 0.3333}, params.start )
		params.end = $.extend({angle: 0, length: 0.3333}, params.end )
		this.p1 = [params.start.x, params.start.y];
		this.p4 = [params.end.x, params.end.y];
		var v14 = V.minus(this.p4, this.p1)
		var v12 = V.scale(v14, params.start.length)
		v12 = V.rotate(v12, params.start.angle)
		this.p2 = V.add(this.p1, v12)
		var v41 = V.scale(v14, -1)
		var v43 = V.scale(v41, params.end.length)
		v43 = V.rotate(v43, params.end.angle)
		this.p3 = V.add(this.p4, v43)
		this.f1 = function(t) { return (t*t*t); }
		this.f2 = function(t) { return (3*t*t*(1-t)); }
		this.f3 = function(t) { return (3*t*(1-t)*(1-t)); }
		this.f4 = function(t) { return ((1-t)*(1-t)*(1-t)); }
		/* p from 0 to 1 */
		this.css = function(p) {
			var f1 = this.f1(p), f2 = this.f2(p), f3 = this.f3(p), f4=this.f4(p)
			var x = this.p1[0]*f1 + this.p2[0]*f2 +this.p3[0]*f3 + this.p4[0]*f4;
			var y = this.p1[1]*f1 + this.p2[1]*f2 +this.p3[1]*f3 + this.p4[1]*f4;
			return {top: y + "px", left: x + "px"}
		}
	};
	$.path.arc = function(params) {
		for(var i in params)
			this[i] = params[i];
		this.dir = this.dir || 1
		while(this.start > this.end && this.dir > 0)
			this.start -= 360;
		while(this.start < this.end && this.dir < 0)
			this.start += 360;
		this.css = function(p) {
			var a = this.start * (p ) + this.end * (1-(p ))
			a = a * 3.1415927 / 180 // to radians
			var x = Math.sin(a) * this.radius + this.center[0]
			var y = Math.cos(a) * this.radius + this.center[1]
			return {top: y + "px", left: x + "px"}
		}
	};
	$.fx.step.path = function(fx){
		var css = fx.end.css(1 - fx.pos)
		for(var i in css)
			fx.elem.style[i] = css[i];
	};
})(jQuery);