package com.tntxia.oa.admin.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class PaywayAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("oa");
	
	public Map<String,Object> detail(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "select * from payment_fs where id = ?";
		return dbManager.queryForMap(sql, new Object[] {id}, true);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		
		String sql = "select * from payment_fs";
		
		List list = dbManager.queryForList(sql, true);
		sql = "select count(*) from payment_fs";
		int count = dbManager.getCount(sql);
		return this.getPagingResult(list, runtime, count);
	}
	
	public Map<String,Object> del(WebRuntime runtime) throws Exception{
		
		String sql = "delete from payment_fs where id = ?";
		String id = runtime.getParam("id");
		dbManager.update(sql, new Object[] {id});
		
		return this.success();
	}
	
	public Map<String,Object> add(WebRuntime runtime) throws Exception{
		
		String sql = "insert into payment_fs(payment,remark,pay_date) values(?,?,?)";
		String payment = runtime.getParam("payment");
		String remark = runtime.getParam("remark");
		dbManager.update(sql, new Object[] {payment,remark,new Date(System.currentTimeMillis())});
		
		return this.success();
	}
	
	public Map<String,Object> update(WebRuntime runtime) throws Exception{
		
		String sql = "update payment_fs set payment=?,remark=? where id = ?";
		String id = runtime.getParam("id");
		String payment = runtime.getParam("payment");
		String remark = runtime.getParam("remark");
		dbManager.update(sql, new Object[] {payment,remark,id});
		
		return this.success();
	}

}
