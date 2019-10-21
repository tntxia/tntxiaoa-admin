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
	
	public Map<String, Object> add(WebRuntime runtime) throws Exception{
		String role_name=runtime.getParam("role_name");
		String remark=runtime.getParam("remark");
		
		String sql="insert into role(role_name,remark,role_date) values(?,?,getdate())";
		dbManager.update(sql, new Object[] {role_name, remark});
		return this.success();
	}
	
	public Map<String, Object> detail(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql="select * from role where id = ?";
		return dbManager.queryForMap(sql, new Object[] {id}, true);
	}
	
	public Map<String, Object> update(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String role_name=runtime.getParam("role_name");
		String remark=runtime.getParam("remark");
		
		String sql="update role set role_name=?,remark=? where id = ?";
		dbManager.update(sql, new Object[] {role_name, remark, id});
		return this.success();
	}
	
	public Map<String, Object> del(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		
		String sql="delete from role where id = ?";
		dbManager.update(sql, new Object[] {id});
		return this.success();
	}
	
}
