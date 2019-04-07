(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('user_add',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let form = $("#form").buildform({
		actions:{
			sub(){
				let params = this.getParamMap();
				
				http.post({
					url:'user!add.do',
					data:params
				}).then(res=>{
					if(res.success){
						router.goRoute("user");
					}else{
						alert("操作失败："+res.msg);
					}
				},e=>{
					alert("操作异常");
				})
				
			}
		}
	});
	
};
return module.exports;});