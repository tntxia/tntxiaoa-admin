(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('organization_add',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let parentId = router.getParam("parentId");
	let parentName = router.getParam("parentName");
	
	
	let form = $("#form").buildform({
		data:{
			parentId:parentId,
			parent:parentName
		},
		actions:{
			add:function(){
				let params = this.getParamMap();
				$.ajax({
					url:'organization!add.do',
					data:params,
					type:'post'
				}).done(res=>{
					if(res.success){
						alert("操作成功");
						router.goRoute("organization");
					}else{
						alert("操作失败");
					}
				}).fail(e=>{
					alert("操作异常");
				})
			}
		}
	});
	
	
};
return module.exports;});