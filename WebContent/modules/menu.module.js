(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('menu',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/menu.html';
exports.init = function(){
	
	let target = $("#datagrid");
	
	$("#addBtn").click(function(){
		router.goRoute("menu_add");
	});
	
	$("#exportBtn").click(function(){
		
		$.ajax({
			url:'menu!export.do',
			data:{
				
			}
		}).done(function(data){
			console.log(data);
			var iframe = $("<iframe>");
			iframe.hide();
			$("body").append(iframe);
			iframe.attr("src","/file_center/file!download.do?uuid="+data.uuid);
		})
	});
	
	$("#importBtn").click(function(){
		
		if(!$("#hiddenUploadForm").length){
			var hiddenUploadForm = $("<form>",{
				id:'hiddenUploadForm',
				action:'menu!importMenu.do',
				enctype:'multipart/form-data',
				method:'post'
			});
			
			var input = $("<input>",{
				type:'file',
				id:'hiddenFormInput',
				name:'file'
			});
			hiddenUploadForm.append(input);
			hiddenUploadForm.hide();
			$("body").append(hiddenUploadForm);
		}
		
		$("#hiddenFormInput").val("");
		
		$("#hiddenFormInput").unbind("change").bind("change",function(){
			
			$("#hiddenUploadForm").ajaxSubmit({
				url:'menu!importMenu.do',
				type:'post',
				success:function(data){
					console.log(data);
				}
			})
		});
		$("#hiddenFormInput").click();
		
		
	});
	
	let grid = new BootstrapGrid({
		url:'menu!list.do',
		target:target,
		cols:[{
			label:'名称',
			field:'name'
		},{
			label:'关键字名称',
			field:'key_name'
		},{
			label:'URL',
			field:'url'
		},{
			label:'排序编号',
			field:'order_no'
		},{
			label:'操作',
			field:'id',
			renderer:function(value){
				
				var res = [];
				
				let but = $("<button>",{
					text:'编辑'
				});
				but.data("id",value);
				but.click(function(){
					let id = $(this).data("id");
					router.goRoute("menu_edit",{id:id});
				});
				res.push(but);
				
				let but2 = $("<button>",{
					text:'删除'
				});
				but2.data("id",value);
				but2.click(function(){
					
					let id = $(this).data("id");
					$.ajax({
						url:'menu!delete.do',
						data:{
							id
						}
					}).done(function(data){
						grid.load();
					});
					
				});
				
				res.push(but2);
				
				
				return res;
			}
		}]
	});
	grid.init();
		
};
return module.exports;});