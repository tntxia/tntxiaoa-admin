(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('restrain_right',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/restrain_right.html';
exports.init = function(){
	
	let target = $(".ztree");
	let id = router.getParams().id;
	let ztree = new EasyZTree({
		check:true,
		target:target,
		url:'restrain!getRightZTreeNode.do?id='+id
	});
	
	$("#subBtn").click(function(){
		
		var tree = ztree.getTree();
		let checkedNodes = tree.getCheckedNodes();
		let rightIds = new Array();
		$.each(checkedNodes,function(i,n){
			let id = n.id;
			if(id.indexOf("right_")==0){
				rightIds.push(id.replace("right_",""));
			}
		});
		$.ajax({
			url:'restrain!addRoleRight.do',
			data:{
				id:id,
				rightIds:rightIds.join(",")
			}
		}).done(data=>{
			if(data.success){
				router.goRoute("restrain");
			}else{
				alert("操作失败");
			}
		}).fail(e=>{
			console.log(e)
		})
		console.log("id,rightIds",id,rightIds);
		
	})
	
	
	
};
return module.exports;});