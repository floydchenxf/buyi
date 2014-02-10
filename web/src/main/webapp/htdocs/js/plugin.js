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
onLoad: F
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
L=function(t){$()[t]("keypress",m)[t]("keydown",m)[t]("mousedown",m);},
m=function(e){var h=H[A[A.length-1]],r=(!$(e.target).parents('.jqmID'+h.s)[0]);if(r)f(h);return !r;},
hs=function(w,t,c){return w.each(function(){var s=this._jqm;$(t).each(function() {
 if(!this[c]){this[c]=[];$(this).click(function(){for(var i in {jqmShow:1,jqmHide:1})for(var s in this[i])if(H[this[i][s]])H[this[i][s]].w[i](this);return F;});}this[c].push(s);});});};
})(jQuery);

/**
*
* <script>
*      $(function () {
*          $("#up").uploadPreview({ imgPreview: "#ImgPr", width: 120, height: 120, imgType: ["bmp", "gif", "png", "jpg"]});
*      });
* </script>
* <body>
*    <div><img id="ImgPr"/></div>
*    <input type="file" id="up" />
* </body>
*/
(function($){
jQuery.fn.extend({
uploadPreview: function(opts){
opts = jQuery.extend({
width: 0,
height: 0,
imgPreview: null,
imgType: ["gif", "jpeg", "jpg", "bmp", "png"],
callback: function(){ return false; }
}, opts || {});

var _self = this;
var _this = $(this);
var imgPreview = $(opts.imgPreview);
//设置样式
autoScaling = function(){
	var img_w = $(this).width();
    var img_h = $(this).height();
	var rit = img_w/img_h;
	var c_rit = opts.width/opts.height;
	
	var s_width = 0;
	if (rit > c_rit) {
		s_width = opts.width;
		s_height = opts.height * 1/c_rit;
	} else {
		s_width = opts.width;
		s_height = opts.height * 1/rit;
	}
	imgPreview.css({"margin-left": 0,"margin-top": 0,"width":s_width,"height":s_height});
	imgPreview.show();
}
//file按钮出发事件
_this.change(function(){
if (this.value) {
	if (!RegExp("\.(" + opts.imgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
		alert("图片类型必须是" + opts.imgType.join("，") + "中的一种");
		this.value = "";
		return false;
	}
	
	var path = '';
	if ($.browser.msie) {//判断ie
		path = $(this).val();
		if (/"\w\W"/.test(path)) {
			path = path.slice(1,-1);
		}
		this.select();
		path = document.selection.createRange().text;
		imgPreview.attr("src",path);
		imgPreview.css({"margin-left": 0,"margin-top": 0,"width":opts.width,"height":opts.height});
		setTimeout("autoScaling()", 100);
	} else {
		if ($.browser.version < 7) {
			imgPreview.attr('src', this.files.item(0).getAsDataURL());
		} else {
			oFReader = new FileReader(), rFilter = /^(?:image\/bmp|image\/cis\-cod|image\/gif|image\/ief|image\/jpeg|image\/jpeg|image\/jpeg|image\/pipeg|image\/png|image\/svg\+xml|image\/tiff|image\/x\-cmu\-raster|image\/x\-cmx|image\/x\-icon|image\/x\-portable\-anymap|image\/x\-portable\-bitmap|image\/x\-portable\-graymap|image\/x\-portable\-pixmap|image\/x\-rgb|image\/x\-xbitmap|image\/x\-xpixmap|image\/x\-xwindowdump)$/i;
			oFReader.onload = function(oFREvent){
			imgPreview.attr('src', oFREvent.target.result);
		};
		var oFile = this.files[0];
		path = oFile.name;
		oFReader.readAsDataURL(oFile);
	}
	imgPreview.css({"margin-left": 0,"margin-top": 0,"width":opts.width,"height":opts.height});
	setTimeout("autoScaling()", 100);
}
}
opts.callback(path);
});
}
});
})(jQuery);