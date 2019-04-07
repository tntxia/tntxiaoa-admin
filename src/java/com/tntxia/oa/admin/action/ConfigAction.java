package com.tntxia.oa.admin.action;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.oa.admin.service.ConfigService;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class ConfigAction extends BaseAction {
	
	private ConfigService service = new ConfigService();
	
	public Map<String,Object> save(WebRuntime runtime) throws Exception{
		
		String appId = runtime.getSessionStr("app");
		String path = runtime.getParam("path");
		String path_upload = runtime.getParam("path_upload");
		String company_name = runtime.getParam("company_name");
		String company_name_en = runtime.getParam("company_name_en");
		
		ResourceBundle rb = ResourceBundle.getBundle("oa_admin");
		HttpTransfer transfer = new HttpTransfer();
		transfer.setHost(rb.getString("oa_center_server"));
		transfer.setPort(rb.getString("oa_center_port"));
		transfer.setContextPath(rb.getString("oa_center_url"));
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("appId", appId);
		param.put("path", path);
		param.put("path_upload", path_upload);
		param.put("company_name", company_name);
		param.put("company_name_en", company_name_en);
		
		return transfer.getMap("app!updatePath", param);
		
	}
	
	public Map<String,Object> detail(WebRuntime runtime) throws Exception{
		String appId = runtime.getSessionStr("app");
		return service.getConfig(appId);
	}

}
