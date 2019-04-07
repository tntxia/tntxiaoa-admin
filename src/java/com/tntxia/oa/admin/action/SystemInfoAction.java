package com.tntxia.oa.admin.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;

public class SystemInfoAction  extends BaseAction{
	
	private DBManager dbManager = getDBManager("oa");
	
	public Map<String,Object> view(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String sql =  "select * from system_info";
		Map<String,Object> systemInfo = dbManager.queryForMap(sql, true);
		return systemInfo;
		
	}
	
	/**
	 * 修改系统基本信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> update(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String name = request.getParameter("name");
		String website = request.getParameter("website");
		String sql = "update system_info set companyName = ?, website = ?";
		dbManager.update(sql,new String[]{name,website});
		return this.success();
	}

}
