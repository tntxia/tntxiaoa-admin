package com.tntxia.oa.admin.action.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.web.mvc.MVCAction;

public class AppManageAction extends MVCAction{
	
	
	@Override
	public void init(HttpServletRequest arg0, HttpServletResponse arg1)
			throws Exception {
		
		this.setToPage("app.ftl");
		
	}

}
