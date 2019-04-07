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

public class FinanceAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("oa");
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> listExchange(WebRuntime runtime) throws Exception{
		
		PageBean pageBean = runtime.getPageBean();
		String sql = "select top "+pageBean.getTop()+" * from credit_debit where 1=1";
		List list = dbManager.queryForList(sql, true);
		int count = dbManager.getCount("select count(*) from credit_debit where 1=1");
		return this.getPagingResult(list, pageBean, count);
		
	}
	
	private void delExchange(Integer id ) throws SQLException {
		String sql = "delete from credit_debit where id = ?";
		Transaction trans = this.getTransaction("oa");
		try {
			trans.update(sql, new Object[] {id});
			trans.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			trans.rollback();
		}finally {
			trans.close();
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> batchDelExchange(WebRuntime runtime) throws Exception{
		String sdate = runtime.getParam("sdate");
		String edate = runtime.getParam("edate");
		
		List param = new ArrayList();
		String sqlWhere = "";
		
		if(StringUtils.isNotEmpty(sdate)) {
			sqlWhere += " and l_date >= ? ";
			param.add(sdate);
		}
		
		if(StringUtils.isNotEmpty(edate)) {
			String nextDate = DateUtil.getNextDate(edate); 
			sqlWhere += " and l_date < ? ";
			param.add(nextDate);
		}
		
		String sql = "select * from credit_debit where 1=1 " + sqlWhere;
		List list = dbManager.queryForList(sql, param, true);
		
		System.out.println("查询到"+ list.size()+"条记录。。。 正在准备删除");
		
		for(int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			Integer id = (Integer) map.get("id");
			this.delExchange(id);
			System.out.println("已删除"+i+"条记录");
		}
		
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> listPayment(WebRuntime runtime) throws Exception{
		
		PageBean pageBean = runtime.getPageBean();
		String sql = "select top "+pageBean.getTop()+" * from payment where 1=1";
		List list = dbManager.queryForList(sql, true);
		int count = dbManager.getCount("select count(*) from payment where 1=1");
		return this.getPagingResult(list, pageBean, count);
		
	}
	
	private void delPayment(Integer id ) throws SQLException {
		String sql = "delete from payment where id = ?";
		Transaction trans = this.getTransaction("oa");
		try {
			trans.update(sql, new Object[] {id});
			trans.commit();
		}catch(Exception ex) {
			ex.printStackTrace();
			trans.rollback();
		}finally {
			trans.close();
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> batchDelPayment(WebRuntime runtime) throws Exception{
		String sdate = runtime.getParam("sdate");
		String edate = runtime.getParam("edate");
		
		List param = new ArrayList();
		String sqlWhere = "";
		
		if(StringUtils.isNotEmpty(sdate)) {
			sqlWhere += " and sjfkdate >= ? ";
			param.add(sdate);
		}
		
		if(StringUtils.isNotEmpty(edate)) {
			String nextDate = DateUtil.getNextDate(edate); 
			sqlWhere += " and sjfkdate < ? ";
			param.add(nextDate);
		}
		
		String sql = "select * from payment where 1=1 " + sqlWhere;
		List list = dbManager.queryForList(sql, param, true);
		
		System.out.println("查询到"+ list.size()+"条记录。。。 正在准备删除");
		
		for(int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			Integer id = (Integer) map.get("id");
			this.delPayment(id);
			System.out.println("已删除"+i+"条记录");
		}
		
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> listGathering(WebRuntime runtime) throws Exception{
		
		PageBean pageBean = runtime.getPageBean();
		String sql = "select top "+pageBean.getTop()+" * from gathering where 1=1";
		List list = dbManager.queryForList(sql, true);
		int count = dbManager.getCount("select count(*) from gathering where 1=1");
		return this.getPagingResult(list, pageBean, count);
		
	}
	

}
