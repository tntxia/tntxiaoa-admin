package com.tntxia.oa.admin.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.annotation.Param;

public class FlowPurchasingAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager("oa");
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		
		PageBean pageBean = runtime.getPageBean(10);
		List list = dbManager.queryForList("select top "+pageBean.getTop()+" * from cgsp", true);
		int totalAmount = dbManager.queryForInt("select count(*) from cgsp");
		return this.getPagingResult(list, pageBean, totalAmount);
		
	}
	
	public Map<String,Object> detail(@Param("id") String id) throws Exception{
		String sql = "select * from cgsp where id = ?";
		Map<String,Object> data = dbManager.queryForMap(sql, new Object[] {id}, true);
		return this.success("data", data);
		
	}
	
	public Map<String,Object> create(WebRuntime runtime) throws Exception{
		
		String dept = runtime.getParam("dept");
		String role = runtime.getParam("role");
		String price_min = runtime.getParam("price_min");
		String price_max = runtime.getParam("price_max");
		String dd_man = runtime.getParam("dd_man");
		String fif = runtime.getParam("fif");
		String fspman = runtime.getParam("fspman");
		String remark = runtime.getParam("remark");
		
		String sql = "insert into cgsp(dept,role,price_min,price_max,dd_man,fif,fspman,remark,dd_date) values(?,?,?,?,?,?,?,?,now())";
		dbManager.update(sql,new Object[] {dept,role,price_min,price_max,dd_man,fif,fspman,remark});
		
		return this.success();
		
	}
	
	public Map<String,Object> update(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		
		String dept = runtime.getParam("dept");
		String role = runtime.getParam("role");
		String price_min = runtime.getParam("price_min");
		String price_max = runtime.getParam("price_max");
		String dd_man = runtime.getParam("dd_man");
		String fif = runtime.getParam("fif");
		String fspman = runtime.getParam("fspman");
		String remark = runtime.getParam("remark");
		
		String sql = "update cgsp set dept=?,role=?,price_min=?,price_max=?,dd_man=?,fif=?,fspman=?,remark=? where id = ?";
		dbManager.update(sql,new Object[] {dept,role,price_min,price_max,dd_man,fif,fspman,remark,id});
		
		return this.success();
		
	}
	
	public Map<String,Object> del(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "delete from cgsp where id = ?";
		dbManager.update(sql,new Object[] {id});
		return this.success();
	}

}
