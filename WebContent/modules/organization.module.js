(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('organization',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let tree;
	
	loadTree();
	
	function loadTree(){
		$(".ztree").empty();
		tree = new EasyZTree({
			url:'organization!tree.do',
			target:$(".ztree"),
			onFinish:function(){
				
			},
			contextmenu:{
				items:[{
					text:'增加子部门',
					click:function(treeNode){
						router.goRoute("organization_add",{
							parentId:treeNode.id,
							parentName:treeNode.name
						});
					}
				},{
					text:'删除',
					click:function(treeNode){
						if(confirm("是否确定删除此部门？")){
							$.ajax({
								url:'organization!delete.do',
								data:{
									id:treeNode.id
								}
							}).done(res=>{
								if(res.success){
									alert("删除成功");
									loadTree();
								}else{
									alert("删除失败");
								}
							}).fail(e=>{
								alert("删除异常");
							});
						}
					}
				}]
			},
			onDrop:function(sourceNodes,targetNode,location){
				
				$.each(sourceNodes,function(i,sourceNode){
					$.ajax({
						url:'organization!move.do',
						data:{
							sourceId:sourceNode.id,
							targetId:targetNode.id,
							location:location
						}
					}).done(res=>{
						
					}).fail(e=>{
						
					});
				})
				
			}
		});
	}
	
	
};
return module.exports;});