$.fn.menus = function(menus){
	
	var div = this;
	
	var ul = $("<ul>",{
		'class':'tntxiaui-menu'
	});
	
	$.each(menus,function(i,d){
		var li = $("<li>",{
			mouseover:function(){
				$(this).find("ul").show();
			},
			mouseout:function(){
				$(this).find("ul").hide();
			}
		});
		var a = $("<a>",{
			href:'#',
			text:d.title
		});
		li.append(a);
		if(d.children)
			childrenAppend(li,d.children);
		
		ul.append(li);
	});
	
	div.append(ul);
	
	function childrenAppend(p,children){
		
		var ul = $("<ul>");
		p.append(ul);
		$.each(children,function(i,d){
			
			var li = $("<li>");
			
			var a = $("<a>",{
				text:d.title,
				href:d.url,
				target:d.target
			});
			
			li.append(a);
			ul.append(li);
		});
		
	}
	
};