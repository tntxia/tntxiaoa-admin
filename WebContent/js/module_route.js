/**
 * 
 * 路由管理
 * 
 */
(function( global, factory ) {
	factory(global);
})( typeof window !== "undefined" ? window : this, function( window ) {
	let router = {};
	// 将变量注册到全局变量
	window.router = router;
	router.register = function(opt){
		let target = opt.target;
		let defaultModule = opt.defaultModule;
		setModule();
		$(window).on("hashchange", function(e) {
			
			setModule();
		});
		function getRoute() {
			return getRouteDetail().route;
		}

		function goRoute(route) {
			window.location.href = "#" + route;
		}

		function setModule() {
			var moduleName = getRoute();
			if (!moduleName) {
				goRoute(defaultModule);
				return;
			}

			if (!window.modules || !window.modules[moduleName]) {
				var node = document.createElement('script');
				node.type = 'text/javascript';
				node.charset = 'utf-8';
				node.async = true;
				node.src = "modules/" + moduleName + ".module.js";
				node.addEventListener('load', function() {
					loadTemplate();
				}, false);
				node.addEventListener('error', function() {

				}, false);
				var head = document.getElementsByTagName('head')[0];
				head.appendChild(node);
			} else {
				loadTemplate();
			}

		}

		function loadTemplate() {
			var moduleName = getRoute();
			target.load("template/" + moduleName + ".html", function() {
				var moduleName = getRoute();
				var module = window.modules[moduleName];
				if (module.init) {
					module.init();
				}

			});
		}
	};
	
	router.getParams = function(){
		return getRouteDetail().params;
	}
	
	function getRouteDetail(){
		var hash = window.location.hash.substring(1);
		var arr = hash.split("?");
		var route = arr[0];
		var paramsStr = arr[1];
		var params;
		if(paramsStr){
			params = getParamsObject(paramsStr);
		}
		return {
			route:route,
			params:params
		};
	}
	
	function getParamsObject(str){
		var paramsArr = str.split("&");
		var params = {};
		$.each(paramsArr,function(i,d){
			if(d){
				var keyValArr = d.split("=");
				params[keyValArr[0]] = keyValArr[1];
			}
		});
		return params;
	}
});
