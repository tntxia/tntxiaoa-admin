package com.tntxia.oa.admin.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class CompanyAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("oa");
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		
		PageBean pageBean = runtime.getPageBean(20);
		
		List list = dbManager.queryForList("select top "+pageBean.getTop()+" * from company order by id desc", true);
		int totalAmount = dbManager.getCount("select count(*) from company ");
		return this.getPagingResult(list, pageBean, totalAmount);
		
	}
	
	public Map<String,Object> update(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		String logo = runtime.getParam("logo");
		String name = runtime.getParam("name");
		String tel = runtime.getParam("tel");
		
		String sql = "update company set companyname = ?,pic_id=?,companytel=? where id = ?";
		dbManager.update(sql,new Object[] {name,logo,tel,id});
		return this.success();
		
	}
	
	public Map<String,Object> add(WebRuntime runtime) throws Exception{
		
		String logo = runtime.getParam("logo");
		String name = runtime.getParam("name");
		String tel = runtime.getParam("tel");
		
		String sql = "insert into company(companyname,pic_id,companytel) values(?,?,?)";
		dbManager.update(sql,new Object[] {name,logo,tel});
		return this.success();
		
	}
	
	public Map<String,Object> detail(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		
		String sql = "select * from company where id = ?";
		return dbManager.queryForMap(sql,new Object[] {id}, true);
		
	}
	
	

}
