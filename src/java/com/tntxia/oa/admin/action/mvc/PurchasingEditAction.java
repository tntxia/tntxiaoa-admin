package com.tntxia.oa.admin.action.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.oa.admin.PurchasingStatus;

import com.tntxia.web.mvc.MVCAction;

public class PurchasingEditAction extends MVCAction {
	
	private HttpTransfer trans = new HttpTransfer();
	
	private List<PurchasingStatus> statusList = new ArrayList<PurchasingStatus>();
	
	@Override
	public void init(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String id = request.getParameter("id");
		trans.setHost("localhost");
		trans.setPort(8080);
		trans.setContextPath("purchasingcenter");
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("method", "detail");
		Map<String,Object> map = trans.getMap("purchasing", params);
		this.setToPage("order/purchasing-edit.ftl");
		this.putAll(map);
		
		String status = (String) map.get("l_spqk");
		statusList.add(new PurchasingStatus("", "请选择"));
		statusList.add(new PurchasingStatus("草拟", "草拟"));
		statusList.add(new PurchasingStatus("待审批", "待审批"));
		statusList.add(new PurchasingStatus("已审批", "已审批"));
		statusList.add(new PurchasingStatus("已入库", "已入库"));
		
		this.put("status", status);
		this.put("statusList", statusList);
		
		
	}
	
	

}
