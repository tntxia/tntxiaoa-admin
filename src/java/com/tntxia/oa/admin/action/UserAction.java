package com.tntxia.oa.admin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.admin.entity.ZTreeNode;
import com.tntxia.oa.admin.service.DepartmentService;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class UserAction extends BaseAction{
	
	private DBManager dbManager = getDBManager("oa");
	
	private DBManager dbManagerBack = getDBManager("oa_back");
	
	private DepartmentService departmentService = new DepartmentService();
	
	public Map<String,Object> detail(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "select * from username where nameid = ?";
		return dbManager.queryForMap(sql,new Object[] {id},true);
	}
	
	/**
	 * 用户查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		
		String sql = "select top " + runtime.getPageBean().getTop() + " * from username";
		String sqlWhere = " where 1=1 ";
		String sqlOrderBy = " order by nameid desc";
		
		List<Object> params = new ArrayList<Object>();
		
		String deptId = runtime.getParam("deptId");
		if (StringUtils.isNotEmpty(deptId)) {
			sqlWhere += " and department_id = ?";
			params.add(deptId);
		}
		String name = runtime.getParam("name");
		if (StringUtils.isNotEmpty(name)) {
			sqlWhere += " and name like ?";
			params.add("%" + name + "%");
		}
		
		List list = dbManager.queryForList(sql + sqlWhere + sqlOrderBy, params, true);
		
		sql = "select count(*) from username ";
		int count = dbManager.getCount(sql + sqlWhere, params);
		
		return this.getPagingResult(list, runtime, count);
	}
	
	/**
	 * 用户查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public List listAll(WebRuntime runtime) throws Exception{
		
		List list = dbManager.queryForList("select * from username", true);
		return list;
	}
	
	public Map<String,Object> add(WebRuntime runtime) throws Exception{
		
		String name = runtime.getParam("name");
		String name_en = runtime.getParam("name_en");
		String password = "888";
		String sex = runtime.getParam("sex");
		
		String restrain_id = runtime.getParam("restrain_id");
		String departmentId = runtime.getParam("department_id");
		String departmentName = departmentService.getDepartmentName(Integer.parseInt(departmentId));
		
		String workj = runtime.getParam("workj");
		String worktel = runtime.getParam("worktel");
		String ipbd = runtime.getParam("ipbd");
		String user_ip = runtime.getParam("user_ip");
		
		String sql = "insert into username(name,name_en,department_name,yjxs,workj,password,sex,restrain_id,department_id,worktel,ipbd,user_ip) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		dbManager.update(sql,new Object[]{name,name_en,departmentName,departmentName,workj,password,sex,restrain_id,departmentId,worktel,ipbd,user_ip});
		
		return this.success();
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> unlock(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String sql = "update username set err = 0 where nameid = ?";
		String id = request.getParameter("id");
		dbManager.update(sql, new Object[]{id});
		return success();
	}
	
	public Map<String,Object> del(WebRuntime runtime) throws Exception{
		String sql = "delete from username where nameid = ?";
		String id = runtime.getParam("id");
		dbManager.update(sql, new Object[]{id});
		return success();
	}
	
	public Map<String,Object> update(WebRuntime runtime) throws Exception{
		
		String name_en = runtime.getParam("name_en");
		String departmentId = runtime.getParam("department_id");
		String departmentName = departmentService.getDepartmentName(Integer.parseInt(departmentId));
		String sex = runtime.getParam("sex");
		String position = runtime.getParam("workj");
		String restrain_id = runtime.getParam("restrain_id");
		String ipbd = runtime.getParam("ipbd");
		String ip = runtime.getParam("user_ip");
		
		String sql = "update username set name_en=?,department_id=?,department_name=?,yjxs=?,workj=?,sex=?,restrain_id=?, ipbd=?, user_ip=? where nameid = ?";
		String id = runtime.getParam("nameid");
		dbManager.update(sql, new Object[]{name_en,departmentId,departmentName,departmentName, position,sex,restrain_id, ipbd, ip, id});
		return success();
	}
	
	public Map<String,Object> updatePassword(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String password = runtime.getParam("password");
		String sql = "update username set password = ? where nameid=?";
		dbManager.update(sql, new Object[] {password,id});
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public List<ZTreeNode> getRoleZTreeNode(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		
		List<ZTreeNode> res = new ArrayList<ZTreeNode>();
		
		String sql = "select * from restrain";
		
		List restrainList = dbManager.queryForList(sql, true);
		
		for(int i=0;i<restrainList.size();i++) {
			Map map = (Map) restrainList.get(i);
			ZTreeNode treeNode = new ZTreeNode();
			treeNode.setId(String.valueOf((Integer)map.get("id")));
			treeNode.setName((String) map.get("restrain_name"));
			res.add(treeNode);
		}
		
		sql = "select * from user_role_rel where user_id = ?";
		
		List list = dbManagerBack.queryForList(sql,new Object[]{id},true);
		
		List<Integer> ids = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			Map item = (Map) list.get(i);
			ids.add((Integer) item.get("role_id"));
		}
		
		for(ZTreeNode node : res) {
			String nodeId = node.getId();
			for(Integer roleId : ids) {
				if(String.valueOf(roleId).equals(nodeId)) {
					node.setChecked(true);
					break;
				}
			}
		}
		
		return res;
	}
	
	public Map<String,Object> setRole(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		String[] roles = runtime.getParam("roles").split(",");
		String sql = "delete from user_role_rel where user_id = ?";
		dbManagerBack.update(sql,new Object[] {id});
		
		sql = "insert into user_role_rel(user_id,role_id) values(?,?)";
		
		for(String role:roles){
			dbManagerBack.update(sql,new Object[]{id,role});
		}
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;
	}

}
