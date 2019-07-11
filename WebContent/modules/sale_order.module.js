(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('sale_order',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/sale_order.html';
exports.init = function(){
	
	new Vue({
		el: '#vue',
		data: {
			form: {
				number: null,
				sdate: null,
				edate: null
			},
			dataset: {
				url: 'sale!list.do'
			}
		},
		methods: {
			query() {
				let datagrid = this.$refs["datagrid"];
				datagrid.setParams(this.form);
				datagrid.loadData();
			},
			batchDel() {
				let datagrid = this.$refs["datagrid"];
				let selectedRows = datagrid.getSelectedRows();
				let ids = selectedRows.map(row=> row.id);
				console.log(selectedRows);
				$.ajax({
					url:'sale!batchDel.do',
					data: {
						ids: ids.join(",")
					},
					type: 'post'
				}).done(res=>{
					this.query();
				})
			}
		}
	})
	
};
return module.exports;});