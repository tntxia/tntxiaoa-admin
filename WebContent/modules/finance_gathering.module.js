(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('finance_gathering',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/finance_gathering.html';
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
		url:'finance!listGathering.do',
		target:target,
		cols:[{
			label:'单号',
			field:'orderform'
		},{
			label:'客户名称',
			field:'coname'
		},{
			label:'收款时间',
			field:'sjskdate'
		}]
	});
	grid.init();
};
return module.exports;});