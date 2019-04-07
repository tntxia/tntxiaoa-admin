$(function(){
	
	$("#purchasingSearchBut").click(function(){
		var number = $("[name=number]").val();
		$("#datagrid").datagrid('load',{
			number:number
		});
	});
	
	$("body").on("click","#updateStatusBut",function(){
		var status = $("[name=status]").val();
		var id = $("[name=id]").val();
		$.ajax({
			url:'../order.do',
			type:'post',
			data:{
				method:'updatePurchasingStatus',
				status:status,
				id:id
			},
			success:function(data){
				if(data.success){
					alert("操作成功！");
					window.location.reload();
				}else{
					alert("操作失败！");
				}
			},
			error:function(){
				alert("获取服务失败");
			}
		});
	});
	
	var number = $("[name=number]").val();
	$("#datagrid").datagrid({
		title:'采购订单列表',
		url:'../order.do',
		data:{
			method:'listPurchasing',
			number:number
		},
		cols:[{
			label:'订单号',
			field:'number',
			
			renderer:function(data){
				var a = $("<a>",{
					text:data.number,
					click:function(){
						var id = data.id;
						$.dialog({
							width:500,
							height:500,
							url:'purchasing/edit.mvc',
							data:{
								id:id
							}
						});
					}
				});
				return a;
			}
		},{
			label:'负责人',
			field:'man'
		},{
			label:'状态',
			field:'l_spqk'
		}]
	});
});