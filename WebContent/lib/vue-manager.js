
var vueCache = Object.create(null);

var VueManager = {
	createVue:function(name,opt){
		vueCache[name] = new Vue(opt);
	},
	setVueData:function(name,key,value){
		var vm = vueCache[name];
		vm[key] = value;
	},
	invokeVueMethod:function(name,funName,data){
		var vm = vueCache[name];
		vm[funName](data);
	}
};