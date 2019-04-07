$(function(){
	var target = "";
	var menus = $.getJSON("json/menu.json").done(function(data){
		
		let menus = data.menus;
		let leftbar = $(".leftbar");
		$.each(menus,(i,item)=>{
			
			let titleDiv = $("<div>",{
				'class': 'menu-title',
				text: item.title
			});
			leftbar.append(titleDiv);
			console.log(item);
			
			let ul = $("<ul>");
			titleDiv.append(ul);
			
			let children = item.children;
			$.each(children,(i,c)=>{
				let li = $("<li>");
				let a = $("<a>",{
					href: c.url,
					text: c.title
				});
				li.append(a);
				ul.append(li);
			});
		})
		

	}).fail(function(){
		alert("fail");
	});
	
	// 绑定路由，设置路由变化影响的区域
	router.register({
		target:$("#mainPage"),
		defaultModule:'main'
	});
	
	new Vue({
		el:'#changePassModal',
		data:{
			pass:'',
			passConfirm:''
		},
		methods:{
			updatePass:function(){
				
				var pass = this.pass;
				var passConfirm = this.passConfirm;
				
				if(pass!=passConfirm){
					alert("两次输入的密码不一致！");
					return;
				}
				
				$.ajax({
					url:'modifyPass.do',
					data:{
						pass:pass
					}
				}).done(function(data){
					if(data.success){
						alert("修改成功");
					}else{
						alert("修改失败");
					}
				}).fail(function(e){
					alert("修改异常");
				});
			}
		}
	});
	
});

function changePassword(){
	$("#changePassModal").modal('show');
}

function logout(){
	$.ajax({
		url:'logout.do',
		success:function(data){
			if(data.success){
				window.location.reload();
			}else{
				alert("注销失败");
			}
		},
		error:function(){
			alert("注销异常");
		}
	})
}