exports.init = function(){
	
	$.ajax({
		url:'design/stamp!detail.do',
		success:function(data){
			$("[name=width]").val(data.width);
			$("[name=height]").val(data.height);
		}
	});
	
	$("#sub").click(function(){
		$("form").ajaxSubmit();
		
	});
	
	
};