package com.tntxia.oa.admin.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class WarehouseAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("oa");
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		
		PageBean pageBean = runtime.getPageBean(20);
		
		String model = runtime.getParam("model");
		String sqlWhere = " where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if(StringUtils.isNotEmpty(model)) {
			params.add(model);
			sqlWhere += " and pro_model = ?";
		}
		
		List list = dbManager.queryForList("select top "+pageBean.getTop()+" * from warehouse "+sqlWhere+" order by id desc", params,true);
		int totalAmount = dbManager.getCount("select count(*) from warehouse "+sqlWhere,params);
		return this.getPagingResult(list, pageBean, totalAmount);
		
	}
	
	public Map<String,Object> update(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		String num = runtime.getParam("pro_num");
		
		String sql = "update warehouse set pro_num = ? where id = ?";
		dbManager.update(sql,new Object[] {num,id});
		return this.success();
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> inOutRecordList(WebRuntime runtime) throws Exception{
		
		PageBean pageBean = runtime.getPageBean(20);
		
		String model = runtime.getParam("model");
		String sqlWhere = " where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if(StringUtils.isNotEmpty(model)) {
			params.add(model);
			sqlWhere += " and w.pro_model = ?";
		}
		
		String number = runtime.getParam("number");
		if(StringUtils.isNotEmpty(number)) {
			params.add(number);
			sqlWhere += " and l.number = ?";
		}
		
		
		
		List list = dbManager.queryForList("select top "+pageBean.getTop()+" l.*,w.pro_model model from warehouse_in_out_log l left join warehouse w on l.wid = w.id "+sqlWhere+" order by l.id desc", params,true);
		int totalAmount = dbManager.getCount("select count(*) from warehouse_in_out_log l left join warehouse w on l.wid = w.id  "+sqlWhere,params);
		return this.getPagingResult(list, pageBean, totalAmount);
		
	}
	
	public Map<String,Object> updateInOutRecord(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		String final_num = runtime.getParam("final_num");
		
		String sql = "update warehouse_in_out_log set final_num = ? where id = ?";
		dbManager.update(sql,new Object[] {final_num,id});
		return this.success();
		
	}

}
