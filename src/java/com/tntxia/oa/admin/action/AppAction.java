package com.tntxia.oa.admin.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;

public class AppAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager("admin");
	
	/**
	 * 增加或修改
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> update(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		if(id==null){
			String sql = "insert into tntxiaoa_app(name) values(?)";
			dbManager.update(sql,new Object[]{name});
		}else{
			String sql = "update tntxiaoa_app set name = ? where id = ?";
			dbManager.update(sql,new Object[]{name,id});
		}
		return this.success();
	}
	
	/**
	 * 删除应用
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Object> delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String sql = "delete from tntxiaoa_app where id = ?";
		dbManager.update(sql,new Object[]{id});
		return this.success();
	}
	
	private int getCount() throws Exception{
		String sql = "select count(*) from tntxiaoa_app";
		return dbManager.getCount(sql);
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		PageBean pageBean = this.getPageBean(request);
		String sql = "select top "+pageBean.getTop()+" * from tntxiaoa_app";
		List list = dbManager.queryForList(sql, true);
		int count = this.getCount();
		return this.getPagingResult(list, pageBean, count);
	}
	
	@SuppressWarnings("rawtypes")
	public List listAll(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		PageBean pageBean = this.getPageBean(request);
		String sql = "select top "+pageBean.getTop()+" * from tntxiaoa_app";
		return dbManager.queryForList(sql, true);
		
	}
	
	public Map<String,Object> choose(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String app = request.getParameter("app");
		request.getSession().setAttribute("app", app);
		return this.success();
	}

}
