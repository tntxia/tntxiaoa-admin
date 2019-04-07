package com.tntxia.oa.admin.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.dom4j.Document;

import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.oa.admin.service.ConfigService;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.util.Dom4jUtil;

public class DesignHeaderAction extends BaseAction {
	
	private ConfigService service = new ConfigService();
	
	private Map<String,Object> updateOACenter(String appId,String logoFile) throws Exception{
		
		ResourceBundle rb = ResourceBundle.getBundle("oa_admin");
		HttpTransfer transfer = new HttpTransfer();
		transfer.setHost(rb.getString("oa_center_server"));
		transfer.setPort(rb.getString("oa_center_port"));
		transfer.setContextPath(rb.getString("oa_center_url"));
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("appId", appId);
		param.put("logoFile", logoFile);
		return transfer.getMap("design!setLogo", param);
        
	}

	/**
	 * 修改头部信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 使用Apache文件上传组件处理文件上传步骤：
		// 1、创建一个DiskFileItemFactory工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 2、创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 解决上传文件名的中文乱码
		upload.setHeaderEncoding("UTF-8");
		// 3、判断提交上来的数据是否是上传表单的数据
		if (!ServletFileUpload.isMultipartContent(request)) {
			// 按照传统方式获取数据
			return this.errorMsg("非文件上传");
		}

		List<FileItem> list = upload.parseRequest(request);
		
		String app = (String) request.getSession().getAttribute("app");
		String fileName = null;
		FileItem itemUpload = null;
		for (FileItem item : list) {
			// 如果fileitem中封装的是普通输入项的数据
			if (!item.isFormField()) {
				// 如果fileitem中封装的是上传文件
				// 得到上传的文件名称，
				fileName = item.getName();
				itemUpload = item;
			}
		}

		if (fileName == null || fileName.trim().equals("")) {
			return this.errorMsg("上传的图片不能为空！");
		}

		// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
		// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
		// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
		
		// 获取item中的上传文件的输入流
		InputStream in = itemUpload.getInputStream();
		
		String ext = FilenameUtils.getExtension(fileName);
		String newFileName = UUID.randomUUID().toString().replaceAll("-", "")+"."+ext;
		
		String uploadDir = ResourceBundle.getBundle("oa_admin").getString("upload_path");
		
		String uploadPath = uploadDir+ File.separator + newFileName;;

		// 创建一个文件输出流
		FileOutputStream out = new FileOutputStream(uploadPath);
		// 创建一个缓冲区
		byte buffer[] = new byte[1024];
		// 判断输入流中的数据是否已经读完的标识
		int len = 0;
		// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
		while ((len = in.read(buffer)) > 0) {
			// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" +
			// filename)当中
			out.write(buffer, 0, len);
		}
		
		// 关闭输入流
		in.close();
		// 关闭输出流
		out.close();
		// 删除处理文件上传时生成的临时文件
		itemUpload.delete();
		
		// 生成配置文件
		this.updateOACenter(app,newFileName);

		return this.success();
	}
	
	private String getConfigFilePath(String appId) throws Exception{
		
		Map<String,Object> config = service.getConfig(appId);
		String path = (String) config.get("path");
		String configFilePath = path+File.separator+"WEB-INF"
		+File.separator+"config"+File.separator+"design"+File.separator
		+"header.xml";
		return configFilePath;
		
	}
	
	public Map<String,Object> getHeader(WebRuntime runtime) throws Exception{
		
		String appId = runtime.getSessionStr("app");
		String configFilePath = getConfigFilePath(appId);
		
		Document doc = Dom4jUtil.getDoc(configFilePath);
		
		String img = doc.getRootElement().selectSingleNode("img").getText();
		String width = doc.getRootElement().selectSingleNode("width").getText();
		String height = doc.getRootElement().selectSingleNode("width").getText();
		
		Map<String,Object> res = new HashMap<String,Object>();
		res.put("img", img);
		res.put("width", width);
		res.put("height", height);
		return res;
		
		
	}

}
