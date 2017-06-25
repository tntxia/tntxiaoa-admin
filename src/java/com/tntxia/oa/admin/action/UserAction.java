package com.tntxia.oa.admin.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;

public class UserAction extends BaseAction{
	
	private DBManager dbManager = getDBManager("oa");
	
	/**
	 * 用户查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String, Object> res = new HashMap<String, Object>();
		
		List rows = dbManager.queryForList("select * from username", true);
		res.put("rows", rows);
		return res;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> unlock(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String sql = "update username set err = 0 where nameid = ?";
		String id = request.getParameter("id");
		dbManager.update(sql, new Object[]{id});
		return success();
	}

}
