package com.tntxia.oa.admin.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;

public class MenuAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager();
	
	public Map<String, Object> update(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		Map<String, Object> res = new HashMap<String, Object>();
		if(StringUtils.isEmpty(id)){
			String sql = "insert into menu(name,url) values(?,?)";
			dbManager.update(sql, new Object[]{name,url});
		}else{
			String sql = "update menu set name = ? , url=? where id=?";
			dbManager.update(sql, new Object[]{name,url,id});
		}
		res.put("success", true);
		return res;
		
	}
	
	public Map<String, Object> delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String sql = "delete from menu where id = ?";
		dbManager.update(sql, new Object[]{id});
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;
	}
	
	public Map<String, Object> list(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String sql = "select * from menu";
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("rows", dbManager.queryForList(sql, true));
		return res;
	}

}
