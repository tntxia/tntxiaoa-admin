package com.tntxia.oa.admin.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 
 * 货币类型管理
 * @author tntxia
 *
 */
public class CurrencyAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager();
	
	public Map<String, Object> update(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String rate = request.getParameter("rate");
		
		Map<String, Object> res = new HashMap<String, Object>();
		if(StringUtils.isEmpty(id)){
			String sql = "insert into currency(name,code,rate) values(?,?,?)";
			dbManager.update(sql, new Object[]{name,code,rate});
		}else{
			String sql = "update currency set name = ? , code=?,rate=? where id=?";
			dbManager.update(sql, new Object[]{name,code,rate,id});
		}
		res.put("success", true);
		return res;
		
	}
	
	public Map<String, Object> delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String sql = "delete from currency where id = ?";
		dbManager.update(sql, new Object[]{id});
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;
	}
	
	public Map<String,Object> add(WebRuntime runtime) throws Exception{
		
		String name = runtime.getParam("name");
		String code = runtime.getParam("code");
		String rate = runtime.getParam("rate");
		String iconClass = runtime.getParam("iconClass");
		String sql = "insert into currency(name,code,rate,icon_class) values(?,?,?,?)";
		dbManager.update(sql, new Object[]{name,code,rate,iconClass});
		return this.success();
		
	}
	
	public Map<String, Object> list(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String sql = "select * from currency where 1=1";
		
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("rows", dbManager.queryForList(sql,new Object[]{}, true));
		
		return res;
		
	}

}
