package com.tntxia.oa.admin.action.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.MVCAction;

public class DesignHeaderAction extends MVCAction{
	
	private DBManager dbManager = this.getDBManager();
	
	private Map<String,Object> getDetail() throws Exception{
		String sql = "select * from tntxiaoa_design_header";
		return dbManager.queryForMap(sql, true);
	}

	@Override
	public void init(HttpServletRequest arg0, HttpServletResponse arg1)
			throws Exception {
		
		
		
		this.setToPage("design/header.ftl");
		this.putAll(this.getDetail());
		
	}

}
