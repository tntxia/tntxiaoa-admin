(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('restrain',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/restrain.html';
exports.init = function(){
	
	var ztree;
new Vue({
	el : '#vue',
	data : {
		rows : [],
		positionList:[],
		restrainList:[],
		departmentList:[],
		newRow:{
			ipbd:'N'
		},
		currRow : {
			ipbd:'N'
		}
	},
	created:function(){
		this.initData();
	},
	methods : {
		initData:function(){
			this.fetchData();
		},
		fetchData:function(){
			var vm = this;
			$.ajax({
				url : 'restrain!list.do',
				success : function(data) {
					vm.rows = data.rows;
				},
				error : function() {
					alert("请求失败");
				}
			});
		},
		
		toAdd:function(){
			
			router.goRoute("restrain_add");
		
		},
		viewRight:function(id){
			router.goRoute("restrain_right",{
				id:id
			});
		},
		viewMenu:function(id){
			router.goRoute("restrain_menu",{
				id:id
			});
		},
		toEdit : function(r) {
			
			this.currRow = r;
			$("#editModal").modal("show");
		},
		update : function() {
			
			var vm = this;
			var row = vm.currRow;
			row.sex = $("[name=sex]:checked").val();
			if($("#editModal [name=ipbd]").prop("checked")){
				row.ipbd = 'Y';
			}else{
				row.ipbd = 'N';
			}
			$.ajax({
				url : 'user!update.do',
				type : 'post',
				data : row,
				success : function(data) {
					if (data.success) {
						$("#editModal").modal("hide");
						vm.fetchData();
					} else {
						alert("操作失败");
					}
				}
			});
		},
		del : function(id) {
			
			if (confirm("确实要删除用户吗？")) {
				var vm = this;
				$.ajax({
					url : 'restrain!del.do',
					type : 'post',
					data : {
						id:id
					},
					success : function(data) {
						if(data.success){
							vm.fetchData();
							$("#updateModal").modal("hide");
						}else{
							alert("操作失败");
						}
					}
				});
			}
		}
		
	}
});
	
};
return module.exports;});