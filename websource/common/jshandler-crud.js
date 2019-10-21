module.exports.getFileLines = function(jshandlerData){
	
	var cols = jshandlerData.cols;
	
	var template = jshandlerData.template;
	
	var pageRootPath = jshandlerData.pageRootPath;
	
	var packageName = jshandlerData.modulePackage;
	var webContent = jshandlerData.webContent;
	

	
	var file = jshandlerData["module-init-js"];
	
	var moduleFileLines = new Array();
	moduleFileLines.push("(function(name,module){");
	moduleFileLines.push("if(!window.modules){");
	moduleFileLines.push("window.modules=Object.create(null);");
	moduleFileLines.push("};");
	moduleFileLines.push("window.modules[name]=module();");
	moduleFileLines.push("})('"+jshandlerData.moduleName+"',function(){");
	moduleFileLines.push("var module=Object.create(null);");
	moduleFileLines.push("var exports = Object.create(null);");
	moduleFileLines.push("module.exports=exports;");
	
	
	// 判断是否有模板文件的设置
	if(template){
		moduleFileLines.push("exports.template = 'template/"+template+"';");
		copyFile(pageRootPath+"modules/"+packageName+"/"+template,webContent+"/template/"+template);
		
	}
	
	moduleFileLines.push("exports.init = function(){");
	
	// 判断是否有模块JS的设置
	if(file){
		var fs= require("fs");
		moduleFileLines.push(fs.readFileSync(pageRootPath+"/modules/"+packageName+"/"+file));
		
	}
	
	moduleFileLines.push("};");
	moduleFileLines.push("return module.exports;});");
	return moduleFileLines;
	
	function copyFile(srcFile,destFile){
		
		var fs= require("fs");
		
		var readable = fs.createReadStream( srcFile );
        // 创建写入流
        var writable = fs.createWriteStream( destFile ); 
        // 通过管道来传输流
        readable.pipe( writable );
	}
}