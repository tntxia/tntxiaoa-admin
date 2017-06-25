package com.tntxia.oa.admin.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;

public class ExpenseAccountAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager("oa");
	
	public Map<String, Object> update(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String coun_number = request.getParameter("coun_number");
		String coun_name = request.getParameter("coun_name");
		String coun_ename = request.getParameter("coun_ename");
		
		Map<String, Object> res = new HashMap<String, Object>();
		if(StringUtils.isEmpty(id)){
			String sql = "insert into km_f(coun_number,coun_name,coun_ename) values(?,?,?)";
			dbManager.update(sql, new Object[]{coun_number,coun_name,coun_ename});
		}else{
			String sql = "update department set coun_number = ? , coun_name=?, coun_ename = ? where id=?";
			dbManager.update(sql, new Object[]{coun_number,coun_name,coun_ename,id});
		}
		res.put("success", true);
		return res;
		
	}
	
	public Map<String, Object> delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String sql = "delete from km_f where id = ?";
		dbManager.update(sql, new Object[]{id});
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;
	}
	
	public Map<String, Object> execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String sql = "select * from km_f";
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("rows", dbManager.queryForList(sql, true));
		return res;
	}

}
