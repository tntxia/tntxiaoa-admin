package com.tntxia.oa.admin.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class NoticeAction extends BaseAction {

	private DBManager dbManager = this.getDBManager("oa");

	/**
	 * 列出所有的公告
	 * 
	 * @param request
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception {

		String sql = "select * from pub_table  order by fvdate desc";
		List list = dbManager.queryForList(sql, true);
		
		sql = "select count(*) from pub_table";
		int totalAmount = dbManager.getCount(sql);

		return this.getPagingResult(list, runtime, totalAmount);

	}
	
	public Map<String,Object> detail(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "select * from pub_table where id = ?";
		Map<String,Object> res = dbManager.queryForMap(sql, new Object[] {id},true);
		return res;
	}
	
	public Map<String,Object> update(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String title = runtime.getParam("titel");
		String content = runtime.getParam("content");
		String sql = "update pub_table set titel=?,content=? where id = ?";
		dbManager.update(sql, new Object[] {title,content,id});
		return this.success();
	}

}
