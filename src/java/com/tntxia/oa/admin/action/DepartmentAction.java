package com.tntxia.oa.admin.action;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class DepartmentAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("oa");
	
	@SuppressWarnings("rawtypes")
	public List listAll(WebRuntime runtime) throws Exception {
		return dbManager.queryForList("select * from department", true);
	}

}
