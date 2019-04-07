function startStatic(){
	var path = $("#path").val();
	$.ajax({
		url:'../static.do',
		type:'post',
		data:{
			path:path
		}
	}).done(function(){
		alert("操作成功");
	});
}

$(function(){
	$.ajax({
		url:'../static.do',
		type:'post',
		data:{
			method:'getStaticInfo'
		}
	}).done(function(data){
		if(data)
			$("#path").val(data.export_path);
	});
});