var globalcurrentopenTag="";
$.ajaxSetup({
	cache:		false,
	timeout:	30000
});

// logExceptions start: FIXME remove in production
/**
 * Enhancd Javascript logging and exception handler.
 * 
 * Copyright 2008 Red Innovation Ltd.
 * 
 * @author Mikko Ohtamaa
 * @license 3-clause BSD
 * 
 */

function doCloseChoose( elementselector){
	var $=jQuery;
	$(elementselector).css("display","none");
	return true;
}
function doOpenChoose( elementselector){
	var $=jQuery;
	$(elementselector).css("display","block");
	return true;
}

// Browser specific logging output initialization
// Supports Firefox/Firebug. Other (Opera) can be hooked in here.
if(!console) {
	// Install dummy functions, so that logging does not break the code if
	// Firebug is not present
    var console = {};
    console.log = function(msg) {};
    console.info = function(msg) {};
    console.warn = function(msg) {};
} else {
    // console.log provided by Firefox + Firebug
}

/**
 * Try print human digestable exception stack trace to Firebug console.
 * 
 * http://developer.mozilla.org/en/docs/Core_JavaScript_1.5_Reference:Global_Objects:Error
 * 
 * @param e:
 *            Error
 */
function printStackTrace(e) {
	var msg = e.name + ":" + e.message;

	if (e.fileName) {
		msg += " at " + e.fileName + ":" + e.lineNumber;
	}
	console.log(msg);

	if (e.stack) {
		// Extract Firefox stack information. This tells how you ended up
		// to the exception in the first place. I didn't find
		// instructions how to parse this stuff.
		console.log(e.stack);
	}
}

/**
 * Decorate function so that exceptions falling through are printed always.
 * 
 * Returns a decorated function which will be used instead of the normal
 * function. The decorated function has preplaced try ... catch block which will
 * not let through any exceptions silently or without logging. Even though there
 * is an exception it is normally throw upwards in the stack after logging.
 * 
 * @param func:
 *            Javascript function reference
 */
function logExceptions(func) {

	var orignal = func;

	decorated = function() {
		try {
			orignal.apply(this, arguments);
		} catch(exception) {
			printStackTrace(exception);
			throw exception;
		}
	}

	return decorated;
}
// logExceptions end: FIXME remove in production

// TODO depricated
function userBindWnd(w, h, url, redirectUrl) {
	if(url==null || url==""){
		return;
	}
	if(redirectUrl==null || redirectUrl==""){
		redirectUrl = window.location.href;
	}
	url += ((url.indexOf('?') < 0) ? '?':'&') + 'redirect=' + redirectUrl;
		
// window.open(url, 'new', 'height=' + h + ',innerHeight='+ h + ',width='
// + w + ',innerWidth=' + w + ',top=' + 100 + ',left=' + 200
// +
// ',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
	window.location = url;
}


/**
 * 将同步的anchor转为异步的jquery插件
 */
(function($) {
	$.asyncLoad = function(url, done) {
		$.ajax({
			type : "GET",
			url : url
		}).done(
			// FIXME logon dialog, error handler
			logExceptions(done));
	}

	/**
	 * 异步初始化加载，就是说，调用时立即加载
	 */
	$.fn.asyncInit = function(url,isAppend, showLogon) {
		var _this = this;
		$.ajax({
			type : "GET",
			url : url
		}).done(logExceptions(function(data) {
			// hide logon dialog
			if(!showLogon && $(data).find('#logon_form').length > 0)
				return;
			if(!isAppend){
				_this.empty();
			}
			_this.append(data);
		}));
	}

	/**
	 * 异步加载绑定，click时触发加载
	 * 
	 * @param container
	 *            支持jquery对象，例如$('.myclass');或者string表达式，例如
	 *            "$(this).parent.next()"
	 */
	$.fn.asyncBind = function(container, options) {
		var selected = $(this.selector);
		var selectedAttr = this.selector.replace(/[\.\[\]\=~ #,>\+\*]/g,'') + "_binded";
		this.each(function(){
			var container0 = container;
			if(typeof container0 === 'string' && container0!="NULL") {
				container0 = eval(container0);
			}

			if(!this[selectedAttr]) {
				this[selectedAttr] = true;
				$(this).bind('click',function (e){
					$.asyncBindFunction(this, container0, options, selected);return false;});
			}
		});
	}

	$.fn.asyncBind.defaults = {
			'enableActive':true, // 是否开启 'active'这个css class到当前点击的元素
			'history':true, // 是否开启浏览器history功能
			'onBefore':null, // onBefore(this)发起异步请求之前的回调，返回非空，则放弃ajax请求，返回值当做data，则，其他后续照常动作仍执行
			'onSuccess':null, // onSuccess(data,
								// this)异步请求成功时的回调函数，返回true，表明无需后续动作了，
			'inline': true // 是否为请求url自动加上_line结尾
	};

	$.asyncBindFunction = function (_this, container0, options, selected) {
			var opts=$.extend({}, $.fn.asyncBind.defaults, options);
			var __this = $(_this);
			var hrefOrig = __this.attr('href');
			var hashIdx = hrefOrig.indexOf('#');
			var href;var hash = null;
			if(hashIdx < 0) {
				var href = hrefOrig;
			} else {
				href = hrefOrig.substring(0, idx);
				hash = hrefOrig.substring(hashIdx);
			}
			if(opts.inline) {
				if(href.indexOf('_inline')< 0 && href.indexOf('module') < 0){
					var idx = href.indexOf('?');
				    if(idx < 0) {
						href+='_inline';
					} else {
						href = href.substring(0, idx) + '_inline' + href.substring(idx);
					}
				}
			}
			var data;
			if(opts.onBefore) {
				data = (opts.onBefore)(__this);
				if(data===true){
					return 
				}
				
			}

			var done = logExceptions(function (data) {
				if(hash) {
					window.location.hash = hash;
				}

				// FIXME logon dialog
				if(opts.onSuccess) {
					if((opts.onSuccess)(data,__this))
						return;
				}
				if(typeof container0 === 'string' && container0!="NULL") {
					container0 = container0.replace(/this/g, '_this');
					container0 = eval(container0);
				}
				if(container0!="NULL"){
					if(!opts.isAppend){
						container0.empty();
					}			
					container0.append(data);
				}
				if(opts.history) {
					var History = window.History;
					if(History != undefined && History.enabled) {
						History.pushState(null, $(document).attr('title'), hrefOrig);
					}
				}
			});
			if(data) {
				done(data);
			} else {
				$.ajax({
					type : "GET",
					url : href
				}).done(done);
			}
			if(opts.enableActive && selected != undefined){
				selected.removeClass('active');
				__this.addClass('active');
			}
			// FIXME on ajax error
			return false;
		} 

	/**
	 * 异步加载绑定，click时触发加载
	 * 
	 * @param container
	 *            支持jquery对象，例如$('.myclass');或者string表达式，例如
	 *            "$(this).parent.next()"
	 */
	$.fn.asyncFormBind = function(container, options) {
		var defaults = {
				'onBefore':null, // onBefore(this)发起异步请求之前的回调，返回非空，则放弃ajax请求，返回值当做data，则，其他后续照常动作仍执行
				'onSuccess':null, // onSuccess(data,
									// form)异步请求成功时的回调函数，返回true，表明无需后续动作了，
				'inline': true // 是否为请求url自动加上_line结尾
		};
		var opts=$.extend({},defaults,options);

		var selectedAttr = this.selector.replace(/[\.\[\]\=~ #,>\+\*]/g,'') + "_binded";
		this.each(function(){
			var container0 = container;
			if(typeof container0 === 'string' && container0!="NULL") {
				container0 = eval(container0);
			}
	
			if(!this[selectedAttr]) {
				this[selectedAttr] = true;
				$(this).bind('submit', function() {
					var _this = $(this);
					var method = _this.attr('method');
					if(!method) method = 'POST';
					var href = _this.attr('action');
					if(opts.inline) {
						if(href.indexOf('_inline')< 0 && href.indexOf('module') < 0){
							var idx = href.indexOf('?');
							if(idx < 0) {
								href+='_inline';
							} else {
								href = href.substring(0, idx) + '_inline' + href.substring(idx);
							}
						}
					}
					var data;
					if(opts.onBefore) {
						data = (opts.onBefore)(_this);
					}
					var done = logExceptions(function(data) {
						// FIXME logon dialog
						if(opts.onSuccess) {
							if((opts.onSuccess)(data, _this))
								return;
						}
						if(container0!="NULL"){
							if(!opts.isAppend){
								container0.empty();
							}	
							container0.append(data);
						}
					});
					if(data) {
						done(data);
					} else {
						$.ajax({
							type : method,
							url : href,
							data: _this.serialize()
						}).done(done);
					}
	
					// FIXME on ajax error
					return false;
				});
			}
		});
	}
})(jQuery);


/**
 * hover时滑动显示的效果
 */
(function($){
	$.fn.hoverSlide = function(slided, options) {
		var defaults = {
				dir: 'left', // 滑入的方向：默认 left
				offset: 50, // 滑动偏移量，默认 50%
				initOffset: 100, // 初始偏移量，默认100%
				duration: 800 // 滑动完成所需时间ms
		};
		var opts=$.extend({},defaults,options);

		opts.step = (opts.offset - opts.initOffset)*10/(opts.duration);
		opts.max = Math.max(opts.initOffset, opts.offset);
		opts.min = Math.min(opts.initOffset, opts.offset);
		this.each(function(){
			if(this.hoverSlideBound)
				return;
			this.hoverSlideBound = true;

			var slided0 = slided;
			if(typeof slided0 === 'string')
				slided0 = eval(slided0);
			var slideState = {
				inGrid: false,
				inFloat: false,
				timer: null,
				pos: opts.initOffset
			}

			$(this).mouseenter(function() {
				slideState.inGrid = true;
				$.fn.hoverSlide.doSlide(slided0, slideState, opts);
			}).mouseleave(function() {
				slideState.inGrid = false;
				$.fn.hoverSlide.doSlide(slided0, slideState, opts);
			});

			slided0.mouseenter(function() {
				slideState.inFloat = true;
				$.fn.hoverSlide.doSlide(slided0, slideState, opts);
			}).mouseleave(function() {
				slideState.inFloat = false;
				$.fn.hoverSlide.doSlide(slided0, slideState, opts);
			});
		});
	}
	
	$.fn.hoverSlide.doSlide = function(slided, state, opts) {
		if(state.timer != null)
			clearInterval(state.timer);
		var start = new Date().getTime();
		state.timer = setInterval(function(){
			var farNeed = ((new Date().getTime() - start)/10) * opts.step;
			var inIt = state.inGrid || state.inFloat;
			state.pos = inIt ? (state.pos + farNeed) : (state.pos - farNeed);
			if(state.pos >= opts.max || state.pos <= opts.min) {
				state.pos = inIt ? opts.offset : opts.initOffset;
				clearInterval(state.timer);
				state.timer= null;
			}
			slided.css(opts.dir, state.pos + '%');
		},10);
	}
})(jQuery);
/**
 * 延迟hover效果
 */
(dynamicScripts_last.hoverShow = function (){
	var hoverShowSwitch = function (container, showed, hide, pre) {
		if(hide) { // 隐藏掉
			showed.fadeOut(400);
			// container.trigger('mouseleave', true);
		} else {
			$('.hover-showed-abs,.hover-show1ed-abs').fadeOut(50);
			// container.trigger('mouseenter', true);
			showed.fadeIn(300);
		}
		showed.removeData('hoverTimer'+pre);
	}
	$('.hover-showed').addClass('hover-showed-abs').hide().removeClass('hover-showed');
	$('.hover-show1ed').addClass('hover-show1ed-abs').hide().removeClass('hover-show1ed');
	var onMouseEnter = function(e, pre, move) {
		e.stopImmediatePropagation();
		var target = $(e.target);
		if(!target.hasClass(pre))
			target = target.parents('.'+pre);
		var showed = target.find('.'+pre+'ed-abs');

		if(move && showed.is(":visible"))
			return;

		var hoverTimer = showed.data('hoverTimer'+pre);
		if(hoverTimer) {
			clearTimeout(hoverTimer);
			showed.removeData('hoverTimer'+pre);
		}
		if(showed.is(":visible"))
			return;
		showed.data('hoverTimer'+pre, setTimeout(function(){hoverShowSwitch(target, showed,false,pre)},50));
	};
	var onMouseLeave = function(e, pre) {
		e.stopImmediatePropagation();
		var target = $(e.target);
		if(!target.hasClass(pre))
			target = target.parents('.'+pre);
		var showed = target.find('.'+pre+'ed-abs');
		var hoverTimer = showed.data('hoverTimer'+pre);
		if(hoverTimer) {
			clearTimeout(hoverTimer);
			showed.removeData('hoverTimer'+pre);
		}
		if(!showed.is(":visible"))
			return;
		showed.data('hoverTimer'+pre, setTimeout(function(){hoverShowSwitch(target, showed, true,pre)},800));
	};
	$('.hover-show')
		.mouseenter(function(e){onMouseEnter(e, 'hover-show');})
		.mousemove(function(e){onMouseEnter(e, 'hover-show', true);})
		.mouseleave(function(e){onMouseLeave(e, 'hover-show');});
	$('.hover-show1')
		.mouseenter(function(e){onMouseEnter(e, 'hover-show1');})
		.mousemove(function(e){onMouseEnter(e, 'hover-show1', true);})
		.mouseleave(function(e){onMouseLeave(e, 'hover-show1');});
});

(function($){
	/**
	 * info的位置可自行控制，例如info = $('<span>dd</span>').css('top',300).css('left',200)
	 * 
	 * @param info
	 *            可以是string，或dom
	 * @param duration
	 *            info显示多少ms就消失， <= 0或null表示不自动消失
	 */
	$.popupInfo = function(info, duration, template) {
		if(!template)
			template = '#popup_template';
		var dlg = $(template);
		var tid = dlg.data('popupInfoTid');
		if(tid) {
			dlg.jqmHide();
			clearTimeout(tid);
		}
		dlg.jqmShow(info);
		if(duration > 0) {
			tid = setTimeout(function() {dlg.jqmHide()},duration);
			dlg.data('popupInfoTid', tid);
		}
	}
})(jQuery);


/**
 * fix for Firefox bug,
 * http://stackoverflow.com/questions/7742278/workaround-for-file-input-label-click-firefox
 */
if($.browser.mozilla) {
  $(document).on('click', 'label', function(e) {
    if(e.currentTarget === this && e.target.nodeName !== 'INPUT') {
      var ctrl = $(this.control);
      if('file' == ctrl.attr('type'))
    	  ctrl.click();
    }
  });
} else if($.browser.msie) {
	$("label img").live("click", function() {
		$("#" + $(this).parents("label").attr("for")).click().change();
		return false;
	});
}

$('a.submit').live('click', function() {
	var th = $(this), disabled = th.attr('disabled');
	if(!disabled || disabled == 'false')
		th.closest("form").trigger('submit');
});

/**
 * in some browsers, scrollHeight including padding.
 * 
 * requires target.style.height being set in html style
 */
function textareaAutoSize(target) {
	var paddingHeight = 0;
	var origHeight = target.style.height.replace(/px/i, '');
	var lines = target.scrollHeight / origHeight;
	if(lines % 1 !== 0) {
		try {
			var t = $(target);
			paddingHeight = t.innerHeight() - t.height();
		}catch(e){}
	}
	var height = target.scrollHeight- paddingHeight;
	if(height > origHeight)
		target.style.height=(height + 15) + 'px';
};