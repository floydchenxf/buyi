window.log=function(){log.history=log.history||[];log.history.push(arguments);if(this.console){var a=arguments;a.callee=a.callee.caller;a=[].slice.call(a);"object"===typeof console.log?log.apply.call(console.log,console,a):console.log.apply(console,a)}};
(function(a){a.cookie=function(c,d,b){if(1<arguments.length&&(!/Object/.test(Object.prototype.toString.call(d))||null===d||void 0===d)){b=a.extend({},b);if(null===d||void 0===d)b.expires=-1;if("number"===typeof b.expires){var f=b.expires,e=b.expires=new Date;e.setDate(e.getDate()+f)}d=String(d);return document.cookie=[encodeURIComponent(c),"=",b.raw?d:encodeURIComponent(d),b.expires?"; expires="+b.expires.toUTCString():"",b.path?"; path="+b.path:"",b.domain?"; domain="+b.domain:"",b.secure?"; secure":
""].join("")}b=d||{};for(var f=b.raw?function(a){return a}:decodeURIComponent,e=document.cookie.split("; "),g=0,j;j=e[g]&&e[g].split("=");g++)if(f(j[0])===c)return f(j[1]||"");return null}})(jQuery);
(function(a){a.fn.jqm=function(b){var f={overlay:50,overlayClass:"jqmOverlay",closeClass:"jqmClose",trigger:".jqModal",ajax:e,ajaxText:"",target:e,modal:e,toTop:e,onShow:e,onHide:e,onLoad:e};return this.each(function(){if(this._jqm)return d[this._jqm].c=a.extend({},d[this._jqm].c,b);c++;this._jqm=c;d[c]={c:a.extend(f,a.jqm.params,b),a:e,w:a(this).addClass("jqmID"+c),s:c};f.trigger&&a(this).jqmAddTrigger(f.trigger)})};a.fn.jqmAddClose=function(a){return m(this,a,"jqmHide")};a.fn.jqmAddTrigger=function(a){return m(this,
a,"jqmShow")};a.fn.jqmShow=function(b){return this.each(function(){b=b||window.event;a.jqm.open(this._jqm,b)})};a.fn.jqmHide=function(b){return this.each(function(){b=b||window.event;a.jqm.close(this._jqm,b)})};a.jqm={hash:{},open:function(c,g){var h=d[c],m=h.c,l="."+m.closeClass,p=parseInt(h.w.css("z-index")),p=0<p?p:3E3,n=a("<div></div>").css({height:"100%",width:"100%",position:"fixed",left:0,top:0,"z-index":p-1,opacity:m.overlay/100});if(h.a)return e;h.t=g;h.a=!0;h.w.css("z-index",p);m.modal?
(b[0]||k("bind"),b.push(c)):0<m.overlay?h.w.jqmAddClose(n):n=e;h.o=n?n.addClass(m.overlayClass).prependTo("body"):e;if(f&&(a("html,body").css({height:"100%",width:"100%"}),n)){var n=n.css({position:"absolute"})[0],q;for(q in{Top:1,Left:1})n.style.setExpression(q.toLowerCase(),"(_=(document.documentElement.scroll"+q+" || document.body.scroll"+q+"))+'px'")}m.ajax?(p=m.target||h.w,n=m.ajax,p="string"==typeof p?a(p,h.w):a(p),n="@"==n.substr(0,1)?a(g).attr(n.substring(1)):n,p.html(m.ajaxText).load(n,function(){m.onLoad&&
m.onLoad.call(this,h);l&&h.w.jqmAddClose(a(l,h.w));j(h)})):l&&h.w.jqmAddClose(a(l,h.w));m.toTop&&h.o&&h.w.before('<span id="jqmP'+h.w[0]._jqm+'"></span>').insertAfter(h.o);m.onShow?m.onShow(h):h.w.show();j(h);return e},close:function(c){c=d[c];if(!c.a)return e;c.a=e;b[0]&&(b.pop(),b[0]||k("unbind"));c.c.toTop&&c.o&&a("#jqmP"+c.w[0]._jqm).after(c.w).remove();if(c.c.onHide)c.c.onHide(c);else c.w.hide(),c.o&&c.o.remove();return e},params:{}};var c=0,d=a.jqm.hash,b=[],f=a.browser.msie&&"6.0"==a.browser.version,
e=!1,g=a('<iframe src="javascript:false;document.write(\'\');" class="jqm"></iframe>').css({opacity:0}),j=function(b){f&&(b.o?b.o.html('<p style="width:100%;height:100%"/>').prepend(g):a("iframe.jqm",b.w)[0]||b.w.prepend(g));h(b)},h=function(b){try{a(":input:visible",b.w)[0].focus()}catch(d){}},k=function(b){a(this)[b]("keypress",l)[b]("keydown",l)[b]("mousedown",l)},l=function(c){var e=d[b[b.length-1]];(c=!a(c.target).parents(".jqmID"+e.s)[0])&&h(e);return!c},m=function(b,c,f){return b.each(function(){var b=
this._jqm,g=a(c),h=function(){this[f]||(this[f]=[],this[f].push(b));for(var a in{jqmShow:1,jqmHide:1})for(var c in this[a])if(d[this[a][c]])d[this[a][c]].w[a](this);return e};g.selector?g.live("click",h):g.click(h)})}})(jQuery);
(function(a){a.fn.jqDrag=function(a){return f(this,a,"d")};a.fn.jqResize=function(a){return f(this,a,"r")};a.jqDnR={dnr:{},e:0,drag:function(a){"d"==d.k?b.css({left:d.X+a.pageX-d.pX,top:d.Y+a.pageY-d.pY}):b.css({width:Math.max(a.pageX-d.pX+d.W,0),height:Math.max(a.pageY-d.pY+d.H,0)});return!1},stop:function(){b.css("opacity",d.o);a(this).unbind("mousemove",c.drag).unbind("mouseup",c.stop)}};var c=a.jqDnR,d=c.dnr,b=c.e,f=function(c,f,j){return c.each(function(){f=f?a(f,c):c;f.bind("mousedown",{e:c,
k:j},function(c){var e=c.data,f={};b=e.e;if("relative"!=b.css("position"))try{b.position(f)}catch(g){}d={X:f.left||parseInt(b.css("left"))||!1||0,Y:f.top||parseInt(b.css("top"))||!1||0,W:parseInt(b.css("width"))||!1||b[0].scrollWidth||0,H:parseInt(b.css("height"))||!1||b[0].scrollHeight||0,pX:c.pageX,pY:c.pageY,k:e.k,o:b.css("opacity")};b.css({opacity:0.8});a(this).mousemove(a.jqDnR.drag).mouseup(a.jqDnR.stop);return!1})})}})(jQuery);
(function(a){a.fn.hoverIntent=function(c,d){var b={sensitivity:7,interval:100,timeout:0},b=a.extend(b,d?{over:c,out:d}:c),f,e,g,j,h=function(a){f=a.pageX;e=a.pageY},k=function(d,c){c.hoverIntent_t=clearTimeout(c.hoverIntent_t);if(Math.abs(g-f)+Math.abs(j-e)<b.sensitivity)return a(c).unbind("mousemove",h),c.hoverIntent_s=1,b.over.apply(c,[d]);g=f;j=e;c.hoverIntent_t=setTimeout(function(){k(d,c)},b.interval)},l=function(d){var c=jQuery.extend({},d),e=this;e.hoverIntent_t&&(e.hoverIntent_t=clearTimeout(e.hoverIntent_t));
"mouseenter"==d.type?(g=c.pageX,j=c.pageY,a(e).bind("mousemove",h),1!=e.hoverIntent_s&&(e.hoverIntent_t=setTimeout(function(){k(c,e)},b.interval))):(a(e).unbind("mousemove",h),1==e.hoverIntent_s&&(e.hoverIntent_t=setTimeout(function(){e.hoverIntent_t=clearTimeout(e.hoverIntent_t);e.hoverIntent_s=0;b.out.apply(e,[c])},b.timeout)))};return this.bind("mouseenter",l).bind("mouseleave",l)}})(jQuery);(function(a){a.stopPropagation=function(c){c=c?c:window.event;a.browser.msie?c.cancelBubble=!0:c.stopPropagation()}})(jQuery);
(function(a){function c(d,b){var c=a(d).offsetParent().offset(),e=a(d).offset();e||(e={top:0,left:0});b||(b=4);return{top:e.top-c.top,left:e.left-c.left+b,width:a(d).width()-b}}a.fn.placeholder=function(){return this.each(function(){var d=a(this),b=d.data("target");b&&d.css(c(b))})};a.extend(a,{placeholder:{browser_supported:function(){return void 0!==this._supported?this._supported:this._supported=!!("placeholder"in a('<input type="text">')[0])},shim:function(c){var b={color:"#888",cls:"placeholder",
lr_padding:4,selector:"input[placeholder], textarea[placeholder]"};a.extend(b,c);!this.browser_supported()&&a(b.selector)._placeholder_shim(b)}}});a.extend(a.fn,{_placeholder_shim:function(d){return this.each(function(){var b=a(this);if(b.data("placeholder"))return b.data("placeholder").css(c(b)),!0;var f={};!b.is("textarea")&&"auto"!=b.css("height")&&(f={whiteSpace:"nowrap"},0<b.height()&&(f.lineHeight=b.css("height")));var e=a("<label />").text(b.attr("placeholder")).addClass(d.cls).css(a.extend({position:"absolute",
display:"inline","float":"none",overflow:"hidden",textAlign:"left",color:d.color,cursor:"text",paddingTop:b.css("padding-top"),paddingLeft:b.css("padding-left"),fontSize:b.css("font-size"),fontFamily:b.css("font-family"),fontStyle:b.css("font-style"),fontWeight:b.css("font-weight"),textTransform:b.css("text-transform"),zIndex:99},f)).css(c(this)).attr("for",this.id).data("target",b).click(function(){a(this).data("target").focus()}).insertBefore(this);b.data("placeholder",e).focus(function(){e.hide()}).blur(function(){e[b.val().length?
"hide":"show"]()});e[b.val().length?"hide":"show"]();a(window).resize(function(){var a=e.data("target");e.css(c(a))})})}})})(jQuery);
(function(a){a.fn.getSelection=function(){var a=0,d=0,b,f,e,g=this[0];if("number"==typeof g.selectionStart&&"number"==typeof g.selectionEnd)a=g.selectionStart,d=g.selectionEnd;else if((f=document.selection.createRange())&&f.parentElement()==g)e=g.value.length,b=g.value.replace(/\r\n/g,"\n"),d=g.createTextRange(),d.moveToBookmark(f.getBookmark()),f=g.createTextRange(),f.collapse(!1),-1<d.compareEndPoints("StartToEnd",f)?a=d=e:(a=-d.moveStart("character",-e),a+=b.slice(0,a).split("\n").length-1,-1<
d.compareEndPoints("EndToEnd",f)?d=e:(d=-d.moveEnd("character",-e),d+=b.slice(0,d).split("\n").length-1));return{start:a,end:d}};a.fn.insertAtCaret=function(a,d){d||(d=0);var b=this.getSelection(),f=this.val();this.val(f.substring(0,b.start+d)+a+f.substring(b.end));b.start+=a.length+d;var f=this[0],e=f.scrollTop;if(f.selectionStart||"0"==f.selectionStart)f.selectionStart=b.start,f.selectionEnd=b.start,f.focus();else if(document.selection){f.focus();var g=document.selection.createRange();g.moveStart("character",
-f.value.length);g.moveStart("character",b.start);g.moveEnd("character",0);g.select()}f.scrollTop=e;this.trigger("mousedown");this.trigger("mouseup")}})(jQuery);
(function(a){var c="fontFamily fontSize fontWeight fontVariant fontStyle paddingLeft paddingTop paddingBottom paddingRight marginLeft marginTop marginBottom marginRight borderLeftColor borderTopColor borderBottomColor borderRightColor borderLeftStyle borderTopStyle borderBottomStyle borderRightStyle borderLeftWidth borderTopWidth borderBottomWidth borderRightWidth line-height outline".split(" ");a.fn.getCaretOffset=function(d,b){var f=this[0],e=this,g=e.offset();d||(d=0);if(a.browser.msie)return f.focus(),
g=document.selection.createRange(),{left:g.boundingLeft,top:parseInt(g.boundingTop)+f.scrollTop+document.documentElement.scrollTop+parseInt(e.css("fontSize"))};var j=this.data("caret_pos_shadow");j||(j=a('<div class="input_shadow"/>').css({position:"absolute","white-space":"pre-wrap","*white-space":"pre","word-wrap":"break-word","overflow-x":"hidden","overflow-y":"auto",visibility:"hidden"}).appendTo(document.body),this.data("caret_pos_shadow",j),a.each(c,function(a,b){var d=e.css(b);d&&j.css(b,d)}));
j.empty();j.css({top:g.top,left:g.left,width:e.width(),height:e.height()});var h=e.val(),k=e.getSelection().start+d,g=h.substring(0,k),h=h.substring(k),g=a('<span class="shadow_cur_before"/>').text(g),k=a('<span class="shadow_cur_focus">&nbsp;</span>'),h=a('<span class="shadow_cur_after"/>').text(h);j.append(g).append(k).append(h);g=k.offset();return{top:g.top-f.scrollTop+(b?parseInt(e.css("fontSize")):0),left:g.left-f.scrollLeft}}})(jQuery);
dynamicScripts_last.placeholder=function(){$.placeholder.shim();$(".placeholder").mouseup(function(a){a.stopPropagation()})};
(function(a){a.path={};var c={rotate:function(a,b){var c=3.141592654*b/180,e=Math.cos(c),c=Math.sin(c);return[e*a[0]-c*a[1],c*a[0]+e*a[1]]},scale:function(a,b){return[b*a[0],b*a[1]]},add:function(a,b){return[a[0]+b[0],a[1]+b[1]]},minus:function(a,b){return[a[0]-b[0],a[1]-b[1]]}};a.path.bezier=function(d){d.start=a.extend({angle:0,length:0.3333},d.start);d.end=a.extend({angle:0,length:0.3333},d.end);this.p1=[d.start.x,d.start.y];this.p4=[d.end.x,d.end.y];var b=c.minus(this.p4,this.p1),f=c.scale(b,
d.start.length),f=c.rotate(f,d.start.angle);this.p2=c.add(this.p1,f);b=c.scale(b,-1);b=c.scale(b,d.end.length);b=c.rotate(b,d.end.angle);this.p3=c.add(this.p4,b);this.f1=function(a){return a*a*a};this.f2=function(a){return 3*a*a*(1-a)};this.f3=function(a){return 3*a*(1-a)*(1-a)};this.f4=function(a){return(1-a)*(1-a)*(1-a)};this.css=function(a){var b=this.f1(a),c=this.f2(a),d=this.f3(a);a=this.f4(a);return{top:this.p1[1]*b+this.p2[1]*c+this.p3[1]*d+this.p4[1]*a+"px",left:this.p1[0]*b+this.p2[0]*c+
this.p3[0]*d+this.p4[0]*a+"px"}}};a.path.arc=function(a){for(var b in a)this[b]=a[b];for(this.dir=this.dir||1;this.start>this.end&&0<this.dir;)this.start-=360;for(;this.start<this.end&&0>this.dir;)this.start+=360;this.css=function(a){a=this.start*a+this.end*(1-a);a=3.1415927*a/180;var b=Math.sin(a)*this.radius+this.center[0];return{top:Math.cos(a)*this.radius+this.center[1]+"px",left:b+"px"}}};a.fx.step.path=function(a){var b=a.end.css(1-a.pos),c;for(c in b)a.elem.style[c]=b[c]}})(jQuery);function getErrorBox(a){a=getFieldErrorBox(a);0==a.length&&(a=getGlobalErrorBox());return a}function getFieldErrorBox(a){var c=a.id;c||(c=a.attr("id"));return $("#error-"+c)}function getGlobalErrorBox(){return $("div.global.error")}
function postFormForValidation(a,c,d,b,f){$.ajax({type:"POST",url:a.attr("action"),data:a.serialize()+"&paramNameValidating="+d+(f?f:""),dataType:"json",headers:{"X-Requested-For":"Validation"},success:function(a){var f="",j=a[d],h;for(h in j){f=j[h];break}b(f);a[d]?(a="#ok-"+c,$(a).hide()):(a="#ok-"+c,$(a).show())}})}
function newValidationContext(){return{_needValidate:function(a){var c=a.attr("id");if(void 0===c)return!0;var d=this[c];return!d?(d={},this[c]=d,a.bind("focus",function(){d.validateStatus=!0;d.errMsg="";return!0}),!0):d.validateStatus},_updateContextSatus:function(a,c){var d=a.id;d||(d=a.attr("id"));if(d=this[d])d.errMsg=c,d.validateStatus=""==c||null==c?!0:!1}}}globalValidationContext=newValidationContext();
function getValidationContext(a){a=$(a)[0];if(!a)return globalValidationContext;var c=a.validationContext;c||(c=newValidationContext(),a.validationContext=c);return c}function showErrorMessage(a,c,d){var b=getErrorBox(a);d||(d="error-warn");b.removeClass("error-info");b.removeClass("error-warn");c&&b.addClass(d);b.html(c);(d=a.form)||(d=a.closest("form"));getValidationContext(d)._updateContextSatus(a,c)}
dynamicScripts_first.validationBind=function(){$("[v_notempty]").each(function(){if(!this.v_notempty_bound){this.v_notempty_bound=!0;var a=$(this),c=eval(a.attr("v_notempty"));a.attr("required",!0).bind("blur",function(){getValidationContext(a.closest("form"))._needValidate(a)&&showErrorMessage(a,a.val()?"":c.message)})}});$("[v_notblank]").each(function(){if(!this.v_notblank_bound){this.v_notblank_bound=!0;var a=$(this),c=eval(a.attr("v_notblank"));a.attr("required",!0).bind("blur",function(){getValidationContext(a.closest("form"))._needValidate(a)&&
showErrorMessage(a,$.trim(a.val())?"":c.message)}).bind("focus",function(){showErrorMessage(a,c.message,"error-info")})}});$("[v_size]").each(function(){if(!this.v_size_bound){this.v_size_bound=!0;var a=$(this),c=eval(a.attr("v_size"));a.attr("maxlength",c.max).bind("blur",function(){if(getValidationContext(a.closest("form"))._needValidate(a)){var d=a.val().length;showErrorMessage(a,d>=c.min&&d<=c.max?"":c.message)}})}});$("[v_pattern]").each(function(){if(!this.v_pattern_bound){this.v_pattern_bound=
!0;var a=$(this),c=eval(a.attr("v_pattern").replace("/\\/g","\\\\"));c.regexp=RegExp(c.regexp);a.bind("blur",function(){getValidationContext(a.closest("form"))._needValidate(a)&&showErrorMessage(a,c.regexp.test(a.val())?"":c.message)})}});$("[v_email]").each(function(){if(!this.v_email_bound){this.v_email_bound=!0;var a=$(this),c=eval(a.attr("v_email"));a.bind("blur",function(){if(getValidationContext(a.closest("form"))._needValidate(a)){var d=/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
b=a.val();showErrorMessage(a,!b||d.test(b)?"":c.message)}})}});$("[v_asyncvalidate]").each(function(){this.v_asyncvalidate_bound||(this.v_asyncvalidate_bound=!0,$(this).bind("blur",function(){var a=$(this);if(getValidationContext(a.closest("form"))._needValidate(a)){var c=a.closest("form");postFormForValidation(c,this.id,this.name,function(c){a.attr("onsuccess")&&""===c&&eval(a.attr("onsuccess"))();showErrorMessage(a,c)})}}))});$(".v_Ok").bind("blur",function(a){var c=$(this);getValidationContext(c.closest("form"))._needValidate(c)?
(a="#ok-"+a.target.id,$(a).show()):(a="#ok-"+a.target.id,$(a).hide())});$("form").each(function(){this.form_valid_binded||(this.form_valid_binded=!0,$(this).bind("submit",function(a){validated=!0;var c,d=$(this);d.find("*").each(function(){!1===getValidationContext(d)._needValidate($(this).trigger("blur"))&&(validated&&(c=$(this)),validated=!1)});c&&$("html, body").animate({scrollTop:c.offset().top-10},300);validated?d.find('input[type="submit"],.submit').attr("autocomplete","off").attr("disabled",
"disabled"):a.stopImmediatePropagation();return validated}))})};

var globalcurrentopenTag="";$.ajaxSetup({cache:!1,timeout:3E4});function doCloseChoose(a){jQuery(a).css("display","none");return!0}function doOpenChoose(a){jQuery(a).css("display","block");return!0}if(!console)var console={log:function(){},info:function(){},warn:function(){}};function printStackTrace(a){var c=a.name+":"+a.message;a.fileName&&(c+=" at "+a.fileName+":"+a.lineNumber);console.log(c);a.stack&&console.log(a.stack)}
function logExceptions(a){return decorated=function(){try{a.apply(this,arguments)}catch(c){throw printStackTrace(c),c;}}}function userBindWnd(a,c,d,b){if(!(null==d||""==d)){if(null==b||""==b)b=window.location.href;d+=(0>d.indexOf("?")?"?":"&")+"redirect="+b;window.location=d}}
(function(a){a.asyncLoad=function(c,d){a.ajax({type:"GET",url:c}).done(logExceptions(d))};a.fn.asyncInit=function(c,d,b){var f=this;a.ajax({type:"GET",url:c}).done(logExceptions(function(c){if(b||!(0<a(c).find("#logon_form").length))d||f.empty(),f.append(c)}))};a.fn.asyncBind=function(c,d){var b=a(this.selector),f=this.selector.replace(/[\.\[\]\=~ #,>\+\*]/g,"")+"_binded";this.each(function(){var e=c;"string"===typeof e&&"NULL"!=e&&(e=eval(e));this[f]||(this[f]=!0,a(this).bind("click",function(){a.asyncBindFunction(this,
e,d,b);return!1}))})};a.fn.asyncBind.defaults={enableActive:!0,history:!0,onBefore:null,onSuccess:null,inline:!0};a.asyncBindFunction=function(c,d,b,f){var e=a.extend({},a.fn.asyncBind.defaults,b),g=a(c),j=g.attr("href");b=j.indexOf("#");var h=null;0>b?c=j:(c=j.substring(0,k),h=j.substring(b));if(e.inline&&0>c.indexOf("_inline")&&0>c.indexOf("module")){var k=c.indexOf("?");c=0>k?c+"_inline":c.substring(0,k)+"_inline"+c.substring(k)}var l;if(e.onBefore&&(l=e.onBefore(g),!0===l))return;k=logExceptions(function(b){h&&
(window.location.hash=h);if(!e.onSuccess||!e.onSuccess(b,g))"string"===typeof d&&"NULL"!=d&&(d=d.replace(/this/g,"_this"),d=eval(d)),"NULL"!=d&&(e.isAppend||d.empty(),d.append(b)),e.history&&(b=window.History,void 0!=b&&b.enabled&&b.pushState(null,a(document).attr("title"),j))});l?k(l):a.ajax({type:"GET",url:c}).done(k);e.enableActive&&void 0!=f&&(f.removeClass("active"),g.addClass("active"));return!1};a.fn.asyncFormBind=function(c,d){var b=a.extend({},{onBefore:null,onSuccess:null,inline:!0},d),
f=this.selector.replace(/[\.\[\]\=~ #,>\+\*]/g,"")+"_binded";this.each(function(){var d=c;"string"===typeof d&&"NULL"!=d&&(d=eval(d));this[f]||(this[f]=!0,a(this).bind("submit",function(){var c=a(this),f=c.attr("method");f||(f="POST");var h=c.attr("action");if(b.inline&&0>h.indexOf("_inline")&&0>h.indexOf("module"))var k=h.indexOf("?"),h=0>k?h+"_inline":h.substring(0,k)+"_inline"+h.substring(k);var l;b.onBefore&&(l=b.onBefore(c));k=logExceptions(function(a){if((!b.onSuccess||!b.onSuccess(a,c))&&"NULL"!=
d)b.isAppend||d.empty(),d.append(a)});l?k(l):a.ajax({type:f,url:h,data:c.serialize()}).done(k);return!1}))})}})(jQuery);
(function(a){a.fn.hoverSlide=function(c,d){var b=a.extend({},{dir:"left",offset:50,initOffset:100,duration:800},d);b.step=10*(b.offset-b.initOffset)/b.duration;b.max=Math.max(b.initOffset,b.offset);b.min=Math.min(b.initOffset,b.offset);this.each(function(){if(!this.hoverSlideBound){this.hoverSlideBound=!0;var d=c;"string"===typeof d&&(d=eval(d));var e={inGrid:!1,inFloat:!1,timer:null,pos:b.initOffset};a(this).mouseenter(function(){e.inGrid=!0;a.fn.hoverSlide.doSlide(d,e,b)}).mouseleave(function(){e.inGrid=
!1;a.fn.hoverSlide.doSlide(d,e,b)});d.mouseenter(function(){e.inFloat=!0;a.fn.hoverSlide.doSlide(d,e,b)}).mouseleave(function(){e.inFloat=!1;a.fn.hoverSlide.doSlide(d,e,b)})}})};a.fn.hoverSlide.doSlide=function(a,d,b){null!=d.timer&&clearInterval(d.timer);var f=(new Date).getTime();d.timer=setInterval(function(){var e=((new Date).getTime()-f)/10*b.step,g=d.inGrid||d.inFloat;d.pos=g?d.pos+e:d.pos-e;if(d.pos>=b.max||d.pos<=b.min)d.pos=g?b.offset:b.initOffset,clearInterval(d.timer),d.timer=null;a.css(b.dir,
d.pos+"%")},10)}})(jQuery);
dynamicScripts_last.hoverShow=function(){var a=function(a,c,d,g){d?c.fadeOut(400):($(".hover-showed-abs,.hover-show1ed-abs").fadeOut(50),c.fadeIn(300));c.removeData("hoverTimer"+g)};$(".hover-showed").addClass("hover-showed-abs").hide().removeClass("hover-showed");$(".hover-show1ed").addClass("hover-show1ed-abs").hide().removeClass("hover-show1ed");var c=function(b,c,d){b.stopImmediatePropagation();var g=$(b.target);g.hasClass(c)||(g=g.parents("."+c));var j=g.find("."+c+"ed-abs");if(!d||!j.is(":visible")){if(b=
j.data("hoverTimer"+c))clearTimeout(b),j.removeData("hoverTimer"+c);j.is(":visible")||j.data("hoverTimer"+c,setTimeout(function(){a(g,j,!1,c)},50))}},d=function(b,c){b.stopImmediatePropagation();var d=$(b.target);d.hasClass(c)||(d=d.parents("."+c));var g=d.find("."+c+"ed-abs"),j=g.data("hoverTimer"+c);j&&(clearTimeout(j),g.removeData("hoverTimer"+c));g.is(":visible")&&g.data("hoverTimer"+c,setTimeout(function(){a(d,g,!0,c)},800))};$(".hover-show").mouseenter(function(a){c(a,"hover-show")}).mousemove(function(a){c(a,
"hover-show",!0)}).mouseleave(function(a){d(a,"hover-show")});$(".hover-show1").mouseenter(function(a){c(a,"hover-show1")}).mousemove(function(a){c(a,"hover-show1",!0)}).mouseleave(function(a){d(a,"hover-show1")})};(function(a){a.popupInfo=function(c,d,b){b||(b="#popup_template");var f=a(b);if(b=f.data("popupInfoTid"))f.jqmHide(),clearTimeout(b);f.jqmShow(c);0<d&&(b=setTimeout(function(){f.jqmHide()},d),f.data("popupInfoTid",b))}})(jQuery);
if($.browser.mozilla)$(document).on("click","label",function(a){a.currentTarget===this&&"INPUT"!==a.target.nodeName&&(a=$(this.control),"file"==a.attr("type")&&a.click())});else $.browser.msie&&$("label img").live("click",function(){$("#"+$(this).parents("label").attr("for")).click().change();return!1});$("a.submit").live("click",function(){var a=$(this),c=a.attr("disabled");(!c||"false"==c)&&a.closest("form").trigger("submit")});
function textareaAutoSize(a){var c=0,d=a.style.height.replace(/px/i,"");if(0!==a.scrollHeight/d%1)try{var b=$(a),c=b.innerHeight()-b.height()}catch(f){}c=a.scrollHeight-c;c>d&&(a.style.height=c+15+"px")};
