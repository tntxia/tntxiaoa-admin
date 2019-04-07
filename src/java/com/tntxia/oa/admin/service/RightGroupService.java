package com.tntxia.oa.admin.service;

import java.util.List;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.service.CommonService;

public class RightGroupService extends CommonService {
	
	private DBManager dbManager = this.getDBManager("oa_back");
	
	@SuppressWarnings("rawtypes")
	public List listAll() throws Exception {
		String sql = "select * from user_right_group";
		return dbManager.queryForList(sql, true);
	}

}
