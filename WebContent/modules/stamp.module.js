(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('stamp',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
return module.exports;});