package com.tntxia.oa.admin.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;

public class SystemDepartmentAction extends BaseAction{
	
	private DBManager oaDBManager = this.getDBManager("oa");
	
	public Map<String, Object> update(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String name = request.getParameter("departname");
		String type = request.getParameter("dept_types");
		String dept_code = request.getParameter("dept_code");
		String remark = request.getParameter("remark");
		String leader = request.getParameter("leader");
		String url = request.getParameter("url");
		Map<String, Object> res = new HashMap<String, Object>();
		if(StringUtils.isEmpty(id)){
			String sql = "insert into department(departname,dept_types,dept_code,remark,leader) values(?,?,?,?,?)";
			oaDBManager.update(sql, new Object[]{name,type,dept_code,remark,leader});
		}else{
			String sql = "update department set name = ? , url=? where id=?";
			oaDBManager.update(sql, new Object[]{name,url,id});
		}
		res.put("success", true);
		return res;
		
	}
	
	public Map<String, Object> delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String sql = "delete from department where id = ?";
		oaDBManager.update(sql, new Object[]{id});
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;
	}

}
