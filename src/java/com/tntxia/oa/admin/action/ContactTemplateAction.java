package com.tntxia.oa.admin.action;

import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class ContactTemplateAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager("oa");
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		
		PageBean pageBean = runtime.getPageBean(10);
		List list = dbManager.queryForList("select * from ht_mb", true);
		int totalAmount = dbManager.queryForInt("select count(*) from ht_mb");
		return this.getPagingResult(list, pageBean, totalAmount);
		
	}
	
	public Map<String,Object> update(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String name = runtime.getParam("q_name");
		String q_topic = runtime.getParam("q_topic");
		String company = runtime.getParam("q_company");
		String q_man = runtime.getParam("q_man");
		String q_tel = runtime.getParam("q_tel");
		String q_fax = runtime.getParam("q_fax");
		String q_email = runtime.getParam("q_email");
		String q_net = runtime.getParam("q_net");
		String q_post = runtime.getParam("q_post");
		String q_number = runtime.getParam("q_number");
		String tk = runtime.getParam("q_tk");
		String q_addr = runtime.getParam("q_addr");
		String remark = runtime.getParam("remark");
		String template_type = runtime.getParam("template_type");
		String sql = "update ht_mb set q_name = ?,template_type=?,q_company=?,q_tk=?,q_addr=?,q_topic=?,q_man=?,q_tel=?,q_fax=?,q_email=?,q_net=?,q_post=?,q_number=?,remark=? where id = ?";
		dbManager.update(sql, new Object[]{name,template_type, company,tk,q_addr,q_topic,q_man,q_tel,q_fax,q_email,q_net,q_post,q_number,remark,id});
		return this.success();
	}
	
	public Map<String,Object> create(WebRuntime runtime) throws Exception{
		String name = runtime.getParam("q_name");
		String q_topic = runtime.getParam("q_topic");
		String company = runtime.getParam("q_company");
		String q_man = runtime.getParam("q_man");
		String q_tel = runtime.getParam("q_tel");
		String q_fax = runtime.getParam("q_fax");
		String q_email = runtime.getParam("q_email");
		String q_net = runtime.getParam("q_net");
		String q_post = runtime.getParam("q_post");
		String q_number = runtime.getParam("q_number");
		String tk = runtime.getParam("q_tk");
		String q_addr = runtime.getParam("q_addr");
		String remark = runtime.getParam("remark");
		String sql = "insert into ht_mb(q_name,q_company,q_tk,q_addr,q_topic,q_man,q_tel,q_fax,q_email,q_net,q_post,q_number,remark,q_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
		dbManager.update(sql, new Object[]{name,company,tk,q_addr,q_topic,q_man,q_tel,q_fax,q_email,q_net,q_post,q_number,remark});
		return this.success();
	}

}
