(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('process_expense_add',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.init = function(){
	
	let form = $("#form").buildform({
		actions:{
			sub(){
				let params = this.getParamMap();
				http.post({
					url:'proccess/expense!add.do',
					data:params
				}).then(res=>{
					if(res.success){
						alert("增加成功");
						router.goRoute("process_expense");
					}else{
						alert("增加失败")
					}
				},e=>{
					alert("增加异常")
				})
			}
		}
		
	});
	
	
};
return module.exports;});