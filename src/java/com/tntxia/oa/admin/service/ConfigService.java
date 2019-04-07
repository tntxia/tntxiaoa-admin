package com.tntxia.oa.admin.service;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.tntxia.httptrans.HttpTransfer;

public class ConfigService{
	
	public Map<String,Object> getConfig(String appId) throws Exception{
		
		ResourceBundle rb = ResourceBundle.getBundle("oa_admin");
		HttpTransfer transfer = new HttpTransfer();
		transfer.setHost(rb.getString("oa_center_server"));
		transfer.setPort(rb.getString("oa_center_port"));
		transfer.setContextPath(rb.getString("oa_center_url"));
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("appId", appId);
		return transfer.getMap("app!detail", param);
	
	}

}
