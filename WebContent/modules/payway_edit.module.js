(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('payway_edit',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let id = router.getParams().id;
	
	let form = $("#form").buildform({
		url:'payway!detail.do',
		params:{
			id:id
		},
		actions:{
			sub(){
				var params = this.getParamMap();
				$.ajax({
					url:'payway!update.do',
					type:'post',
					data:params
				}).done(res=>{
					if(res.success){
						alert("修改成功");
						router.goRoute("payway");
					}else{
						alert("修改失败");
					}
				}).fail(e=>{
					alert("修改异常");
				})
				
			}
		}
	});
};
return module.exports;});