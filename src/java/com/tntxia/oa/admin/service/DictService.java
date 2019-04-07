package com.tntxia.oa.admin.service;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.service.CommonService;

public class DictService extends CommonService {
	
	private DBManager dbManager = this.getDBManager();
	
	@SuppressWarnings("rawtypes")
	private static List dictList;
	
	private void initDict() throws Exception {
		dictList = dbManager.queryForList("select * from dict", true);
	}
	
	@SuppressWarnings("rawtypes")
	public String getNameByKey(String key) throws Exception {
		if(dictList==null) {
			initDict();
		}
		
		if(dictList!=null) {
			for(int i=0;i<dictList.size();i++) {
				Map d = (Map)dictList.get(i);
				String dictKey = (String) d.get("dict_key");
				if(dictKey.equals(key)) {
					return (String) d.get("dict_value");
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public List getDepartmentTypeList() throws Exception {
		String sql = "select * from dict where dict_type='department_type'";
		
		
		return dbManager.queryForList(sql, true);
	}

}
