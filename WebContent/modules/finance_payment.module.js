(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('finance_payment',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/finance_payment.html';
exports.init = function(){
	
	let form = $("#form").buildform({
		actions: {
			batchDel() {
				let param = this.getParamMap();
				$.ajax({
					url:'finance!batchDelPayment.do',
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
		url:'finance!listPayment.do',
		target:target,
		cols:[{
			label:'单号',
			field:'contract'
		},{
			label:'供应商名称',
			field:'supplier'
		},{
			label:'付款时间',
			field:'sjfkdate'
		}]
	});
	grid.init();
};
return module.exports;});