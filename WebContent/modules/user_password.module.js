(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('user_password',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let id = router.getParams().id;
	
	$("[name=id]").val(id);
	
	let form = $("#form").buildform({
		actions:{
			sub(){
				let params = this.getParamMap();
				
				http.post({
					url:'user!updatePassword.do',
					data:params
				}).then(res=>{
					if(res.success){
						router.goRoute("user");
						
					}
				})
				
			}
		}
	});
	
	http.post({
		url:'user!detail.do',
		data:{
			id
		}
	}).then(res=>{
		form.setValues(res);
	});
	
	
};
return module.exports;});