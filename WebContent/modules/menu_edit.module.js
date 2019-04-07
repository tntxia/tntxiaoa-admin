(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('menu_edit',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/menu_edit.html';
exports.init = function(){
	
	let id = router.getParams().id;
	
	let form = $("#form").buildform({
		actions:{
			sub(){
				let params = this.getParamMap();
				http.post({
					url:'menu!update.do',
					data:params
				}).then(res=>{
					if(res.success){
						router.goRoute("menu");
					}
				})
				
			}
		}
	});
	
	http.post({
		url:'menu!detail.do',
		data:{
			id
		}
	}).then(res=>{
		form.setValues(res);
	});
	
	
};
return module.exports;});