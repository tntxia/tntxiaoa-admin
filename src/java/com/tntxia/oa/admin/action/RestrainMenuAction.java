package com.tntxia.oa.admin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.dbmanager.DBManager;

import com.tntxia.web.mvc.BaseAction;

public class RestrainMenuAction extends BaseAction{
	
	private DBManager oaDBManager = this.getDBManager("oa");
	
	private DBManager dbManager = this.getDBManager();
	
	public Map<String,Object> list(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String sql = "select * from restrain";
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("rows", oaDBManager.queryForList(sql, true));
		return res;
	}
	
	private void clearMenu(String id) throws Exception{
		String sql = "delete from restrain_menu where restrain_id = ?";
		dbManager.update(sql, new Object[]{id});
		
	}
	
	public Map<String,Object> setMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String id = request.getParameter("id");
		String[] menus = request.getParameter("menus").split(",");
		clearMenu(id);
		
		String sql = "insert into restrain_menu(restrain_id,menu_id) values(?,?)";
		
		for(String menu:menus){
			dbManager.update(sql,new Object[]{id,menu});
		}
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> getMenus(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String id = request.getParameter("id");
		
		String sql = "select * from restrain_menu where restrain_id = ?";
		
		List list = dbManager.queryForList(sql,new Object[]{id},true);
		
		List<Integer> ids = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			Map item = (Map) list.get(i);
			ids.add((Integer) item.get("menu_id"));
		}
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("ids", ids);
		return res;
	}

}
