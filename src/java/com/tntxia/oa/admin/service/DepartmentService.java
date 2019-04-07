package com.tntxia.oa.admin.service;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.service.CommonService;

public class DepartmentService extends CommonService{
	
	private DBManager dbManager = this.getDBManager("oa");
	
	@SuppressWarnings("rawtypes")
	private static List departmentList;
	
	private void initDepartment() throws Exception {
		departmentList = dbManager.queryForList("select * from department", true);
	}
	
	@SuppressWarnings("rawtypes")
	public String getDepartmentName(Integer id) throws Exception {
		
		if(departmentList==null) {
			initDepartment();
		}
		
		if(departmentList!=null) {
			for(int i=0;i<departmentList.size();i++) {
				Map d = (Map)departmentList.get(i);
				Integer departId = (Integer)d.get("id");
				if(departId.equals(id)) {
					return (String) d.get("departname");
				}
			}
		}
		return null;
	}
	
	

}
