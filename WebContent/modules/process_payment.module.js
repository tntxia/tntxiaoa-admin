(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('process_payment',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	$("#addBtn").click(function(){
		router.goRoute("process_payment_add");
	});
	
	let target = $("#datagrid");
	let grid = new BootstrapGrid({
		url:'proccess/payment!list.do',
		target:target,
		cols:[{
			label:'所属部门',
			field:'dept'
		},{
			label:'审核人',
			field:'audit_man'
		},{
			label:'操作',
			field:'id',
			renderer:function(value){
				
				let but = $("<button>",{
					text:'编辑'
				});
				but.data("id",value);
				but.click(function(){
					let id = $(this).data("id");
					router.goRoute("process_prepaid_edit",{id:id});
				})
				return but;
			}
		}]
	});
	grid.init();
	
};
return module.exports;});