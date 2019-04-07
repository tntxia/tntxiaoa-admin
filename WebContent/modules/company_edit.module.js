(function(name,module){
if(!window.modules){
window.modules=Object.create(null);
};
window.modules[name]=module();
})('company_edit',function(){
var module=Object.create(null);
var exports = Object.create(null);
module.exports=exports;
exports.template = 'template/company/edit.html';
exports.init = function(){
	
	let id = router.getParams().id;

	
	var uploader = WebUploader.create({

        // 选完文件后，是否自动上传。
        auto: true,

        // 文件接收服务端。
        server: '/file_center/file!upload.do',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '.picker',

        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        resize: false
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function(file, percentage) {
        $("#showUploading").show();
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess', function(file,res) {
    	$("#showUploading").hide();
    	$("[name=logo]").val(res.uuid);
    	$("#logoShow").attr("src","/file_center/file!showPic.do?uuid="+res.uuid);
        
    });

    // 文件上传失败，显示上传出错。
    uploader.on('uploadError', function(file) {
    	
    });
    
    $.ajax({
    	url:'company!detail.do',
    	data:{
    		id:id
    	}
    }).done(res=>{
    	$("[name=name]").val(res.companyname);
    	$("[name=tel]").val(res.companytel);
    	$("#logoShow").attr("src","/file_center/file!showPic.do?uuid="+res.pic_id);
    	$("[name=logo]").val(res.pic_id);
    })
    
    $("#saveBtn").click(function(){
    	
    	let form = $("#form").buildform();
    	let params = form.getParamMap();
    	params.id = id;
    	$.ajax({
    		url:'company!update.do',
        	data:params,
        	type:'post'
    	}).done(function(res){
    		if(res.success){
    			alert("操作成功");
    			window.location.reload();
    		}else{
    			alert("操作失败");
    		}
    	})
    	console.log(form);
    	
    	
    });
    
    $("#backBtn").click(function(){
    	router.goRoute("company");
    });

};
return module.exports;});