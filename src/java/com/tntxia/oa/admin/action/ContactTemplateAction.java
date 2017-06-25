package com.tntxia.oa.admin.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class ContactTemplateAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager("oa");
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		
		PageBean pageBean = runtime.getPageBean(10);
		List list = dbManager.queryForList("select * from ht_mb", true);
		int totalAmount = dbManager.queryForInt("select count(*) from ht_mb");
		
		return this.getPagingResult(list, pageBean, totalAmount);
		
	}
	
	public Map<String,Object> update(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String name = runtime.getParam("q_name");
		String company = runtime.getParam("q_company");
		String tk = runtime.getParam("q_tk");
		String q_addr = runtime.getParam("q_addr");
		String sql = "update ht_mb set q_name = ?,q_company=?,q_tk=?,q_addr=? where id = ?";
		dbManager.update(sql, new Object[]{name,company,tk,q_addr,id});
		return this.success();
	}

}
