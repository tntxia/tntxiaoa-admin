
function controller(ctr,clazz){
	$(function(){
		
		var events = ["click","change","blur"];
		
		var $scope = {};
		new clazz($scope);
		
		$.each(events,function(i,d){
			$("[tntxia-controller="+ctr+"]").on(d,"[tntxia-"+d+"]",function(){
				var action = $(this).attr("tntxia-"+d);
				eval("$scope."+action);
			});
		});
		
		
	});
}

