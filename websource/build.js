
var fs= require("fs");

console.log(__dirname);

readPackageJson(__dirname+'\\package.json',function(data){
	writeToFile(data);
});

function readPackageJson(path,callback){
	fs.readFile(path,function(err,data){
	    if(err){
	        console.log("can't find package.json!!package json path",path);
	    }else{
	    	callback(JSON.parse(data));
	    }
	});
}

function writeToFile(data){
	
	var DomTree = require("./common/DomTree.js");
	
	// 定义所有的页面
	var pages = data.pages;
	var webContent = data.webContent;
	
	$each(pages,function(i,page){
		
		var dom = new DomTree();
		var head = dom.append("head");
		var target = page.target;
		var title = page.title;
		var name = page.name;
		
		var pageRootPath = "page/"+name+"/";
		var bodyFile = pageRootPath+page.body;
		
		
		
		if(title){
			head.append({
				name:'title',
				text:title
			});
		}
		
		head.append({
			name:'meta',
			attributes:{
				charset:'utf-8'
			}
		});
		
		var css = page.css;
		if(css && css.length){
			for(var i=0;i<css.length;i++){
				var c = css[i];
				head.append({
					name:'link',
					isSingle:true,
					attributes:{
						rel:'stylesheet',
						href:'${basePath}/'+c
					}
				});
			}
		}
		
		var scripts = page.scripts;
		
		if(scripts && scripts.length){
			$each(scripts,function(i,d){
				
				var scriptEl = createScript();
				
				if(d.content){
					scriptEl.text = d.content
				}else{
					scriptEl.attributes.src = "${basePath}/"+d;
				}
				
				head.append(scriptEl);
			});
		}
		
		var modules = page.modules;
		
		if(modules && modules.length){
			$each(modules,function(i,d){
				
				var name = d.name;
				
				var modulePackage = d["package"];
			
				var moduleFilePath = "modules/"+name+".module.js";
				
				if(modulePackage){
					
					var packageJsonPath = pageRootPath +"modules/" + modulePackage + "/package.json";
					
					readPackageJson(packageJsonPath,function(modulePackageJson){
						
						
						console.log(modulePackageJson.jshandler);
						var jshandler = require(modulePackageJson.jshandler);
						var jshandlerData = modulePackageJson.jshandlerData;
						if(!jshandlerData){
							jshandlerData = {};
						}
						jshandlerData.moduleName = name;
						var moduleInitJs = modulePackageJson["module-init-js"];
						if(!moduleInitJs){
							moduleInitJs = "init.js";
						}
						jshandlerData["module-init-js"] = moduleInitJs;
						
						var template = modulePackageJson.template;
						if(!template){
							template = "template.html";
						}
						
						var sourceTemplatePath = pageRootPath +"modules/" + modulePackage +"/"+template;
						var targetTemplatePath = webContent+"/template/"+name+".html";
						console.log("copy file from "+sourceTemplatePath+" to "+targetTemplatePath);
						
						copyFile(sourceTemplatePath,targetTemplatePath);
						
						jshandlerData.template = template;
						jshandlerData.modulePackage = modulePackage;
						jshandlerData.webContent = webContent;
						jshandlerData.pageRootPath = pageRootPath;
						var moduleFileLines = jshandler.getFileLines(jshandlerData);
						
						var template = data.template;
						
						fs.writeFileSync(webContent+"/"+moduleFilePath,moduleFileLines.join("\n"));
						
						console.log(data);
					});
					
				}else{
					var file = d.jsfile;
					
					
					var moduleFileLines = new Array();
					moduleFileLines.push("(function(name,module){");
					moduleFileLines.push("if(!window.modules){");
					moduleFileLines.push("window.modules=Object.create(null);");
					moduleFileLines.push("};");
					moduleFileLines.push("window.modules[name]=module();");
					moduleFileLines.push("})('"+name+"',function(){");
					moduleFileLines.push("var module=Object.create(null);");
					moduleFileLines.push("var exports = Object.create(null);");
					moduleFileLines.push("module.exports=exports;");
					
					// 判断是否有模板文件的设置
					if(d.templatefile){
						moduleFileLines.push("exports.template = '"+d.templatefile+"';");
						copyFile(pageRootPath+d.templatefile,webContent+"/"+d.templatefile);
						
					}
					
					// 判断是否有模块JS的设置
					if(file){
						moduleFileLines.push("exports.init = function(){");
						moduleFileLines.push(fs.readFileSync(pageRootPath+file));
						moduleFileLines.push("};");
					}
					
					moduleFileLines.push("return module.exports;});");
					
					fs.writeFileSync(webContent+"/"+moduleFilePath,moduleFileLines.join("\n"));
				}
				
				var scriptEl = createScript();
				scriptEl.attributes.src = "${basePath}/"+moduleFilePath;
				head.append(scriptEl);
				
				
			});
		}
		
		
		var body = dom.append("body");
		
		var bodyContent = fs.readFileSync(bodyFile);
		
		body.text = bodyContent;
		
		console.log("generate file");
		
		dom.generateFile({
			target:webContent+"/"+target
		});
		
	});
	
	function createScript(){
		return Object.create({
			name:'script',
			attributes:{
				type:'text/javascript'
			}
		});
	}
	
	function copyFile(srcFile,destFile){
		var readable = fs.createReadStream( srcFile );
        // 创建写入流
        var writable = fs.createWriteStream( destFile ); 
        // 通过管道来传输流
        readable.pipe( writable );
	}
	
}

function $each(arr,callback){
	for(var i=0;i<arr.length;i++){
		var a = arr[i];
		callback(i,a);
	}
}


