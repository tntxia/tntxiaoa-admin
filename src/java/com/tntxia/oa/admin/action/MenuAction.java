package com.tntxia.oa.admin.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.alibaba.fastjson.JSON;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.httptrans.HttpTransferFactory;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.annotation.Param;
import com.tntxia.web.util.UUIDUtils;

public class MenuAction extends BaseAction {

	private DBManager dbManager = this.getDBManager("oa_back");

	public Map<String, Object> update(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String name = runtime.getParam("name");
		String key_name = runtime.getParam("key_name");
		String url = runtime.getParam("url");
		String order_no = runtime.getParam("order_no");

		String sql = "update menu set name = ? , url=?,order_no=?,key_name=? where id=?";
		dbManager.update(sql, new Object[] { name, url, order_no,key_name, id });

		return this.success();

	}

	public Map<String, Object> add(@Param("pid") String pid, 
			@Param("name") String name, @Param("url") String url,
			@Param("key_name") String key_name, @Param("order_no") String order_no) throws Exception {

		String id = UUIDUtils.getUUID();
		String sql = "insert into menu (id, name,url,key_name, order_no,pid) values(?, ?,?,?,?,?)";
		dbManager.update(sql, new Object[] {id, name, url, key_name,order_no,pid });

		return this.success();

	}

	public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String id = request.getParameter("id");
		String sql = "delete from menu where id = ?";
		dbManager.update(sql, new Object[] { id });

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;
	}

	@SuppressWarnings("rawtypes")
	public List list(@Param("pid") String pid, WebRuntime runtime) throws Exception {

		String sql = "select * from menu order  by order_no";
		List list = dbManager.queryForList(sql, true);
		return list;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> export(WebRuntime runtime) throws Exception {
		String sql = "select * from menu";
		List list = dbManager.queryForList(sql, true);
		String content = JSON.toJSONString(list);
		
		String tempDirPath = runtime.getRealPath("/WEB-INF/temp");
		File tempDir = new File(tempDirPath);
		tempDir.mkdirs();
		String uuid = UUIDUtils.getUUID();
		String tempPath = tempDir.getAbsolutePath() + File.separatorChar + uuid + ".json";
		File file = new File(tempPath);
		if(!file.exists()){
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fileWriter);
		bw.write(content);
		bw.close();
		System.out.println("finish");
		

		HttpTransfer trans = HttpTransferFactory.generate("file_center");
		Map<String, Object> transResult = trans.uploadFile("file!upload", tempPath, new HashMap<String, String>());
		uuid = (String) transResult.get("uuid");
		return this.success("uuid", uuid);

	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> importMenu(@Param("file") FileItem fileItem,
			WebRuntime runtime) throws Exception {
		
		String content = fileItem.getString("UTF-8");
		List list = (List) JSON.parse(content);
		
		Transaction trans = this.getTransaction("oa_back");
		try {
			trans.update("delete from menu");
			for(int i=0;i<list.size();i++) {
				Map map = (Map)list.get(i);
				String id = (String) map.get("id");
				String name = (String) map.get("name");
				String key_name = (String) map.get("key_name");
				String url = (String) map.get("url");
				Integer order_no = (Integer) map.get("order_no");
				String pid = (String) map.get("pid");
				String sql = "insert into menu(id, name, key_name, url, order_no, pid) values(?, ?, ?, ?, ?, ?)";
				trans.update(sql, new Object[] {id, name, key_name, url, order_no, pid});
			}
			trans.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			trans.rollback();
		}finally {
			trans.close();
		}
		
		System.out.println(list.size());

		return this.success();

	}

	public Map<String, Object> detail(WebRuntime runtime) throws Exception {

		String id = runtime.getParam("id");

		String sql = "select * from menu where id = ?";
		return dbManager.queryForMap(sql, new Object[] { id }, true);

	}

}
