package com.tntxia.oa.admin.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;

public class OrderAction extends BaseAction{
	
	private HttpTransfer trans = new HttpTransfer();
	
	public Map<String,Object> listPurchasing(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		PageBean pageBean = this.getPageBean(request);
		String number = request.getParameter("number");
		trans.setHost("localhost");
		trans.setPort(8080);
		trans.setContextPath("purchasingcenter");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pageBean", pageBean);
		params.put("number", number);
		params.put("method", "list");
		
		return trans.getMap("purchasing",params );
		
	}

}
