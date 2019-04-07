(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('warehouse',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/warehouse.html';
exports.init = function(){
	
	let grid;
	
	let form = $("#form").buildform({
		
		actions:{
			// 查询
			"search":function(){
				let params = this.getParamMap();
				grid.load(params);
			}
		}
		
		
	});
	
	grid = new BootstrapGrid({
		target:$("#datagrid"),
		url:'warehouse!list.do',
		cols:[{
			label:'型号',
			field:'pro_model'
		},{
			label:'库存数量',
			field:'pro_num',
			editable:true
		},{
			label:'修改',
			renderer:function(value,row){
				let button = $("<button>",{
					text:'修改',
					click:function(){
						var row = $(this).data("row");
						console.log(row);
						$.ajax({
							url:'warehouse!update.do',
							type:'post',
							data:row
						}).done(res=>{
							console.log(res);
						})
					}
				});
				button.data("row",row)
				return button;
			}
		}]
	});
	grid.init();
};
return module.exports;});