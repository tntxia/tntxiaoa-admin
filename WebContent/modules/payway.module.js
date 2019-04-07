(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('payway',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let target = $("#datagrid");
	
	$("#addBtn").click(function(){
		router.goRoute("payway_add");
	});
	
	let grid = new BootstrapGrid({
		url:'payway!list.do',
		target:target,
		cols:[{
			label:'支付方式',
			field:'payment'
		},{
			label:'操作',
			field:'id',
			renderer:function(value){
				
				var res = new Array();
				
				let but = $("<button>",{
					text:'编辑'
				});
				but.data("id",value);
				but.click(function(){
					let id = $(this).data("id");
					router.goRoute("payway_edit",{id:id});
				})
				res.push(but);
				
				but = $("<button>",{
					text:'删除'
				});
				but.data("id",value);
				but.click(function(){
					let id = $(this).data("id");
					$.ajax({
						url:'payway!del.do',
						data:{
							id:id
						}
					}).done(function(data){
						if(data.success){
							alert("删除成功");
						}else{
							alert("删除失败");
						}
					}).fail(function(e){
						alert("删除异常");
					})
				})
				res.push(but);
				
				return res;
			}
		}]
	});
	grid.init();
		
};
return module.exports;});