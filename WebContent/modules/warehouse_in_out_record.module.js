(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('warehouse_in_out_record',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/warehouse_in_out_record.html';
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
		url:'warehouse!inOutRecordList.do',
		cols:[{
			label:'订单编号',
			field:'number'
		},{
			label:'型号',
			field:'model'
		},{
			label:'操作人',
			field:'operater'
		},{
			label:'原始数量',
			field:'orgin_num'
		},{
			label:'修改数量',
			field:'change_num'
		},{
			label:'最终数量',
			field:'final_num',
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
							url:'warehouse!updateInOutRecord.do',
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