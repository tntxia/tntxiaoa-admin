package com.tntxia.oa.admin.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class ProccessPrepaidAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("oa_back");
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime ) throws Exception{
		
		String sqlWhere = " where flow_type='prepaid'";
		
		String sql = "select * from work_flow";
		List list = dbManager.queryForList(sql+sqlWhere, true);
		
		sql = "select count(*) from work_flow";
		int count = dbManager.getCount(sql+sqlWhere);
		return this.getPagingResult(list, runtime, count);
		
	}
	
	public Map<String,Object> add(WebRuntime runtime ) throws Exception{
		
		String dept = runtime.getParam("dept");
		String man = runtime.getParam("man");
		String sql = "insert into work_flow(flow_type,dept,audit_man,create_time) values('prepaid',?,?,?)";
		dbManager.update(sql, new Object[] {dept,man,new Date(System.currentTimeMillis())});
		return this.success();
		
	}
	

}
