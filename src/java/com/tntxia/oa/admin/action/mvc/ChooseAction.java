package com.tntxia.oa.admin.action.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.MVCAction;

public class ChooseAction extends MVCAction{
	
	private DBManager dbManager = this.getDBManager("admin");
	
	@SuppressWarnings("rawtypes")
	private List getList() throws Exception{
		String sql = "select * from tntxiaoa_app";
		return dbManager.queryForList(sql, true);
	}

	@Override
	public void init(HttpServletRequest arg0, HttpServletResponse arg1)
			throws Exception {
		
		this.setToPage("choose.ftl");
		this.put("list", this.getList());
		
	}

}
