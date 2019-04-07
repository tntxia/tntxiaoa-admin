(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('config',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/config.html';
exports.init = function(){
new Vue({
	el:'#app',
	data:{
		path:null,
		company_name:null,
		company_name_en:null
	},
	created:function(){
		this.init();
	},
	methods:{
		
		chooseDir:function(){
			
			console.log("chooseDir");
			
			var offset = $(".dir-choose-button").offset();
			
			var div = $("<div>",{
				'class':'ztree'
			});
			div.css({
				width:200,
				height:200,
				position:'absolute',
				top:offset.top+20,
				left:offset.left+20,
				border:'1px solid #ccc',
				"overflow-y":"auto"
			});
			$("body").append(div);
			
			var setting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false
				},
				data: {
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pId",
						rootPId: ""
					}
				},
				callback: {
					beforeDblClick: function(treeId, treeNode) {
						if(!treeNode.children){
							findSub(treeNode);
						}
						
					}
				}
			};
			
			var zNodes =[
				{id:1, pId:0, name:"文件选择", open:true}
				
			];
			
			var tree = $.fn.zTree.init(div, setting, zNodes);
			
			var nodes = tree.getNodes();
			
			$.ajax({
				url:'choose!root.do'
			}).done(function(data){
				console.log(data);
				$.each(data,function(i,r){
					tree.addNodes(nodes[0],{
						id:r.absolutePath,
						pId:1,
						name:r.name,
						icon:'css/images/disk.png'
					});
				})
				
			}).fail(function(e){
				console.log(e);
			})
			
			function findSub(treeNode){
				$.ajax({
					url:'choose!dir.do',
					data:{
						parent:treeNode.id
					}
				}).done(function(list){
					
					$.each(list,function(i,r){
						
						var icon;
						if(r.dir){
							icon = 'css/images/dir.png'
						}else{
							icon = 'css/images/file.png'
						}
						tree.addNodes(treeNode,{
							id:r.absolutePath,
							pId:treeNode.id,
							name:r.name,
							icon:icon
						});
					})
					
				});
			}
			
			
		},
		init:function(){
			
			var vm = this;
			
			$.ajax({
				url:'config!detail.do',
				success:function(data){
					vm.path = data.path;
					vm.company_name = data.company_name;
					vm.company_name_en = data.company_name_en;
				}
			});
			
		},
		sub:function(){
			var path = this.path;
			var company_name = this.company_name;
			var company_name_en = this.company_name_en;
			$.ajax({
				url:'config!save.do',
				type:'post',
				data:{
					path:path,
					company_name:company_name,
					company_name_en:company_name_en
				},
				success:function(data){
					if(data.success){
						alert("操作成功");
					}else{
						alert("操作失败");
					}
				}
			});
		}
	}
});
};
return module.exports;});