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

public class PurchasingAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("oa");
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> list(WebRuntime runtime) throws Exception{
		
		PageBean pageBean = runtime.getPageBean();
		String sql = "select top "+pageBean.getTop()+" * from procure where 1=1";
		List list = dbManager.queryForList(sql, true);
		int count = dbManager.getCount("select count(*) from procure where 1=1");
		return this.getPagingResult(list, pageBean, count);
		
	}
	
	private void delOrder(Integer id ) throws SQLException {
		String sql = "delete from procure where id = ?";
		String sqlPro = "delete from cgpro where ddid = ?";
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
		
		String sql = "select * from procure where 1=1 " + sqlWhere;
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
		String sql = "select top "+pageBean.getTop()+" * from th_table_supplier where 1=1";
		List list = dbManager.queryForList(sql, true);
		int count = dbManager.getCount("select count(*) from th_table_supplier where 1=1");
		return this.getPagingResult(list, pageBean, count);
		
	}
	
	private void delRefundOrder(Integer id ) throws SQLException {
		String sql = "delete from th_table_supplier where id = ?";
		String sqlPro = "delete from th_pro_supplier where ddid = ?";
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
		
		String sql = "select * from th_table_supplier where 1=1 " + sqlWhere;
		List list = dbManager.queryForList(sql, param, true);
		
		System.out.println("查询到"+ list.size()+"条记录。。。 正在准备删除");
		
		for(int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			Integer id = (Integer) map.get("id");
			this.delRefundOrder(id);
			System.out.println("已删除"+i+"条记录");
		}
		
		return this.success();
	}

}
