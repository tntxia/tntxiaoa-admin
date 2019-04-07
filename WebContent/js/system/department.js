$(function(){
	
	function crud(data){
		
		function getDicValue(list,type){
			var res = "";
			$.each(list,function(i,d){
				if(d.dict_key==type){
					res = d.dict_value;
				}
			});
			return res;
		}
		
		var departDic = data.rows;
		
		$("#crud").crud({
			title:"部门管理",
			idField:"id",
			cols:[{
				label:'ID',
				field:'id'
			},{
				label:'名称',
				field:'departname'
			},{
				label:'部门类型',
				field:'dept_types',
				type:'select',
				opt:{
					dataset:{
						url:'dict.do',
						data:{
							method:'list'
						},
						label:'dict_value',
						field:'dict_key'
					},
					defaultOption:{
						text:'请选择',
						value:''
					}
					
				},
				renderer:function(data){
					return getDicValue(departDic,data.dept_types);
				}
			},{
				label:'部门编码',
				field:'dept_code'
			},{
				label:'备注',
				field:'remark'
			},{
				label:'主管',
				field:'leader'
			}],
			list:{
				url:'dept.do',
				data:{
					method:'list'
				}
			},
			create:{
				url:'dept.do',
				data:{
					method:'update'
				}
			},
			update:{
				url:'dept.do',
				data:{
					method:'update'
				}
			},
			del:{
				url:'dept.do',
				data:{
					method:'delete'
				}
			}
		});
	}
	
	$.ajax({
		url:'dict.do',
		data:{
			method:'list',
			dictType:'department_type'
		}
	}).done(crud);
	
	
});