
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