package com.tntxia.oa.admin.action;

import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class ModifyPassAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager();
	
	public Map<String,Object> execute(WebRuntime runtime) throws Exception{
		String pass = runtime.getParam("pass");
		String username = runtime.getSessionStr("username");
		String sql = "update admin set password = ? where name=?";
		dbManager.update(sql, new Object[]{pass,username});
		return this.success();
	}

}
