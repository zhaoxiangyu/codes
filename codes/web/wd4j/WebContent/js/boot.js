var skinRoot = "skins/", skin = "sky", locale = "zh_CN";

$packagesConfig = {
	"contextPath": contextPath,
	"defaultCharset": "UTF-8"
};

$packagesConfig.patterns = {
	"default": {
		url: ">${fileName}.js"
	},
	"css": {
		url: ">${fileName}.css",
		contentType: "text/css"
	},
	"lib": {
		charset: "UTF-8",
		url: ">${fileName}.js",
		mergeRequests: false
	}
};

$packagesConfig.packages = {
	
	//jquery	
	"jquery": {
		fileName: "js/jquery/jquery-1.7.2",
		pattern: "lib"
	},
	//jquery	
	"autocomplete.css": {
		fileName: "js/jquery/jquery.autocomplete",
		pattern: "css"
	},
	//jquery	
	"jquery.autocomplete": {
		fileName: "js/jquery/jquery.autocomplete",
		pattern: "lib",
		depends:"autocomplete.css"
	},
	//validation
	"validation": {
		fileName: ["js/validation/validationEngine-cn","js/validation/validationEngine"],
		pattern: "lib",
		depends: "jquery"
	},
	//ztree.style
	"ztree.style": {
		fileName: "js/ztree/css/zTreeStyle/zTreeStyle",
		pattern: "css"
	},
	//ztree
	"ztree": {
		fileName: "js/ztree/js/jquery.ztree.all-3.5",
		pattern: "lib",
		depends: "jquery,ztree.style"
	},
	//floatPanel
	"floatPanel": {
		fileName: "js/floatPanel/floatPanel",
		pattern: "lib",
		depends: "jquery"
	},
	"datepicker": {
		fileName: "js/datePicker/WdatePicker",
		pattern: "lib"
	},
	
	"dialog": {
		fileName: "js/dialog",
		pattern: "lib"
	},
	"drag": {
		fileName: "js/drag",
		pattern: "lib"
	},
	"md5": {
		fileName: "js/md5",
		pattern: "lib"
	},
	"xdic": {
		fileName: "js/xdic/common,js/xdic/default,js/xdic/edit,js/xdic/xDic,js/xdic/xWin",
		pattern: "lib"
	},
	//ajaxfileupload
	"ajaxfileupload":{
		fileName:"js/ajaxfileupload",
		pattern: "lib"
	},
	//dwr
	"dwr":{
		fileName:["dwr/engine","dwr/util"],
		pattern: "lib"
	}
		
};

document.writeln("<script src='" + contextPath + "/js/boot-engine.js' charset='utf-8'></script>");
