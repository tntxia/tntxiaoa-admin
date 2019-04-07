$.fn.getParamMap = function(escapeFlag) {
	var res = {};
	this.find(":input").each(function(i, inp) {

		if (inp.name && inp.name != "") {

			var value;
			if (escapeFlag) {
				value = escape($.trim(inp.value));
			} else {
				value = inp.value;
			}
			
			// �����checkbox,��ѡ���򲻸�ֵ
			if(inp.type=="checkbox"){
				if(!$(inp).prop("checked")){
					return;
				}
			}

			if (res[inp.name]) {
				var orignValue = res[inp.name];
				if (typeof orignValue == "string") {
					var arr = [];
					arr.push(orignValue);
					arr.push(value);
					res[inp.name] = arr;
				} else {
					res[inp.name].push(value);
				}
			} else {
				res[inp.name] = value;
			}
		}
	});
	return res;
};
