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

/**
 * 
 * 货币类型管理
 * @author tntxia
 *
 */
public class TrademarkAction extends BaseAction{
	
	private DBManager dbManager = getDBManager("oa");
	
	public Map<String, Object> update(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String epro = request.getParameter("epro");
		String cpro = request.getParameter("cpro");
		
		Map<String, Object> res = new HashMap<String, Object>();
		if(StringUtils.isEmpty(id)){
			String sql = "insert into profll(epro,cpro,tnumber) values(?,?,'')";
			dbManager.update(sql, new Object[]{epro,cpro});
		}else{
			String sql = "update profll set epro = ? ,cpro=? where id=?";
			dbManager.update(sql, new Object[]{epro,cpro,id});
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
	
	public Map<String, Object> list(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String sql = "select * from profll where 1=1";
		
		String dict_type = request.getParameter("dict_type");
		String dict_key = request.getParameter("dict_key");
		String dict_value = request.getParameter("dict_value");
		
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
