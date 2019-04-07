$(function(){
	
	$("#datagrid").datagrid({
		url:'restrainMenu.do',
		data:{
			method:'list'
		},
		title:"权限菜单管理",
		cols:[{
			label:'ID',
			field:'id'
		},{
			label:'名称',
			field:'restrain_name'
		},{
			label:'菜单权限',
			renderer:function(){
				
				var button = $("<button>",{
					text:'菜单权限',
					click:function(){
						
						var rowData = $(this).parent().parent().data("data");
						var name = rowData.restrain_name;
						var id = rowData.id;
						
						var content = $("<div>");
						var ul = $("<ul>");
						content.append(ul);
						var dialog = $.dialog({
							title:'菜单权限 - '+name,
							width:200,
							height:200,
							content:content,
							buttons:[{
								text:'确定',
								click:function(){
									
									var menus = "";
									var selects = $(this).parent().parent().find(":checkbox:checked");
									selects.each(function(i){
										if(menus===""){
											menus = this.value;
										}else{
											menus += ","+this.value;
										}
										
									});
									
									$.ajax({
										url:'restrainMenu.do',
										data:{
											id:id,
											menus:menus,
											method:'setMenu'
										},
										success:function(data){
											dialog.remove();
											
										}
									});
								}
							}]
						});
						
						function fillUl(data){
							$.each(data,function(i){
								var li = $("<li>");
								var checkbox = $("<input>",{type:'checkbox',name:'menu',value:this.id});
								li.append(checkbox);
								li.append(this.name);
								ul.append(li);
							});
						}
						
						function setChecked(){
							$.ajax({
								url:'restrainMenu.do',
								data:{
									method:'getMenus',
									id:id
								},
								success:function(data){
									ul.find(":checkbox").each(function(i){
										var checkbox = $(this);
										var val = checkbox.val();
										$.each(data.ids,function(i,id){
											
											if(val==id){
												checkbox.attr("checked","checked");
												return false;
											}
										});
									});
								}
							});
						}
						
						if($("body").data("menuList")){
							var menuList = $("body").data("menuList");
							fillUl(menuList);
							setChecked();
						}else{
							$.ajax({
								url:'menu.do',
								data:{
									method:'list'
								},
								success:function(data){
									var rows = data.rows;
									fillUl(rows);
									setChecked();
									$("body").data("menuList",rows);
								}
							});
						}
						
					}
				});
				return button;
			}
		}]
	});
});