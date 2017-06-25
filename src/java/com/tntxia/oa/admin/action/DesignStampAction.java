package com.tntxia.oa.admin.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;

public class DesignStampAction extends BaseAction {

	private DBManager dbManager = this.getDBManager("admin");

	private String getUplaodPath(String app, String appPath, String newFileName,
			HttpServletRequest request) {
		
		String uploadDirPath = this.getAppRealPath(app, request)
				+ "\\"+appPath;
		File uploadDir = new File(uploadDirPath);
		uploadDir.mkdirs();
		String res = uploadDirPath + "\\" + newFileName;
		return res;
	}

	/**
	 * 
	 * @param fileName
	 * @param filePath
	 * @throws Exception 
	 */
	private void saveToDB(String app, String appPath,
			String realPath,String width,String height) throws Exception {
		String sql = "insert into tntxiaoa_design_stamp(app_path,real_path,app,width,height,create_time) values(?,?,?,?,?,?)";
		dbManager.update(sql, new Object[] { appPath,realPath,app,width,height,
				new Date(System.currentTimeMillis()) });
	}

	private String getParameter(List<FileItem> list, String key)
			throws UnsupportedEncodingException {

		for (FileItem item : list) {
			// 如果fileitem中封装的是普通输入项的数据
			if (item.isFormField()) {
				String name = item.getFieldName();
				if (key.equals(name)) {
					return item.getString("UTF-8");
				}
			}
		}
		return null;
	}
	
	private void makeConfigFile(String app,String headerImagePath,String width,String height
			,HttpServletRequest request){
		
		String appPath = this.getAppRealPath(app, request);
		
		//2.第二种 创建文档及设置根元素节点的方式  
        Element root = DocumentHelper.createElement("header");
        Document document = DocumentHelper.createDocument(root);
          
        //给根节点添加孩子节点 
        Element element1 = root.addElement("img");  
        element1.setText(headerImagePath);
          
        Element element2 = root.addElement("width");  
        element2.setText(width);
        
        Element element3 = root.addElement("height");  
        element3.setText(height);
        
        String designConfigDir = appPath+"\\WEB-INF\\config\\design";
        new File(designConfigDir).mkdirs();
          
        //把生成的xml文档存放在硬盘上  true代表是否换行  
        OutputFormat format = new OutputFormat("    ",true);  
        format.setEncoding("UTF-8");//设置编码格式  
        XMLWriter xmlWriter = null;
		try {
			xmlWriter = new XMLWriter(new FileOutputStream(designConfigDir+"\\stamp.xml"),format);
			xmlWriter.write(document);  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(xmlWriter!=null)
				try {
					xmlWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
      
	}
	
	private String getAppRealPath(String app,HttpServletRequest request){
		File currPath = new File(request.getServletContext().getRealPath("/"));
		String parentPath = currPath.getParent();
		return parentPath+"\\"+app;
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
		String appPath = getParameter(list, "appPath");
		String width = getParameter(list, "width");
		String height = getParameter(list, "height");
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

		String uploadPath = this.getUplaodPath(app, appPath,newFileName, request);

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

		// 保存到admin数据库中
		this.saveToDB(app, appPath,uploadPath,width,height);
		
		// 生成配置文件
		this.makeConfigFile(app, appPath+"/"+newFileName, width, height, request);

		return this.success();
	}

}
