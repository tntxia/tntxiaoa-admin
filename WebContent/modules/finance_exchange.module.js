(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('finance_exchange',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/finance_exchange.html';
exports.init = function(){
	
	let form = $("#form").buildform({
		actions: {
			batchDel() {
				let param = this.getParamMap();
				$.ajax({
					url:'finance!batchDelExchange.do',
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
		url:'finance!listExchange.do',
		target:target,
		cols:[{
			label:'销售单号',
			field:'xsdh'
		},{
			label:'客户名称',
			field:'l_coname'
		},{
			label:'创建时间',
			field:'l_date'
		}]
	});
	grid.init();
};
return module.exports;});