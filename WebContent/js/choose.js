
$(function(){
	
	new Vue({
		
		el:'#vue',
		data:{
			app:'',
			appList:[]
		},
		created:function(){
			var vm = this;
			$.ajax({
				url:'app.do',
				data:{
					method:'listAll'
				},
				success:function(data){
					vm.appList = data;
				}
			});
		},
		methods:{
			confirm:function(){
				var val = this.app;
				if(!val || val==""){
					alert("请选择一个应用");
					return;
				}
				$.ajax({
					url:'app.do',
					data:{
						method:'choose',
						app:val
					},
					success:function(data){
						if(data.success){
							window.location.reload();
						}else{
							alert("操作失败");
						}
					},
					error:function(e){
						alert("获取后台服务失败");
					}
				});
			},
			appManage:function(){
				window.location.href = "appManage.mvc";
			}
		}
	});
});