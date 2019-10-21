package com.tntxia.oa.admin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.admin.entity.ZTreeNode;
import com.tntxia.oa.admin.service.DictService;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class OrganizationAction extends BaseAction{
	
	private DBManager dbManager = this.getDBManager("oa");
	
	private DictService dictService = new DictService();
	
	@SuppressWarnings("rawtypes")
	private List list() throws Exception{
		String sql = "select * from department";
		List list = dbManager.queryForList(sql, true);
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public List listAll(WebRuntime runtime) throws Exception{
		String sql = "select * from department";
		return dbManager.queryForList(sql, true);
	}
	
	public Map<String, Object> add(WebRuntime runtime) throws Exception{
		
		String parentId = runtime.getParam("parentId");
		String name = runtime.getParam("name");
		String dept_code = runtime.getParam("code");
		String remark = runtime.getParam("remark");
		String leader = runtime.getParam("leader");
		
		String sql = "insert into department(departname,dept_code,remark,leader,parent_id) values(?,?,?,?,?)";
		dbManager.update(sql, new Object[]{name,dept_code,remark,leader,parentId});
		
		return this.success();
		
	}
	
	public Map<String, Object> update(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		String name = runtime.getParam("departname");
		String leader = runtime.getParam("leader");
		String remark = runtime.getParam("remark");
		
		String sql = "update department set departname = ? , leader=?, remark=? where id=?";
		dbManager.update(sql, new Object[]{name,leader, remark,id});
		
		return this.success();
		
	}
	
	public Map<String, Object> delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String sql = "delete from department where id = ?";
		dbManager.update(sql, new Object[]{id});
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public List getDepartmentTypeList(WebRuntime runtime) throws Exception {
		return dictService.getDepartmentTypeList();
	}
	
	@SuppressWarnings("rawtypes")
	public List<ZTreeNode> tree(WebRuntime runtime) throws Exception {
		
		List<ZTreeNode> res = new ArrayList<ZTreeNode>();
		List list = this.list();
		
		for(int i=0;i<list.size();i++) {
			Map<String,Object> map = (Map<String,Object>) list.get(i);
			ZTreeNode groupNode = new ZTreeNode();
			groupNode.setId(String.valueOf((Integer) map.get("id")));
			groupNode.setPid(String.valueOf((Integer) map.get("parent_id")));
			groupNode.setName((String)map.get("departname"));
			groupNode.setOpen(true);
			res.add(groupNode);
		}
		return res;
	}
	
	private Integer getParentId(String id) throws Exception {
		String sql = "select parent_id from department where id = ?";
		return dbManager.queryForInt(sql, new Object[] {id});
	}
	
	public Map<String,Object> move(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("sourceId");
		String targetId = runtime.getParam("targetId");
		String location = runtime.getParam("location");
		
		String sql = "update department set parent_id = ? where id = ?";
		if(location.equals("inner")) {
			dbManager.update(sql, new Object[] {targetId,id});
		}else {
			Integer parentId = this.getParentId(targetId);
			dbManager.update(sql, new Object[] {parentId,id});
		}
		return this.success();	
	}
	
	/**
	 * 部门详情
	 * @param runtime
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> detail(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "select * from department where id = ?";
		return dbManager.queryForMap(sql,new Object[] {id}, true);
	}

}
