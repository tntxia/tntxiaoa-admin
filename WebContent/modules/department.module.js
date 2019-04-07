(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('department',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/department.html';
exports.init = function(){

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
		this.fetchData();
	},
	methods : {
		fetchData:function(){
			var vm = this;
			$.ajax({
				url : 'dept!list.do',
				success : function(data) {
					vm.rows = data.rows;
				},
				error : function() {
					alert("请求失败");
				}
			});
		},
		
		toAdd:function(){
			
			var vm = this;
			BootstrapUtils.createDialog({
				id:"addModal",
				template:'template/department/department_add.html',
				init:function(){
					
					this.find("[name=dept_types]").fillSelect({
						url:'dept!getDepartmentTypeList.do',
						handler:function(data){
							var list = new Array();
							$.each(data,function(i,d){
								list.push({
									text:d.dict_value,
									value:d.dict_key
								});
							});
							return list;
						}
					});
					this.find("[name=leader]").fillSelect({
						url:'user!listAll.do',
						handler:function(data){
							var list = new Array();
							$.each(data,function(i,d){
								list.push({
									text:d.name,
									value:d.name
								})
							});
							return list;
						}
					});
				},
				onConfirm:function(){
					
					var paramMap = this.getParamMap();
					if(!paramMap.departname){
						alert("请输入部门名称");
						return;
					}
					if(!paramMap.dept_code){
						alert("请输入部门编码");
						return;
					}
					$.ajax({
						url:'dept!add.do',
						data:paramMap
					}).done(function(data){
						if(data.success){
							$("#addModal").modal('hide');
							vm.fetchData();
							
						}else{
							alert("操作失败");
						}
					}).fail(function(e){
						alert("操作异常");
					});
					console.log(paramMap);
				}
			});
			
			$("#addModal").modal('show');
		},
		add:function(){
			var vm = this;
			var newRow = vm.newRow;
			if($("#addModal [name=ipbd]").prop("checked")){
				newRow.ipbd = 'Y';
			}else{
				newRow.ipbd = 'N';
			}
			$.ajax({
				url : 'user!add.do',
				type:'post',
				data:newRow,
				success : function(data) {
					if(data.success){
						vm.fetchData();
						$("#addModal").modal("hide");
					}else{
						alert("操作失败");
					}
				},
				error : function() {
					alert("请求失败");
				}
			});
		},
		
		unlock:function(id){
			unlock(id);
		},
		toEdit : function(r) {
			
			var vm = this;
			BootstrapUtils.createDialog({
				id:"editModal",
				template:'template/department/department_edit.html',
				init:function(){
					
					this.fillForm({
						"id":{
							value:r.id
						},
						"departname":{
							value:r.departname
						},
						"dept_code":{
							value:r.dept_code
						},
						"remark":{
							value:r.remark
						},
						"dept_types":{
							type:'select',
							value:r.dept_types,
							selectOpt:{
								url:'dept!getDepartmentTypeList.do',
								handler:function(data){
									var list = new Array();
									$.each(data,function(i,d){
										list.push({
											text:d.dict_value,
											value:d.dict_key
										});
									});
									return list;
								}
							}
						},
						"leader":{
							type:'select',
							value:r.leader,
							selectOpt:{
								url:'user!listAll.do',
								handler:function(data){
									var list = new Array();
									$.each(data,function(i,d){
										list.push({
											text:d.name,
											value:d.name
										});
									});
									return list;
								}
							}
						}
					});
				},
				onConfirm:function(){
					
					var paramMap = this.getParamMap();
					if(!paramMap.departname){
						alert("请输入部门名称");
						return;
					}
					if(!paramMap.dept_code){
						alert("请输入部门编码");
						return;
					}
					$.ajax({
						url:'dept!update.do',
						data:paramMap
					}).done(function(data){
						if(data.success){
							$("#editModal").modal('hide');
							vm.fetchData();
							
						}else{
							alert("操作失败");
						}
					}).fail(function(e){
						alert("操作异常");
					});
					console.log(paramMap);
				}
			});
			
			$("#editModal").modal('show');
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
			if (confirm("确实要删除部门吗？")) {
				var vm = this;
				$.ajax({
					url : 'dept!delete.do',
					type : 'post',
					data : {
						id:id
					},
					success : function(data) {
						if(data.success){
							vm.fetchData();
						}else{
							alert("操作失败");
						}
					}
				});
			}
		},
		onChangeIPBind : function(){
			if($("#addModal [name=ipbd]").prop("checked")){
				this.newRow.ipbd = 'Y';
			}else{
				this.newRow.ipbd = 'N';
			}
		},
		onChangeIPBindEdit : function(){
			if($("#editModal [name=ipbd]").prop("checked")){
				this.currRow.ipbd = 'Y';
			}else{
				this.currRow.ipbd = 'N';
			}
		},
		toUpdatePassword:function(r){
			VueManager.setVueData("updatePasswordVue","id",r.nameid);
			$("#updatePasswordModal").modal('show');
		}
		
	}
});

};
return module.exports;});