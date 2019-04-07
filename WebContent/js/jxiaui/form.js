$.fn.fillForm = function(opt){
	var form = this;
	$.each(opt,function(i,d){
		
		var input = form.find("[name="+i+"]");
		
		if(d.type=="select"){
			
			var selectOpt = d.selectOpt;
			selectOpt.selected = d.value;
			input.fillSelect(selectOpt);
		}else{
			input.val(d.value);
		}
		
	});
	
}