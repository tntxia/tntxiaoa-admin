package com.tntxia.oa.admin.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tntxia.datepower.DateUtil;
import com.tntxia.dbmanager.DBManager;
import com.tntxia.sqlexecutor.Transaction;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class SaleAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("oa");
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> list(WebRuntime runtime) throws Exception{
		
		PageBean pageBean = runtime.getPageBean();
		String sql = "select top "+pageBean.getTop()+" * from subscribe where 1=1";
		List list = dbManager.queryForList(sql, true);
		int count = dbManager.getCount("select count(*) from subscribe where 1=1");
		return this.getPagingResult(list, pageBean, count);
		
	}
	
	private void delOrder(Integer id ) throws SQLException {
		String sql = "delete from subscribe where id = ?";
		String sqlPro = "delete from ddpro where ddid = ?";
		Transaction trans = this.getTransaction("oa");
		try {
			trans.update(sql, new Object[] {id});
			trans.update(sqlPro, new Object[] {id});
			trans.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			trans.rollback();
		}finally {
			trans.close();
		}
		
	}
	
	@SuppressWarnings("rawtypes") 
	public Map<String,Object> batchDel(WebRuntime runtime) throws Exception{
		String sdate = runtime.getParam("sdate");
		String edate = runtime.getParam("edate");
		
		
		List param = new ArrayList();
		String sqlWhere = "";
		
		if(StringUtils.isNotEmpty(sdate)) {
			
			sqlWhere += " and datetime >= ? ";
			param.add(sdate);
		}
		
		if(StringUtils.isNotEmpty(edate)) {
			String nextDate = DateUtil.getNextDate(edate); 
			sqlWhere += " and datetime < ? ";
			param.add(nextDate);
		}
		
		String sql = "select * from subscribe where 1=1 " + sqlWhere;
		List list = dbManager.queryForList(sql, param, true);
		
		System.out.println("查询到"+ list.size()+"条记录。。。 正在准备删除");
		
		for(int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			Integer id = (Integer) map.get("id");
			this.delOrder(id);
			System.out.println("已删除"+i+"条记录");
		}
		
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> listRefund(WebRuntime runtime) throws Exception{
		
		PageBean pageBean = runtime.getPageBean();
		String sql = "select top "+pageBean.getTop()+" * from th_table where 1=1";
		List list = dbManager.queryForList(sql, true);
		int count = dbManager.getCount("select count(*) from th_table where 1=1");
		return this.getPagingResult(list, pageBean, count);
		
	}
	
	private void delOrderRefund(Integer id ) throws SQLException {
		String sql = "delete from th_table where id = ?";
		String sqlPro = "delete from th_pro where ddid = ?";
		Transaction trans = this.getTransaction("oa");
		try {
			trans.update(sql, new Object[] {id});
			trans.update(sqlPro, new Object[] {id});
			trans.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			trans.rollback();
		}finally {
			trans.close();
		}
		
	}
	
	@SuppressWarnings("rawtypes") 
	public Map<String,Object> batchDelRefund(WebRuntime runtime) throws Exception{
		String sdate = runtime.getParam("sdate");
		String edate = runtime.getParam("edate");
		
		
		List param = new ArrayList();
		String sqlWhere = "";
		
		if(StringUtils.isNotEmpty(sdate)) {
			
			sqlWhere += " and datetime >= ? ";
			param.add(sdate);
		}
		
		if(StringUtils.isNotEmpty(edate)) {
			String nextDate = DateUtil.getNextDate(edate); 
			sqlWhere += " and datetime < ? ";
			param.add(nextDate);
		}
		
		String sql = "select * from th_table where 1=1 " + sqlWhere;
		List list = dbManager.queryForList(sql, param, true);
		
		System.out.println("查询到"+ list.size()+"条记录。。。 正在准备删除");
		
		for(int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			Integer id = (Integer) map.get("id");
			this.delOrderRefund(id);
			System.out.println("已删除"+i+"条记录");
		}
		
		return this.success();
	}

}
