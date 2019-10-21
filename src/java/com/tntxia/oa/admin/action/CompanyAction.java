package com.tntxia.oa.admin.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.httptrans.HttpTransferFactory;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.annotation.Param;
import com.tntxia.web.mvc.entity.MultipartForm;
import com.tntxia.web.util.UUIDUtils;

public class CompanyAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("oa");
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		
		PageBean pageBean = runtime.getPageBean(20);
		
		List list = dbManager.queryForList("select top "+pageBean.getTop()+" * from company order by id desc", true);
		int totalAmount = dbManager.getCount("select count(*) from company ");
		return this.getPagingResult(list, pageBean, totalAmount);
		
	}
	
	public Map<String,Object> update(@Param("id") String id, 
			@Param("companyname") String companyname, 
			@Param("companytel") String companytel, 
			@Param("pic") FileItem pic,
			HttpServletRequest request) throws Exception{
		
		String sql = "update company set companyname = ?,companytel=?";
		List<Object> params = new ArrayList<Object>();
		params.add(companyname);
		params.add(companytel);
		if (pic!=null) {
			sql += ",pic_id=?";
			
			String fileNameOriginal = pic.getName();
			String ext = FilenameUtils.getExtension(fileNameOriginal);
			String uploadPath = request.getServletContext().getRealPath("/uploadFile");
			File uploadDir = new File(uploadPath);
			uploadDir.mkdirs();
			String uuid = UUIDUtils.getUUID();
			String tempPath = uploadDir.getAbsolutePath() + File.separatorChar + uuid + "." + ext;
			File tempFile = new File(tempPath);
			System.out.println("文件写入" + tempFile.getAbsolutePath());
			pic.write(tempFile);
			
			HttpTransfer trans = HttpTransferFactory.generate("file_center");
			Map<String, Object> transResult = trans.uploadFile("file!upload", tempPath, new HashMap<String, String>());
			uuid = (String) transResult.get("uuid");
			params.add(uuid);
		}
		sql += " where id = ?";
		params.add(id);
		dbManager.executeUpdate(sql,params);
		return this.success();
		
	}
	
	public Map<String,Object> add(
			@Param("name") String name, 
			@Param("tel") String tel, 
			@Param("pic") FileItem pic,
			HttpServletRequest request) throws Exception{
		
		
		String fileNameOriginal = pic.getName();
		String ext = FilenameUtils.getExtension(fileNameOriginal);
		
		String uploadPath = request.getServletContext().getRealPath("/uploadFile");
		File uploadDir = new File(uploadPath);
		uploadDir.mkdirs();
		String uuid = UUIDUtils.getUUID();
		
		String tempPath = uploadDir.getAbsolutePath() + File.separatorChar + uuid + "." + ext;
		File tempFile = new File(tempPath);
		System.out.println("文件写入" + tempFile.getAbsolutePath());
		pic.write(tempFile);
		
		HttpTransfer trans = HttpTransferFactory.generate("file_center");
		Map<String, Object> transResult = trans.uploadFile("file!upload", tempPath, new HashMap<String, String>());
		uuid = (String) transResult.get("uuid");
		
		String sql = "insert into company(companyname,pic_id,companytel) values(?,?,?)";
		dbManager.update(sql,new Object[] {name,uuid,tel});
		return this.success();
		
	}
	
	public Map<String,Object> del(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "delete from company where id = ?";
		dbManager.update(sql,new Object[] {id});
		return this.success();
		
	}
	
	public Map<String,Object> detail(@Param("id") String id) throws Exception{
		String sql = "select * from company where id = ?";
		return dbManager.queryForMap(sql,new Object[] {id}, true);
		
	}
	
	

}
