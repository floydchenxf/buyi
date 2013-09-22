/**
 * 获取字段级别的错误框, 若没有，则获取全局错误框
 * @param field
 * 			该字段的输入框, 必须是个jquery对象
 * @return error box, 或者null
 */
function getErrorBox(field) {
	var errBox = getFieldErrorBox(field);
	if(0 == errBox.length)
		errBox = getGlobalErrorBox();
	return errBox;
}

/**
 * 获取字段级别的错误框, 通过id获取
 * @param field
 * 			该字段的输入框, 必须是个jquery对象
 * @return error box, 或者null
 */
function getFieldErrorBox(field) {
	var id = field.id;if(!id)id=field.attr('id');
	var errSelector= "#error-"+id;
	return $(errSelector);
//	return field.siblings('div[class*="error"]');
}

/**
 * 获取全局错误框 div[*global error*]
 * @return error box, 或者null
 */
function getGlobalErrorBox() {
	// div[*global error*]
	return $('div.global.error');
}

/**
 * @param form 要提交的form
 * @param propertyName 当前验证的属性字段名称
 * @param onSuccess(msgMap<code, msg>) 请求响应成功的handler function
 */
function postFormForValidation(form, propertyId, propertyName, onSuccess, additionalData) {
	$.ajax({
		type : "POST",
		url : form.attr("action"),
		data : form.serialize() + '&paramNameValidating=' + propertyName + ( additionalData ? additionalData : ''),
		dataType: 	'json',
		headers : {
			"X-Requested-For" : "Validation"
		},
		success : function(data) {// 200
			var msg="";
			var obj = data[propertyName];
			for(var key in obj){
				msg = obj[key];
				break;
			}
			onSuccess(msg);
			if(data[propertyName]){
				var okSelector= "#ok-"+propertyId;
				$(okSelector).hide();
			}else{
				var okSelector= "#ok-"+propertyId;
				$(okSelector).show();
			}
		}
	});
}

/**
 * 验证上下文
 */
function newValidationContext() {
	return {
		/**
		 * 检查上下文，确定是否继续验证
		 */
		_needValidate : function(field) {
			// TODO how about radio binding?
			var id = field.attr('id');
			if(id === undefined)
				return true; // 不存在id，不验证

			var ctx = this[id];
			if(!ctx) {
				ctx = {};
				this[id] = ctx;

				field.bind('focus', function() {
					ctx['validateStatus'] = true;
					ctx['errMsg'] = "";
					return true;
				});
				
				return true;
			}
			return ctx['validateStatus'];
		},
		/**
		 * 更新上下文状态
		 */
		_updateContextSatus : function(field, errMsg) {
			var id = field.id;if(!id)id=field.attr('id');
			var ctx = this[id]
			if(ctx) {
				ctx['errMsg'] = errMsg;
				if(errMsg == "" || errMsg == null){
					ctx['validateStatus'] = true;
				}else{
					ctx['validateStatus'] = false;
				}
			}
		}
	};
}
globalValidationContext = newValidationContext();
function getValidationContext(form) {
	var form = $(form)[0];
	if(!form) {
		return globalValidationContext;
	}

	var ctx = form.validationContext;
	if(!ctx) {
		ctx = newValidationContext();
		form.validationContext = ctx;
	}
	return ctx;
}

/**
 * 设置错误信息，若msg为空表示无错误，否则有错误
 */
function showErrorMessage(field, msg, cssClass) {
	var errBox = getErrorBox(field);
	if(!cssClass)
		cssClass = 'error-warn';
	errBox.removeClass('error-info');
	errBox.removeClass('error-warn');
	if(msg)
		errBox.addClass(cssClass);
	errBox.html(msg);
	var theform = field.form;if(!theform)theform=field.closest('form');
	getValidationContext(theform)._updateContextSatus(field, msg);
	
}

/**
 * 默认验证逻辑依照class注册
 */
dynamicScripts_first.validationBind = function () {
	// 多个验证绑定到同一个元素时，在第一个失败的地方停止验证

	// 注意：实际验证顺序，由下面的具体绑定顺序来控制！！

	/////////// 绑定非空NotEmpty验证
	$('[v_notempty]').each(function() {
		if(this.v_notempty_bound)
			return;
		this.v_notempty_bound = true;

		var target = $(this);
		var params = eval(target.attr('v_notempty'));
		target.attr('required',true).bind('blur', function(event) {
			if(!getValidationContext(target.closest('form'))._needValidate(target))
				return;
	
			showErrorMessage(target, target.val() ? "" : params.message);
		});
	});

	/////////// 绑定非空非空格NotBlank验证
	$('[v_notblank]').each(function() {
		if(this.v_notblank_bound)
			return;
		this.v_notblank_bound = true;

		var target = $(this);
		var params = eval(target.attr('v_notblank'));
		target.attr('required',true).bind('blur', function(event) {
			if(!getValidationContext(target.closest('form'))._needValidate(target))
				return;
			showErrorMessage(target, $.trim(target.val()) ? "" : params.message);
		}).bind('focus', function(event) { // hints 显示
			showErrorMessage(target, params.message, 'error-info');
		});
	});

	$('[v_size]').each(function() {
		if(this.v_size_bound)
			return;
		this.v_size_bound = true;

		var target = $(this);
		var params = eval(target.attr('v_size'));
		target.attr('maxlength',params.max).bind('blur', function(event) {
			if(!getValidationContext(target.closest('form'))._needValidate(target))
				return;
			
			var len = target.val().length;
			showErrorMessage(target, (len >= params.min && len <= params.max) ? "" : params.message);
		});
	});

	$('[v_pattern]').each(function() {
		if(this.v_pattern_bound)
			return;
		this.v_pattern_bound = true;
		
		var target = $(this);
		var params = eval(target.attr('v_pattern').replace(/\\/g, '\\\\'));
		params.regexp =  new RegExp(params.regexp);
		target.bind('blur', function(event) {
			if(!getValidationContext(target.closest('form'))._needValidate(target))
				return;
			showErrorMessage(target, params.regexp.test(target.val()) ? "" : params.message);
		});
	});

	/////////// 绑定 Email验证
	$('[v_email]').each(function() {
		if(this.v_email_bound)
			return;
		this.v_email_bound = true;

		var target = $(this);
		var params = eval(target.attr('v_email'));
		target.bind('blur', function(event) {
			if(!getValidationContext(target.closest('form'))._needValidate(target))
				return;
			var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			var val = target.val();
			showErrorMessage(target, !val || re.test(val) ? "" : params.message);
		});
	});

	/////////// 绑定服务端异步验证
	$('[v_asyncvalidate]').each(function() {
		if(this.v_asyncvalidate_bound)
			return;
		this.v_asyncvalidate_bound = true;

		$(this).bind('blur', function(event) {
			var target = $(this);
			if(!getValidationContext(target.closest('form'))._needValidate(target))
				return;
	
			var form = target.closest('form');
			postFormForValidation(form, this.id, this.name, function(msg) {
				if(target.attr("onsuccess")){
					if("" === msg ){
						(eval(target.attr("onsuccess")))();
					}
				}
				showErrorMessage(target, msg);
				});
		});
	});
	
	// FIXME
	///////////显示 OK 图标
	$('.v_Ok').bind('blur', function(event){
		var target = $(this);
		if(getValidationContext(target.closest('form'))._needValidate(target)){
			var okSelector= "#ok-"+event.target.id;
			$(okSelector).show();
		}else{
			var okSelector= "#ok-"+event.target.id;
			$(okSelector).hide();
		}
	});

	/**
	 * 所有form先客户端验证，通过再提交
	 */
	$('form').each(function(event) {
			if(!this['form_valid_binded']) {
				this['form_valid_binded'] = true;
				$(this).bind('submit',function (e) {
					validated = true;
					var firstInvalid;
					var form = $(this);
					form.find('*').each(function() {
						if(false === getValidationContext(form)._needValidate($(this).trigger('blur'))) {
							if(validated)
								firstInvalid = $(this);
							validated = false;
						}
					});
					if(firstInvalid)
						$('html, body').animate({
					         scrollTop: firstInvalid.offset().top -10
					     }, 300);
					if(!validated)
						e.stopImmediatePropagation();
					else {
						form.find('input[type="submit"],.submit').attr("autocomplete", "off").attr("disabled", "disabled");
					}
					return validated;
	})}});
};
