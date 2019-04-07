package com.tntxia.oa.admin.action.mvc;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.MVCPageHandler;
import com.tntxia.web.mvc.WebRuntime;

public class IndexAction extends MVCPageHandler{
	
	private DBManager dbManager = this.getDBManager("admin");
	
	@SuppressWarnings("rawtypes")
	private List getList() throws Exception{
		String sql = "select * from tntxiaoa_app";
		return dbManager.queryForList(sql, true);
	}


	@Override
	public Map<String, Object> execute(WebRuntime runtime) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
