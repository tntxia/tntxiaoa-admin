package com.tntxia.oa.admin.service;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.service.CommonService;

public class MenuService extends CommonService {
	
	private DBManager dbManager = this.getDBManager("oa_back");
	
	@SuppressWarnings("rawtypes")
	public List list() throws Exception {
		return dbManager.queryForList("select * from menu", true);
	}

}
