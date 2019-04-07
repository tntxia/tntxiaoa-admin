(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('contact_template',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/template.html';
exports.init = function(){
var tkeditor;

$.ajax({
	url : 'contactTemplate!list.do',
	success : function(data) {
		new Vue({
			el : '#vue',
			data : {
				rows : data.rows,
				currRow : {}
			},
			methods : {
				
				add : function(){
					var dialog = BootstrapUtils.createDialog({
						id:'newTemplateDialog',
						title:'新增模板',
						width:700,
						template:"template/order_template/order_template_add.html",
						init:function(){
							var now = new Date();
							var year = now.getFullYear();
							var month = now.getMonth()+1;
							var date = now.getDate();
							$("#createDateLabel").html(year+"-"+month+"-"+date);
						},
						onConfirm:function(){
							var params = this.getParamMap();
							$.ajax({
								url:'contactTemplate!create.do',
								type : 'post',
								data : params,
								success : function(data) {
									if (data.success) {
										$("#newTemplateDialog").modal('hide');
									} else {
										alert("操作失败！");
									}
								}
							})
						}
					});
					$("#newTemplateDialog").modal('show');
				},
				toEdit : function(r) {
					this.currRow = r;
					try {
						tkeditor = CKEDITOR.replace('tkEditor');
					} catch (e) {
					}

					tkeditor.setData(r.q_tk);

					$("#updateModal").modal("show");
				},
				update : function() {
					var row = this.currRow;
					row.q_tk = tkeditor.getData();
					$.ajax({
						url : 'contactTemplate!update.do',
						type : 'post',
						data : row,
						success : function(data) {
							if (data.success) {
								$("#updateModal").modal("hide");
							} else {
								alert("操作失败！");
							}
						}
					});
				},
				del : function(id) {

				}
			}
		});

	},
	error : function() {
		alert("请求失败");
	}
});
};
return module.exports;});