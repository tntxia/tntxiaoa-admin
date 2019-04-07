(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('notice',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
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
		url:'notice!list.do',
		target:target,
		cols:[{
			label:'主题',
			field:'titel'
		},{
			label:'发布人',
			field:'username'
		},{
			label:'发布日期',
			field:'fvdate'
		},{
			label:'阅读部门',
			field:'dept'
		},{
			label:'状态',
			field:'states'
		},{
			label:'操作',
			field:'id',
			renderer:function(value,data){
				var button = $("<button>",{
					text:'编辑',
					click:function(){
						var data = $(this).data("data");
						router.goRoute("notice_edit",{
							id:data.id
						});
					}
				});
				button.data("data",data);
				return button;
			}
		}]
	});
	grid.init();
		
};
return module.exports;});