package com.tntxia.oa.admin.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class AppAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager("admin");
	
	/**
	 * 增加或修改
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> update(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		if(id==null){
			String sql = "insert into tntxiaoa_app(name) values(?)";
			dbManager.update(sql,new Object[]{name});
		}else{
			String sql = "update tntxiaoa_app set name = ? where id = ?";
			dbManager.update(sql,new Object[]{name,id});
		}
		return this.success();
	}
	
	/**
	 * 删除应用
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String sql = "delete from tntxiaoa_app where id = ?";
		dbManager.update(sql,new Object[]{id});
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		
		ResourceBundle rb = ResourceBundle.getBundle("oa_admin");
		HttpTransfer transfer = new HttpTransfer();
		transfer.setHost(rb.getString("oa_center_server"));
		transfer.setPort(rb.getString("oa_center_port"));
		transfer.setContextPath(rb.getString("oa_center_url"));
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("page", runtime.getParam("page"));
		Map res = transfer.getMap("app!list", param);
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public List listAll(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		ResourceBundle rb = ResourceBundle.getBundle("oa_admin");
		HttpTransfer transfer = new HttpTransfer();
		transfer.setHost(rb.getString("oa_center_server"));
		transfer.setPort(rb.getString("oa_center_port"));
		transfer.setContextPath(rb.getString("oa_center_url"));
		
		List list = transfer.getList("app!listAll", null);
		return list;
		
	}
	
	public Map<String,Object> choose(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String app = request.getParameter("app");
		request.getSession().setAttribute("app", app);
		return this.success();
	}

}
