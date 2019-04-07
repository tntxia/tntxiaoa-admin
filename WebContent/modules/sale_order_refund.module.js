(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('sale_order_refund',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sale_order_refund.html';
exports.init = function(){
	
	let form = $("#form").buildform({
		actions: {
			batchDel() {
				let param = this.getParamMap();
				$.ajax({
					url:'sale!batchDelRefund.do',
					data: param,
					type: 'post'
				}).done(res=>{
					
				})
			}
		}
	})
	
	let target = $("#datagrid");
	
	let grid = new BootstrapGrid({
		check: true,
		url:'sale!listRefund.do',
		target:target,
		cols:[{
			label:'单号',
			field:'number'
		},{
			label:'客户名称',
			field:'coname'
		},{
			label:'责任人',
			field:'man'
		},{
			label:'下单时间',
			field:'datetime'
		}]
	});
	grid.init();
};
return module.exports;});