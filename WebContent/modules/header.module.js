(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('header',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/header.html';
exports.init = function(){

	
$.ajax({
	url:'design/header!getHeader.do',
	success:function(data){
		console.log(data);
	}
});
	

};
return module.exports;});