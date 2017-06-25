package com.tntxia.oa.admin.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.oa.admin.service.StaticService;

public class StaticAction {
	
	private StaticService service = new StaticService();
	
	public Map<String, Object> execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String path = request.getParameter("path");
		service.execute(path);
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;
	}
	
	public Map<String,Object> getStaticInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		return service.getStaticInfo();
	}
	
}
