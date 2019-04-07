package com.tntxia.oa.admin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

/**
 * 
 * 数据字典管理
 * @author tntxia
 *
 */
public class DictAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager();
	
	public Map<String, Object> update(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("dict_type");
		String key = request.getParameter("dict_key");
		String value = request.getParameter("dict_value");
		
		Map<String, Object> res = new HashMap<String, Object>();
		if(StringUtils.isEmpty(id)){
			String sql = "insert into dict(dict_type,dict_key,dict_value) values(?,?,?)";
			dbManager.update(sql, new Object[]{type,key,value});
		}else{
			String sql = "update dict set dict_type = ? , dict_key=?,dict_value=? where id=?";
			dbManager.update(sql, new Object[]{type,key,value,id});
		}
		res.put("success", true);
		return res;
		
	}
	
	public Map<String, Object> delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String sql = "delete from dict where id = ?";
		dbManager.update(sql, new Object[]{id});
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;
	}
	
	public Map<String, Object> list(WebRuntime runtime) throws Exception{
		
		String sql = "select * from dict where 1=1";
		
		String dict_type = runtime.getParam("dict_type");
		String dict_key = runtime.getParam("dict_key");
		String dict_value = runtime.getParam("dict_value");
		
		List<Object> params = new ArrayList<Object>();
		
		if(StringUtils.isNotEmpty(dict_type)){
			sql += " and dict_type like '%"+dict_type+"%' ";
		}
		
		if(StringUtils.isNotEmpty(dict_key)){
			sql += " and dict_key = ? ";
			params.add(dict_key);
		}
		
		if(StringUtils.isNotEmpty(dict_value)){
			sql += " and dict_value = ? ";
			params.add(dict_value);
		}
		
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("rows", dbManager.queryForList(sql,params, true));
		
		return res;
		
	}

}
