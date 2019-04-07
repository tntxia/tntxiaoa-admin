(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('user_role',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let target = $(".ztree");
	let id = router.getParams().id;
	let ztree = new EasyZTree({
		check:true,
		target:target,
		url:'user!getRoleZTreeNode.do?id='+id
	});
	
	$("#subBtn").click(function(){
		
		var tree = ztree.getTree();
		let checkedNodes = tree.getCheckedNodes();
		let roles = new Array();
		$.each(checkedNodes,function(i,n){
			let id = n.id;
			roles.push(id);
		});
		$.ajax({
			url:'user!setRole.do',
			data:{
				id:id,
				roles:roles.join(",")
			}
		}).done(data=>{
			if(data.success){
				router.goRoute("user");
			}else{
				alert("操作失败");
			}
		}).fail(e=>{
			console.log(e)
		})
		
	})
	
	
	
};
return module.exports;});