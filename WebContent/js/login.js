

$(function(){
	
	function login(){
		var username = this.username;
		var password = this.password;
		
		$.blockUI({
			message:'正在登录，请稍候。。。。'
		});
		$.ajax({
			url:'login.do',
			type:'post',
			data:{
				username:username,
				password:password
			},
			success:function(data){
				
				$.unblockUI();
				
				if(data.success){
					window.location.reload();
				}else{
					alert("登陆失败");
				}
			}
		});
	}
	
	new Vue({
		el:'#main-content',
		data:{
			username:'',
			password:''
		},
		methods:{
			login:login
		}
	});
});