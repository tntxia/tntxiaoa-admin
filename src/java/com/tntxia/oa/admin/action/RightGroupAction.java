package com.tntxia.oa.admin.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.admin.service.RightGroupService;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class RightGroupAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("oa_back");
	
	private RightGroupService service = new RightGroupService();
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		PageBean pageBean = runtime.getPageBean(20);
		String sql = "select top "+pageBean.getTop()+" * from user_right_group";
		List list = dbManager.queryForList(sql, true);
		int count = dbManager.queryForInt("select count(*) from user_right_group");
		return this.getPagingResult(list, pageBean, count);
	}
	
	@SuppressWarnings("rawtypes")
	public List listAll(WebRuntime runtime) throws Exception {
		return service.listAll();
	}
	
	public Map<String,Object> add(WebRuntime runtime) throws Exception{
		
		String sql = "insert into user_right_group(name) values(?)";
		dbManager.update(sql,new Object[] {runtime.getParam("name")});
		return this.success();
	}
	
	public Map<String,Object> del(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "delete from user_right_group where id = ?";
		dbManager.update(sql,new Object[] {id});
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public List search(WebRuntime runtime) throws Exception{
		String sql = "select * from user_right_group where name like ?";
		return dbManager.queryForList(sql, new Object[] {"%"+runtime.getParam("name")+"%"},true);
	}

}
