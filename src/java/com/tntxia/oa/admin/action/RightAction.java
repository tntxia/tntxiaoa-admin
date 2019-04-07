package com.tntxia.oa.admin.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class RightAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("oa_back");
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		PageBean pageBean = runtime.getPageBean(20);
		String sql = "select top "+pageBean.getTop()+" r.*,rg.name groupName from user_right r left join user_right_group rg where r.group_id = rg.id";
		List list = dbManager.queryForList(sql, true);
		int count = dbManager.queryForInt("select count(*) from user_right");
		return this.getPagingResult(list, pageBean, count);
	}
	
	public Map<String,Object> add(WebRuntime runtime) throws Exception{
		String groupId = runtime.getParam("groupId");
		String sql = "insert into user_right(name,display,group_id) values(?,?,?)";
		dbManager.update(sql,new Object[] {runtime.getParam("name"),runtime.getParam("display"),groupId});
		return this.success();
	}
	
	public Map<String,Object> del(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "delete from user_right where id = ?";
		dbManager.update(sql,new Object[] {id});
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public List search(WebRuntime runtime) throws Exception{
		String sql = "select * from user_right where display like ?";
		return dbManager.queryForList(sql, new Object[] {"%"+runtime.getParam("name")+"%"},true);
	}

}
