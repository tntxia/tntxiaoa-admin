(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('restrain_menu',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let target = $(".ztree");
	let id = router.getParams().id;
	let ztree = new EasyZTree({
		check:true,
		target:target,
		url:'restrainMenu!getMenuZTreeNode.do?id='+id
	});
	
	$("#subBtn").click(function(){
		
		var tree = ztree.getTree();
		let checkedNodes = tree.getCheckedNodes();
		let menus = new Array();
		$.each(checkedNodes,function(i,n){
			let id = n.id;
			menus.push(id);
		});
		$.ajax({
			url:'restrainMenu!setMenu.do',
			data:{
				id:id,
				menus:menus.join(",")
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
		
	})
	
	
	
};
return module.exports;});