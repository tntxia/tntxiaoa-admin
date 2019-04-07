(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('restrain_add',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/restrain_add.html';
exports.init = function(){
	
	var form = $("#form").buildform({
		
		actions:{
			back(){
				router.goRoute("restrain");
			},
			sub(){
				var params = form.getParamMap();
				$.ajax({
					url:'restrain!add.do',
					type:'post',
					data:params
				}).done(function(data){
					if(data.success){
						router.goRoute("restrain");
					}else{
						alert("操作失败");
					}
				}).fail(function(e){
					console.error(e);
				});
			}
			
		}
		
	});
	
};
return module.exports;});