(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('payway_add',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	let form = $("#form").buildform({
		actions:{
			sub(){
				let params = this.getParamMap();
				http.post({
					url:'payway!add.do',
					data:params
				}).then(res=>{
					if(res.success){
						router.goRoute("payway");
					}
				})
				
			}
		}
	});
};
return module.exports;});