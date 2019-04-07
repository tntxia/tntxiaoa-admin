

if(typeof require != "undefined"){
  // 使用之前把jquery导入
  require(["jquery"],function(){
  });
}

$.parser = {
	parseOptions:function(target){
		var opt = {};
		opt.title = $(target).attr("title");
		return opt;
	}
};

var $tntxiaui = {
   	popupid:1,
   	fillTable:function(opt,tbody,rows){
   		
   		function isArray(it) {
   			return it && it.toString && it.toString()=== '[object Array]';
   	    }
   		
   		function renderByType(td,type,index){
   			if(type=="index"){
   				return index;
   			}
   			return "";
   		}
   		
   		if(!rows){
			return;
		}
   		
		$.each(rows,function(i){
			var tr = $("<tr>");
			tr.hover(function(){
				$(this).find("td").addClass("tntxiaui-datagrid-tr-hover");
			},function(){
				$(this).find("td").removeClass("tntxiaui-datagrid-tr-hover");	
			});
			tr.data("data",this);
			tr.click(opt.trClick);
				
			if(i%2==0)
				tr.addClass("tntxiaui-datagrid-tr-odd");
				
			var d = this;
			$.each(opt.cols,function(index){
				var col = this;
				var td = $("<td>");
				var content;
				if(col.renderer){
					content = col.renderer(d,i);
				}else if(col.edit){
					content = $("<input>",{
						value:d[col.field],
						name:col.field
					});
				}else if(col.type){
					content = renderByType(col.type,index);
				}else{
					content = d[col.field];
				}
				
				if(isArray(content)){
					$.each(content,function(i){
						td.append(this);
					});
				}else{
					td.append(content);
				}
				
				td.appendTo(tr);
			});
			tr.appendTo(tbody);
		});
   	},
   	createTable:function(opt,rows){
   		
   		var table = $("<table>",{width:'100%'});
   		var header = $("<tr>");
		$.each(opt.cols,function(i){
			var h = $("<th>",{text:this.label});
			h.appendTo(header);
		});
		header.appendTo(table);
		
		var tbody = $("<tbody>",{"class":"tntxia-ui-datagrid-databody"});
		
		$tntxiaui.fillTable(opt,tbody,rows);
		
		tbody.appendTo(table);
		
		if(opt.url){
			$.ajax({
				url:opt.url,
				data:opt.data,
				success:function(rows){
					$tntxiaui.fillTable(opt,tbody,rows);
				}
			});
		}
		
		return table;
   	},
   	createTableForm:function(opt){
   		
   		var form = $("<form>",{id:opt.id});
   		form.attr("method",opt.type);
   		form.attr("action",opt.url);
   		
   		if(opt.hiddens){
   			$.each(opt.hiddens,function(i,hidden){
   				var hiddenInput = $("<input>",{
   					type:'hidden',
   					name:hidden.name,
   					value:hidden.value
   				});
   				form.append(hiddenInput);
   			});
   		}
   		
   		var table = $("<table>",{width:'100%'});
   		table.appendTo(form);
   		
   		var colspan = opt.colspan;
   		if(!colspan){
   			colspan = 1;
   		}
   		var inputs = opt.inputs;
   		var tr = $("<tr>");
   		var colIndex = 0;
   		$.each(inputs,function(i,inputOpt){
   			var th = $("<th>",{text:inputOpt.label});
   			tr.append(th);
   			var td = $("<td>");
   			if(inputOpt.colspan){
   				td.attr("colspan",inputOpt.colspan*2-1);
   			}else{
   				inputOpt.colspan = 1;
   			}
   			var inp;
   			var type = inputOpt.type;
   			
   			if(inputOpt.renderer){
   				td.append(inputOpt.renderer());
   			}else{
   				if(type=="select"){
   	   				inp = $("<select>",{name:inputOpt.name});
   	   				
   	   				if(inputOpt.defaultOption){
   	   					inp.append($("<option>",inputOpt.defaultOption));
   	   				}
   	   				
   	   				var dataset = inputOpt.dataset;
   	   				if($.isArray(dataset)){
   	   					$.each(dataset,function(i,data){
   	   						
   	   						var option = $("<option>");
   	   						if(typeof data == "string"){
   	   							option.text(data);
   	   						}else{
   	   							option.text(data.text);
   	   							option.val(data.value);
   	   						}
   	   						inp.append(option);
   	   					});
   	   				} else{
   	   					var ajaxOption = $.extend({
   	   						success:function(data){
   	   							
   	   							$.each(data,function(i){
   	   								var d = data[i];
   	   								var option = $("<option>",{value:d[dataset.field],text:d[dataset.label]});
   	   	   							inp.append(option);
   	   							});
   	   							
   	   						}
   	   					},dataset);
   	   					$.ajax(ajaxOption);
   	   				}
   	   				
   	   				
   	   			}else if(type=="textarea"){
	   				inp = $("<textarea>",{name:inputOpt.name,width:'90%',height:'80%'});
	   			}else{
   	   				inp = $("<input>",{name:inputOpt.name});
   	   			}
   			}
   			td.append(inp);
   			tr.append(td);
   			if(colIndex+inputOpt.colspan>=opt.colspan){
   				table.append(tr);
   				tr = $("<tr>");
   				colIndex = 0;
   			}else{
   				colIndex = (colIndex + inputOpt.colspan)%opt.colspan;
   			}
   			
   		});
   		table.append(tr);
   		return form;
   	},
   	
   	loadSelect : function(sel,opt){
   		
   	},
   	
   	startLoading : function(msg){
   		var length = $(".tntxia-ui-loading").length;
   		if(length>0){
   			$(".tntxia-ui-loading").show();
   		}else{
   			var div = $("<div>",{"class":"tntxia-ui-loading",text:msg});
   			var span = $("<span>");
   			function showDots(){
   				if(span.text().length>5){
   					span.text(".");
   				}else{
   					span.text(span.text()+".");
   				}
   				setTimeout(showDots,1000);
   			}
   			showDots();
   			div.append(span);
   			$("body").append(div);
   			div.css("position","absolute");
   		}
   	}
		
};

$.fn.panel = function(opt){
	
	function wrapPanel(target){
		var panel = $("<div>",{'class':'panel',width:opt.width,height:opt.height});
		target.wrap(panel);
		return panel;
	}
	
	function addHeader(target,opt){
		
		var title = "";
		if(opt){
			title = opt.title;
		}
		
		var header = $("<div>",{
			'class':'panel-header',
			height:16
		});
		
		var titleDiv = $("<div>",{text:title});
		titleDiv.css("float","left");
		titleDiv.appendTo(header);
		
		var tool = $("<div class=\"panel-tool\"></div>").appendTo(header);
		tool.bind("click", function (e) {
            e.stopPropagation();
        });
		
		if(opt.tools && opt.tools.length){
			$.each(opt.tools,function(i,d){
				var t = $("<a href=\"javascript:void(0)\"></a>").addClass(d.iconCls).appendTo(tool);
				if (d.handler) {
                    t.bind("click", d.handler);
                }
			});
		}
		
		target.before(header);
	}
	
	var panel = wrapPanel(this);
	addHeader(this,opt);
	return panel;
	
};

$.fn.accordion = function(){
	
	function parseOptions(target){
		var opt = {};
		opt.title = $(target).attr("title");
		return opt;
	}
	
	function renderAccordion(target) {
        var cc = $(target);
        cc.addClass("accordion");
        
        cc.children("div").each(function () {
            var options = $.extend({}, parseOptions(this));
            var pp = $(this);
            pp.panel(options);
        });
        
    };
	
	return this.each(function(){
		renderAccordion(this);//渲染折叠面板
	});
};

$.fn.tabs = function(){
	
	//创建tab
    function createTab(target, pp, options) {
        
        options = options || {};
        pp.panel($.extend({}, options));
        
        var tabs2 = $(target).children("div.tabs-header").find("ul.tabs");
        var tab = $("<li></li>").appendTo(tabs2);
        tab.append("<a href=\"javascript:void(0)\" class=\"tabs-inner\">"
            + "<span class=\"tabs-title\"></span>"
            + "<span class=\"tabs-icon\"></span>"+options.title
            + "</a>");
        
    };
	
	//初始化tab
    function wrapTabs(target) {
    	var tabs = $(target);
        tabs.addClass("tabs-container");
        tabs.wrapInner("<div class=\"tabs-panels\"/>");
        $("<div class=\"tabs-header\">"
                + "<div class=\"tabs-scroller-left\"></div>"
                + "<div class=\"tabs-scroller-right\"></div>"
                + "<div class=\"tabs-wrap\">"
                + "<ul class=\"tabs\"></ul>"
                + "</div>" + "</div>").prependTo(target);
        tabs.children("div.tabs-panels").children("div").each(function (i) {
            var options = $.extend({}, $.parser.parseOptions(this));
            var pp = $(this);
            createTab(target, pp, options);
        });
    }
	
	 return this.each(function () {
         wrapTabs(this);
         
     });
};

$.fn.datepick = function(opt){
	var picker = this;
	
	var showNowDate = opt?opt.showNowDate:false;
	if(showNowDate){
		var now = new Date();
		$(picker).val(now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate());
	}
	
	var img = $("<span></span>",{"class":"tntxiaui-picker-image"});
	if(picker[0].tagName=="INPUT"){
		img.data("target",picker);
		img.insertAfter(picker);
	}else{
		var input = $("<input>",{name:opt.name});
		img.data("target",input);
		this.append(input);
		this.append(img);
	}
	
	img.click(function(){
		function buildDate(){
			var div = $("#tntxia-ui-popup-dialog-date-line-div");
			div.empty();
			
			var year = parseInt($("#tntxia-ui-popup-dialog-year-input").val(),10);
			var month = parseInt($("#tntxia-ui-popup-dialog-month-sel").val(),10);    //本月
			var day = -1;            //本日
			var today = new Date();
			if(year == today.getFullYear() && month == today.getMonth()+1){
				day = today.getDate();           
			}
			
			var target = $("#tntxia-ui-popup-dialog").data("target");
			var fillDate = _getFillDate(target);
			
			var inputFillDate = -1;
			if(fillDate && year == fillDate.getFullYear() && month == fillDate.getMonth()+1){
				inputFillDate = fillDate.getDate();
			}
			
	        //本月第一天是星期几（距星期日离开的天数）
	        var startDay = new Date(year, month - 1, 1).getDay();
	        
	        //本月有多少天(即最后一天的getDate()，但是最后一天不知道，我们可以用“上个月的0来表示本月的最后一天”)
	        var nDays = new Date(year, month, 0).getDate();
			
			var dateLine = $("<tr>",{"class":"tntxia-ui-popup-dialog-dateline"});
	        
	        //开始画日历
	        var numRow = 0;  //记录行的个数，到达7的时候创建div
	        
	        for (var i = 0; i < startDay; i++) {

	            var aDate = $("<td>",{"class":"tntxia-ui-popup-dialog-emptyitem"});
	            aDate.appendTo(dateLine);
	            numRow++;

	        }
	        
	        for (var j = 1; j <= nDays; j++) {
	        	
	        	var aDate = $("<td>",{text:j});
	            //如果是今天则显示红色
	            if (j == day) {
	            	aDate.addClass("tntxia-ui-popup-dialog-todayitem");
	            } else if(j == inputFillDate){
	            	aDate.addClass("tntxia-ui-popup-dialog-filldateitem");
	            }else {
	            	aDate.addClass("tntxia-ui-popup-dialog-commonitem");
	            }
	            aDate.click(function(){
	            	var dialog = $("#tntxia-ui-popup-dialog");
	            	var target = dialog.data("target");
	            	var year = $("#tntxia-ui-popup-dialog-year-input").val();
	            	var month = $("#tntxia-ui-popup-dialog-month-sel").val();
	            	var selectDate = year+"-"+month+"-"+$(this).text();
	            	target.val(selectDate);
	            	$(this).addClass("tntxia-ui-popup-dialog-clickitem");
	            	var lastClick = dialog.data("lastClick");
	            	if(lastClick){
	            		lastClick.removeClass("tntxia-ui-popup-dialog-clickitem");
	            	}
	            	dialog.data("lastClick",$(this));
	            });
	            aDate.appendTo(dateLine);
	            numRow++;

	            if (numRow == 7) {  //如果已经到一行（一周）了，重新创建div
	                numRow = 0;
	                dateLine.appendTo(div);
	                dateLine = $("<tr>",{"class":"tntxia-ui-popup-dialog-dateline"});

	            }
	        }
	        
	        dateLine.appendTo(div);
		}
		
		function _getFillDate(target){
			var fillDate = $(target).val();
			if(!fillDate || $.trim(fillDate) == ""){
				return null;
			}
			var arr = fillDate.split("-");
			return new Date(parseInt(arr[0],10),parseInt(arr[1],10)-1,parseInt(arr[2],10));
		}
		
		var div = $("#tntxia-ui-popup-dialog");
		var target = $(this).data("target");
		var fillDate = _getFillDate(target);     // 文本框里面的日期
		if(!fillDate){
			fillDate = new Date();
		}
		
		var year = fillDate.getFullYear();      //本年
        var month = fillDate.getMonth() + 1;    //本月
		
		if(div.length==0){
			div = $("<div>",{
				id:"tntxia-ui-popup-dialog"
			});
			
			var operBar = $("<div>",{
				id:"tntxia-ui-popup-dialog-bar"
			});
			
			operBar.appendTo(div);
			
			var button = $("<button>",{
				text:'关闭',
				click:function(){
					div.hide();
				}
			});
			button.appendTo(operBar);
			
			var table = $("<table>");
			table.appendTo(div);
			
			var tr = $("<tr>");
			tr.appendTo(table);
			var td = $("<td>",{colspan:5,align:"center"});
			td.appendTo(tr);
			
			var addButton = $("<button>",{
				text:'+',
				click:function(){
					var yearInput = $(this).next();
					var year = yearInput.val();
					yearInput.val(parseInt(year,10)+1);
					buildDate();
				}
			});
			var yearInput = $("<input>",{
				type:'text',
				id:"tntxia-ui-popup-dialog-year-input",
				width:50,
				readonly:"readonly"
			});
			var subButton = $("<button>",{
				text:'-',
				click:function(){
					var yearInput = $(this).prev();
					var year = yearInput.val();
					yearInput.val(parseInt(year,10)-1);
					buildDate();
				}
			});
			
			addButton.appendTo(td);
			yearInput.appendTo(td);
			subButton.appendTo(td);
			
			var monthSel = $("<select>",{id:"tntxia-ui-popup-dialog-month-sel",change:function(){
				buildDate();
			}});
			
			for(var i=1;i<=12;i++){
				var opt = $("<option>",{text:i,value:i});
				monthSel.append(opt);
			}
			monthSel.appendTo(td);
			
			tr = $("<tr>");
			
			var dayArr = ["日","一","二","三","四","五","六"];
			
			$.each(dayArr,function(i,day){
				var td = $("<td>",{text:day,"class":"tntxia-ui-popup-dialog-item"});
				td.appendTo(tr);
			});
			
			tr.appendTo(table);
			
	        var dateLineDiv = $("<tbody>",{id:"tntxia-ui-popup-dialog-date-line-div"});
	        
	        dateLineDiv.appendTo(table);
	        
			div.appendTo($("body"));
			
			var windowHeight = $(window).height(); 
			var windowWidth = $(window).width();
			div.css("top",windowHeight/2-div.height()/2);
			div.css("left",windowWidth/2-100);
			
		}
		
		div.show();
		$("#tntxia-ui-popup-dialog-year-input").val(year);
		$("#tntxia-ui-popup-dialog-month-sel option:nth-child("+month+")").attr("selected","selected");
		$("#tntxia-ui-popup-dialog").data("target",target);
		
		buildDate();
		
	});
	
	console.log(this);
};

$.fn.form = function(opt){

	var form = this;
	form.addClass("tntxiaui-form");
	var gap = opt.gap;
	
	$.each(opt.inputs,function(i){
		var inp = this;
		var label = $("<span>",{text:this["label"]});
		label.appendTo(form);
		var size = 15;
		if(inp.size){
			size = inp.size;
		}
		var input = null;
		var type = inp.type;
		if(type=="select"){
			input = $("<select>",{name:inp["name"]});
			if(inp.opt){
				input.dropdown(inp.opt);
			}
			
		}else if(type=="date"){
			input = $("<span>");
			input.datepick({name:inp.name});
		} else{
			var opt = {size:size,name:inp.name,value:inp.value};
			if(type){
				opt.type = type;
			}
			if(inp.disabled){
				opt.disabled = "disabled";
			}
			input = $("<input>",opt);
			
		}
		
		input.appendTo(form);
		
		if(gap){
			form.append(gap);
		}
		
	});
	
	if(opt.operations){
		$.each(opt.operations,function(i,data){
			var button = $("<button>",data);
			form.append(button);
		});
	}
	return form;
	
};

// 把数据在表格中显示
$.fn.loadTableData = function(opt,rows){
	
	var tbody = this;
	
	function isArray(it) {
		return it && it.toString && it.toString()=== '[object Array]';
	}
		
	function renderByType(type,index){
		if(type=="index"){
			return index+1;
		}
		return "";
	}
		
	if(!rows){
		return;
	}
		
	$.each(rows,function(i){
		var tr = $("<tr>");
		tr.hover(function(){
			$(this).find("td").addClass("tntxiaui-datagrid-tr-hover");
		},function(){
			$(this).find("td").removeClass("tntxiaui-datagrid-tr-hover");	
		});
		tr.data("data",this);
		tr.click(opt.trClick);
			
		if(i%2==0)
			tr.addClass("tntxiaui-datagrid-tr-odd");
			
		var d = this;
		$.each(opt.cols,function(index){
			
			var col = this;
			if(col.type=="hidden"){
				return;
			}
			var td = $("<td>");
			var content;
			if(col.renderer){
				content = col.renderer(d,i);
			}else if(col.edit){
				content = $("<input>",{
					value:d[col.field],
					name:col.field
				});
			}else if(col.type=="index"){
				content = renderByType(col.type,index,d);
			}else{
				content = d[col.field];
			}
			
			if(isArray(content)){
				$.each(content,function(i){
					td.append(this);
				});
			}else{
				td.append(content);
			}
			
			td.appendTo(tr);
		});
		tr.appendTo(tbody);
	});
};

$.fn.datagrid = function(opt){
	
	// 如果没有参数，无效
	if(arguments.length==0){
		return this;
	}
	
	var datagrid = this;
	
	function loadData(tbody,opt){
		
		if(!opt.data){
			opt.data ={};
		}
		opt.data.pageSize = opt.pageSize;
		$.ajax({
			url:opt.url,
			data:opt.data,
			type:'post',
			success:function(data){
				var rows;
				if($.isArray(data)){
					rows = data;
				}else{
					rows = data.rows;
				}
				var pageSize = data.pageSize;
				var totalAmount = data.totalAmount;
				var currentPage = data.currentPage;
				var totalPage = Math.ceil(totalAmount/pageSize);
			
				tbody.loadTableData(opt,rows);
				
				$("#totalPage").html(totalPage);
				$("#currentPage").html(currentPage);
				$("#totalPage").html(totalPage);
				$("#totalAmount").html(totalAmount);
				
				if(opt.onFinish){
					opt.onFinish();
				}
				
			}
		});
	}
	
	if(typeof arguments[0] ==="object"){
		this.data("opt",opt);
		this.addClass('tntxiaui-datagrid');
		
		if(opt.height){
			this.css("height",opt.height);
		}
		
		var headerDiv = $("<div>",{'class':'panel-header'});
		var titleDiv = $("<div>",{'class':'panel-title',text:opt.title});
		headerDiv.append(titleDiv);
		this.append(headerDiv);
		
		if(opt.operations){
			
			var divOperations = $("<div>");
			$.each(opt.operations,function(i,o){
				if(i!=0){
					divOperations.append(" | ");
				}
				if(!o.text){
					o.text = o.label;
				}
				var link = $("<a>",$.extend({"href":"#"},o));
				link.appendTo(divOperations);
				
			});
			divOperations.appendTo(this);
		}
		
		var divContent = $("<div>");
		
		if(opt.form){
			var form = $("<div>");
			form.form(opt.form);
			divContent.append(form);
		}
		
		var table = $("<table>",{width:'100%'});
		table.appendTo(divContent);
		
		divContent.appendTo(this);
		
		var navDiv = $("<div>",{"class":"nav"});
		
		var span = $("<span>");
		span.append("总共<span id='totalPage'></span>页，");
		span.append("当前第<span id='currentPage'>1</span>页 总共<span id='totalAmount'></span>条记录");
		
		var prevLink = $("<a>",{
			text:"上一页",
			click:function(){
				var currentPage = parseInt($("#currentPage").text(),10);
				if(currentPage<=1){
					return;
				}
				var opt = datagrid.data("opt");
				opt.data.page = currentPage-1;
				datagrid.datagrid("reload",opt.data);
			}
		});
		
		prevLink.appendTo(span);
		
		var nextLink = $("<a>",{
			text:"下一页",
			click:function(){
				var currentPage = parseInt($("#currentPage").text(),10);
				var opt = datagrid.data("opt");
				opt.data.page = currentPage+1;
				datagrid.datagrid("reload",opt.data);
			}
		});
		
		nextLink.appendTo(span);
		
		span.appendTo(navDiv);
		
		navDiv.appendTo(this);
		
		var header = $("<tr>");
		
		if(opt.cols){
			$.each(opt.cols,function(i){
				var col = this;
				if(col.type!="hidden"){
					var h = $("<th>",{text:col.label});
					h.appendTo(header);
				}
				
			});
		}
		
		
		header.appendTo(table);
		
		var tbody = $("<tbody>",{"class":"tntxia-ui-datagrid-databody"});
		tbody.appendTo(table);
		
		loadData(tbody,opt);
	}else{
		var action = arguments[0];
		if(action=="reload"){
			var reloadData = arguments[1];
			if(!reloadData){
				reloadData = {};
			}
			opt = this.data("opt");
			opt.data = $.extend(opt.data,reloadData);
		}
		var tbody = this.find(".tntxia-ui-datagrid-databody");
		tbody.empty();
		loadData(tbody,opt);
	}
	
};

$.dialog = function(opt){
	
	opt.tools =[{
		iconCls:'panel-tool-close',
		handler:function(){
			panel.remove();
		}
	}];
	
	var contentDiv = $("<div>",{width:'100%',height:opt.height-50});
	contentDiv.css("overflow","scroll");
	contentDiv.appendTo($("body"));
	contentDiv.panel(opt);
	
	var panel = contentDiv.parent();
	
	
	
	var windowWidth = $(window).width();
	panel.css("top","20%");
	panel.css("left",windowWidth/2-panel.width()/2);
	panel.css("position","absolute");
	panel.css("background","white");
	
	var operBar = panel.find(".panel-header");
	
	operBar.mousedown(function (e) { 
		
	    iDiffX = e.pageX - $(this).offset().left; 
	    iDiffY = e.pageY - $(this).offset().top; 
	    $(document).mousemove(function (e) { 
	    	panel.css({ "left": (e.pageX - iDiffX), "top": (e.pageY - iDiffY) }); 
	    });
	});
	   
	operBar.mouseup(function () { 
	    $(document).unbind("mousemove"); 
	});
	
	if(opt.url){
		contentDiv.loadPage(opt);
	}else{
		// 如果type属性有，按type属性来取值
		if(opt.type){
			if(opt.type=="grid"){
				var table = $tntxiaui.createTable(opt.gridOpt);
				opt.content = table;
			}
		}
		contentDiv.append(opt.content);
	}
	
	var buttonsDiv = $("<div>",{width:'100%'});
	buttonsDiv.css("text-align","center");
	if(opt.buttons){
		$.each(opt.buttons,function(i,b){
			var button = $("<button>",b);
			buttonsDiv.append(button);
		});
	}
	
	panel.append(buttonsDiv);
	
	return panel;
	
};

$.fn.getParamMap = function(escapeFlag){
	var res = {};
	this.find(":input").each(function(i,inp){
		
		if(escapeFlag){
			res[inp.name] = escape(inp.value);
		}else{
			res[inp.name] = inp.value;
		}
	});
	return res;
};

$.fn.loadPage = function(opt){
	var content = this;
	var ajaxOption = $.extend(opt,{
		cache:false,
		success:function(data){
			console.log(opt.templateData);
			var compile = _.template(data);
			content.append(compile(opt.templateData));
			if(opt.onFinish){
				opt.onFinish();
			}
		}
	});
	$.ajax(ajaxOption);
};

$.fn.dropdown = function(opt){
	var sel = this;
	sel.empty();
	if(opt.defaultOption){
		sel.append($("<option>",opt.defaultOption));
	}
	var dataset = opt.dataset;
	if($.isArray(dataset)){
		$.each(dataset,function(i,data){
			if(!data.value){
				data.value = data.text;
			}
			var option = $("<option>",data);
			sel.append(option);
		});
	}else{
		var ajaxOption = $.extend({
			success:function(data){
				var arr = null;
				if(data.rows){
					arr = data.rows;
				}else{
					arr = data;
				}
				$.each(arr,function(i){
					var d = this;
					var option = $("<option>",{value:d[dataset.field],text:d[dataset.label]});
   					sel.append(option);
				});
			}
		},dataset);
		$.ajax(ajaxOption);
	}
};



$.fn.leftbar = function(opt){
	var content = this;
	var childLabel = content.attr("childLabel");
	if(!childLabel){
		childLabel = "children";
	}
	$.each(opt,function(i,data){
		content.append($("<div>",{
			text:data.text,
			'class':'tntxia-ui-leftbar-mainitem'
		}));
		var ul = $("<ul>",{'class':'tntxia-ui-leftbar-list'});
		content.append(ul);
		
		$.each(data[childLabel],function(i,data){
			var li = $("<li>");
			ul.append(li);
			var a = $("<a>",{text:data.text,href:data.url});
			li.append(a);
		});
	});
	
};

// 生成Crud界面
$.fn.crud = function(opt){
	
	var target = this;
	var cols = opt.cols;
	var idField = "id";
	if(opt.idField){
		idField = opt.idField;
	}
	
	function setDefaultTemplate(content,datagrid,rowData){
		
		var isUpdate = rowData && rowData.id;
		
		var type = null;
		if(isUpdate){
			type = "修改";
		}else{
			type = "增加";
		}
		
		var formOpt = {};
		formOpt.gap = "<br>";
		formOpt.inputs = [];
		formOpt.operations =[{
			text:type,
			click:function(){
				var params = $(this).parent().getParamMap();
				var url = null;
				if(isUpdate){
					url = opt.update.url;
				}else{
					url = opt.create.url;
				}
				
				if(isUpdate){
					params = $.extend(opt.update.data,params);
				}else{
					params = $.extend(opt.create.data,params);
				}
				
				$.ajax({
					url:url,
					type:'post',
					data:params,
					success:function(data){
						datagrid.datagrid('reload');
						dialog.remove();
					}
				});
			}
		}];
		
		$.each(cols,function(i,col){
			
			var isIdField = col.field===idField;
			
			if(isIdField){
				if(isUpdate){
					formOpt.inputs.push({
						label:col.label,
						name:col.field,
						type:"hidden",
						value:rowData[col.field]
					});
				}
			}else{
				if(col.field){
					formOpt.inputs.push({
						label:col.label,
						name:col.field,
						value:rowData[col.field]
					});
				}
				
			}
			
		});
		content.form(formOpt);
	}
	
	function setTemplate(content,tempate,rowData,onFinish){
		content.loadPage({
			url:tempate,
			templateData:rowData,
			onFinish:onFinish
		});
		
	}
	
	// 弹出增加或更新的窗口
	function toAddOrUpdate(rowData){
		
		var content = $("<div>");
		var dialog = $.dialog({
			width:500,
			height:500,
			content:content
		});
		
		var datagrid = target.data("datagrid");
		
		var isUpdate = rowData && rowData.id;
		
		if(isUpdate && opt.update  && opt.update.template ){
			setTemplate(content,opt.update.template,rowData,opt.update.onFinish);
		}else if(!isUpdate && opt.create && opt.create.template){
			setTemplate(content,opt.create.template,rowData,opt.create.onFinish);
		}else{
			setDefaultTemplate(content,datagrid,rowData);
		}

		
	}
	
	var inputs = [];
	
	if(opt.search && opt.search.cols){
		$.each(opt.search.cols,function(i,col){
			var isIdField = col.field===idField;
			if(!isIdField){
				col.name = col.field;
				inputs.push(col);
			}
		});
	}else{
		if(cols){
			$.each(cols,function(i,col){
				var isIdField = col.field===idField;
				if(!isIdField){
					col.name = col.field;
					inputs.push(col);
				}
			});
		}
	}
	
	var operations = [];
	
	var datagrid = $("<div>");
	target.data("datagrid",datagrid);
	
	operations.push({
		text:'查询',
		click:function(){
			var params = $(this).parent().getParamMap();
			datagrid.datagrid('reload',params);
		}
	});
	
	
	if(opt.create){
		operations.push({
			text:'增加',
			click:function(){
				toAddOrUpdate('增加');
			}
		});
	}
	
	var optList = opt.list;
	if(!optList){
		optList = {};
	}
	
	optList.form = {};
	optList.form.inputs = inputs;
	optList.form.operations = operations;
	
	var listCols = $.extend([],opt.cols);
	
	listCols.push({
		label:'操作',
		renderer:function(){
			var buttonModify = $("<button>",{
				text:'修改',
				click:function(){
					var rowData = $(this).parent().parent().data("data");
					toAddOrUpdate(rowData);
				}
			});
			var buttonDel = $("<button>",{text:'删除',click:function(){
				var rowData = $(this).parent().parent().data("data");
				$.ajax({
					url:opt.del.url,
					data:$.extend(opt.del.data,rowData),
					success:function(data){
						if(data.success){
							alert("操作成功");
							datagrid.datagrid('reload');
						}else{
							alert("操作失败");
						}
					}
				});
			}});
			return [buttonModify,buttonDel];
		}
	});
	
	optList.cols = listCols;
	optList.title = opt.title;
	datagrid.datagrid(optList);
	this.append(datagrid);
};

$.fn.accordion = function(){
	var children = this.children();
	var size = children.length;
	var height = this.height();
	children.each(function(){
		var item = $(this);
		var title = item.attr("title");
		$(this).panel({
			title:title,
			height:height/size
		});
	});
};

$.mainLayout = function(opt){
	
	var doc = $(document);
	var documentWidth = doc.width();
	var documentHeight = doc.height();
	
	var body = $("body");
	
	var headerOpt = opt.header;
	
	var headerHeight = headerOpt.height;
	
	var headDiv = $("<div>",{'class':'tntxia-ui-mainlayout-header',height:headerHeight});
	body.append(headDiv);
	
	if(headerOpt.ico){
		var img = $("<img>",{src:headerOpt.ico});
		headDiv.append(img);
	}
	
	if(headerOpt.content){
		var content = headerOpt.content;
		if(content){
			headDiv.append(content);
		}
	}
	
	if(headerOpt.url){
		headDiv.loadPage({
			url:headerOpt.url
		});
	}
	
	
	var leftWidth = opt.left.width;
	var heightRemain = documentHeight-headerHeight;
	
	var leftDiv = $("<div>",{
		'class':'tntxia-ui-mainlayout-leftbar',
		height:heightRemain,
		width : leftWidth
	});
	
	var menus = opt.left.menus;
	$.each(menus,function(i,m){
		var menu = $("<div>",{title:m.title});
		leftDiv.append(menu);
		var ol = $("<ol>");
		menu.append(ol);
		$.each(m.items,function(i,item){
			var li = $("<li>");
			var a = $("<a>",$.extend({target:'mainFrame'},item));
			li.append(a);
			ol.append(li);
		});
	});
	leftDiv.css("float","left");
	
	leftDiv.accordion();
	
	body.append(leftDiv);
	
	var contentDiv = $("<div>",{
		'class':'tntxia-ui-mainlayout-content',
		width:documentWidth-leftWidth-19,
		height:heightRemain
		
	});
	contentDiv.css("float","left");
	var iframe = $("<iframe>",{width:'100%',height:'100%',name:'mainFrame'});
	contentDiv.append(iframe);
	
	body.append(contentDiv);
	
};

$(function(){
	
	$(".tntxia-ui-table").each(function(){
		
		var options = eval("({"+$(this).attr("option-data")+"})");
		
		var startIndex = 1;
		
		if(options.startIndex){
			startIndex = options.startIndex;
		}
		
		$(this).find("tr").each(function(i,el){
			if(startIndex-1<=i){
				$(el).css("background-color",options.changeColors[i%2]);
			}
			
		});
	});
	
	$(".tntxia-ui-date-picker").each(function(i,picker){
		$(picker).datepick();
	});
	
});


