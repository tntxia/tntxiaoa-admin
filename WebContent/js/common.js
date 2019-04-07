(function(globe,name,fun){
	globe[name] = fun();
})(window,"OACommonSelects",function(){
	
	return {
		
		fillDepartmentSelect:function(opt){
			
			var sel = opt.sel;
			var defaultValue = opt.defaultValue;
			$.ajax({
				url:"dept!list.do",
				type:'post',
				success:function(data){
					
					if(data.rows){
						var rows = data.rows;
						$.each(rows,function(i,r){
							var option = $("<option>",{
								value:r.departname,
								text:r.departname
							});
							if(defaultValue==r.departname){
								option.prop("selected",true);
							}
							sel.append(option);
						});
					}else{
						$.each(data,function(i,r){
							var option = $("<option>",{
								value:r.departname,
								text:r.departname
							});
							sel.append(option);
						});
					}
					
					
				},
				dataType:'json'
			});
		},
		
		fillPositionSelect:function(opt){
			
			var sel = opt.sel;
			var defaultValue = opt.defaultValue;
			debugger;
			$.ajax({
				url:"position!list.do",
				type:'post',
				success:function(data){
					
					var rows;
					if(data.rows){
						rows = data.rows;
					}else{
						rows = data;
					}
					
					$.each(rows,function(i,r){
						var option = $("<option>",{
							value:r.name,
							text:r.name
						});
						if(r.name==defaultValue){
							option.prop("selected",true);
						}
						sel.append(option);
					});
					
					
				},
				dataType:'json'
			});
		},
		
		fillPaymentSelect:function(opt){
			
			var sel = opt.sel;
			
			$.ajax({
				url:webRoot+"/payment!list.do",
				type:'post',
				success:function(data){
					
					$.each(data,function(i,r){
						var option = $("<option>",{
							value:r.payment,
							text:r.payment
						});
						sel.append(option);
					});
					
				},
				dataType:'json'
			});
		},
		fillCurrentSelect:function(opt){
			var sel = opt.sel;
			$.ajax({
				url:webRoot+"/current!list.do",
				type:'post',
				success:function(data){
					
					$.each(data,function(i,r){
						var option = $("<option>",{
							value:r.currname,
							text:r.currname
						});
						sel.append(option);
					});
					
				},
				dataType:'json'
			});
		},
		fillBrandSelect:function(opt){
			var sel = opt.sel;
			$.ajax({
				url:webRoot+"/brand!list.do",
				type:'post',
				success:function(data){
					
					$.each(data,function(i,r){
						var option = $("<option>",{
							value:r.cpro,
							text:r.cpro
						});
						sel.append(option);
					});
					
				},
				dataType:'json'
			});
		},
		fillUserSelect:function(opt){
			
			var sel = opt.sel;
			var defaultValue = opt.defaultValue;
			
			var selLabel = "name";
			var selValue = "name";
			
			if(opt.label){
				selLabel = opt.label;
			}
			
			if(opt.value){
				selValue = opt.value;
				
			}
			
			var url= "user!list.do";
			
			$.ajax({
				url:url,
				type:'post',
				success:function(data){
					
					var rows;
					if(data.rows){
						rows = data.rows;
					}else{
						rows = data;
					}
					$.each(rows,function(i,item){
						var op = $("<option value='"+item[selValue]+"'>"+item[selLabel]+"</option>");
						if(defaultValue==item[selValue]){
							op.prop("selected",true);
						}
						sel.append(op);
					});
				},
				dataType:'json'
			});
		},
		fillUnitSelect:function(opt){
			var sel = opt.sel;
			var url= webRoot+"/unit!list.do";
			
			$.ajax({
				url:url,
				type:'post',
				success:function(data){
					
					$.each(data,function(i,item){
						var op = $("<option value='"+item.punit_name+"'>"+item.punit_name+"</option>");
						sel.append(op);
					});
				},
				dataType:'json'
			});
		}
	};
	
});

(function(globe,name,fun){
	globe[name] = fun();
})(window,"OACommonUse",function(){
	
	return {
		
		openOrderTemplateChoose : function(title,jumpUrl){
			BootstrapUtils.createDialog({
				id:'chooseOrderTemplateModal',
				title:title,
				template:webRoot+'/template/chooseOrderTemplate.mvc',
				onFinish:function(){
					var dialog = this;
					var vm = new Vue({
						data:{
							rows:[],
							page:1,
							params:{}
						},
						created:function(){
							this.fetchData();
						},
						methods:{
							fetchData:function(){
								var vm = this;
								$.ajax({
									url:webRoot+"/template!list.do",
									type:'post',
									data:vm.params,
									success:function(data){
										vm.rows = data;
									}
								});
							},
							getUrl:function(id){
								return jumpUrl+"?id="+id;
							}
						}
					});
					
					vm.$mount(this.find(".modal-body").get(0));
				}
			});
			$("#chooseOrderTemplateModal").modal('show');
		},
		
		// 公共的客户选择
		openClientChooseDialog : function(callbackFun){
			BootstrapUtils.createDialog({
				id:'chooseClientModal',
				template:webRoot+'/template/chooseClient.mvc',
				onFinish:function(){
					var dialog = this;
					var vm = new Vue({
						data:{
							rows:[],
							page:1,
							params:{}
						},
						created:function(){
							this.fetchData();
						},
						methods:{
							fetchData:function(){
								var vm = this;
								$.ajax({
									url:webRoot+"/client/client!list.do",
									type:'post',
									data:vm.params,
									success:function(data){
										vm.rows = data.rows;
									}
								});
							},
							choose:function(row){
								callbackFun.call(dialog,row);
								$("#chooseClientModal").modal('hide');
							}
						}
					});
					
					vm.$mount(this.find(".modal-body").get(0));
				}
			});
			$("#chooseClientModal").modal('show');
			
			
		},
		
		// 公共的供应商选择
		openSupplierChooseDialog:function(callbackFun){
			BootstrapUtils.createDialog({
				id:'supplierChooseModal',
				template:webRoot+'/template/chooseSupplier.mvc',
				onFinish:function(){
					var dialog = this;
					var vm = new Vue({
						data:{
							rows:[],
							page:1,
							params:{}
						},
						created:function(){
							this.fetchData();
						},
						methods:{
							fetchData:function(){
								var vm = this;
								$.ajax({
									url:webRoot+"/purchasing/supplier!list.do",
									type:'post',
									data:vm.params,
									success:function(data){
										vm.rows = data.rows;
									}
								});
							},
							choose:function(row){
								callbackFun.call(dialog,row);
								$("#supplierChooseModal").modal('hide');
							}
						}
					});
					
					vm.$mount(this.find(".modal-body").get(0));
				}
			});
			$("#supplierChooseModal").modal('show');
		},
		
		// 公共的供应商联系人选择
		openSupplierContactChooseDialog:function(supplierNumber,callbackFun){
			
			BootstrapUtils.createDialog({
				id:'supplierContactChooseModal',
				template:webRoot+'/template/chooseSupplierContact.mvc',
				onFinish:function(){
					var dialog = this;
					var vm = new Vue({
						data:{
							rows:[],
							page:1,
							params:{
								number:supplierNumber
							}
						},
						created:function(){
							this.fetchData();
						},
						methods:{
							fetchData:function(){
								var vm = this;
								$.ajax({
									url:webRoot+"/purchasing/supplier!getContactList.do",
									type:'post',
									data:vm.params,
									success:function(data){
										vm.rows = data;
									}
								});
							},
							choose:function(row){
								callbackFun.call(dialog,row);
								$("#supplierContactChooseModal").modal('hide');
							}
						}
					});
					
					vm.$mount(this.find(".modal-body").get(0));
				}
			});
			$("#supplierContactChooseModal").modal('show');
		},
		
		// 公共的供应商联系人选择
		openBankChooseDialog:function(callbackFun){
			
			BootstrapUtils.createDialog({
				id:'bankChooseModal',
				template:webRoot+'/finance/template/bankChoose.mvc',
				onFinish:function(){
					var dialog = this;
					var vm = new Vue({
						data:{
							rows:[],
							page:1,
							params:{
					
							}
						},
						created:function(){
							this.fetchData();
						},
						methods:{
							fetchData:function(){
								var vm = this;
								$.ajax({
									url:webRoot+"/finance/finance!getBankList.do",
									type:'post',
									data:vm.params,
									success:function(data){
										vm.rows = data;
									}
								});
							},
							choose:function(row){
								callbackFun.call(dialog,row);
								$("#bankChooseModal").modal('hide');
							}
						}
					});
					
					vm.$mount(this.find(".modal-body").get(0));
				}
			});
			$("#bankChooseModal").modal('show');
		}
	};
	
});






