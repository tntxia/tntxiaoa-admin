(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('banner',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/banner.html';
exports.init = function(){

$("#sub").click(function(){
	$("#logoUploadForm").ajaxSubmit({
        success: function (data) {
        	getLogoUrl();
        },
        error: function (error) { alert(error); },
        type: "post", /*设置表单以post方法提交*/
        dataType: "json" /*设置返回值类型为文本*/
    });
});
};
return module.exports;});