$(function(){
	$("#submit").click(function(){
		
		var name = $("[name=companyName]").val();
		var website = $("[name=website]").val();
		
		$.ajax({
			url:'systeminfo.do',
			type:'post',
			data:{
				method:'update',
				name:name,
				website:website
			},
			success:function(data){
				if(data.success){
					alert("操作成功！");
				}else{
					alert("操作失败");
				}
			},
			error:function(e){
				alert("后台访问异常！");
			}
		});
	});
});