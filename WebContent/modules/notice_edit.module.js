(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('notice_edit',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	var id = router.getParam('id');
	
	$("#form").buildform({
		url:'notice!detail.do',
		params:{
			id:id
		},
		actions:{
			sub(){
				var params = this.getParamMap();
				$.ajax({
					url:'notice!update.do',
					type:'post',
					data:params
				}).done(res=>{
					if(res.success){
						alert("修改成功");
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