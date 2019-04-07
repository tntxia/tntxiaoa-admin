package com.tntxia.oa.admin.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class PositionAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("oa");
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		PageBean pageBean = runtime.getPageBean(20);
		String sql = "select top "+pageBean.getTop()+" id,role_name name,role_date roleDate from role";
		List list = dbManager.queryForList(sql, true);
		int count = dbManager.queryForInt("select count(*) from role");
		return this.getPagingResult(list, pageBean, count);
		
	}
	
	@SuppressWarnings("rawtypes")
	public List listAll(WebRuntime runtime) throws Exception{
		
		String sql = "select * from role";
		List list = dbManager.queryForList(sql, true);
		return list;
		
	}

}
