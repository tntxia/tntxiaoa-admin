$.fn.fillSelect = function(opt){
	var url = opt.url;
	var params = opt.params;
	var handler = opt.handler;
	var selected = opt.selected;
	var select = this;
	$.ajax({
		url:url,
		data:params
	}).done(function(data){
		var list = handler(data);
		$.each(list,function(i,d){
			var option = $("<option>",{
				value:d.value,
				text:d.text
			});
			if(d.value==selected){
				option.prop("selected",true);
			}
			select.append(option);
		})
	}).fail(function(e){
		console.error(e);
	});
}