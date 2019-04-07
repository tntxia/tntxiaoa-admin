package com.tntxia.oa.admin.action;


import java.util.Map;
import java.util.ResourceBundle;

import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class DesignAction extends BaseAction {

	public Map<String,Object> getLogoUrl(WebRuntime runtime) throws Exception{
		
		ResourceBundle rb = ResourceBundle.getBundle("oa_admin");
		String appId = runtime.getSessionStr("app");
		String server = rb.getString("oa_center_server");
		String oa_center_port = rb.getString("oa_center_port");
		String oa_center_url = rb.getString("oa_center_url");
		
		return this.success("url", "http://"+server+":"+oa_center_port+"/"+oa_center_url+"/design!showLogoImage.do?appId="+appId+"&_="+System.currentTimeMillis());
		
	}

}
