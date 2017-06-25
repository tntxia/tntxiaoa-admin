package com.tntxia.oa.admin.action.mvc;

import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;

/**
 * 
 * 显示图片
 * 
 * @author tntxia
 * 
 */
public class ShowImageAction extends BaseAction {

	private DBManager dbManager = this.getDBManager("admin");

	private String getHeaderImage(String app) throws Exception {
		String sql = "select top 1 real_path from tntxiaoa_design_header where app=? order by id desc";
		return dbManager.getString(sql, new Object[] { app });
	}

	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String app = (String) request.getSession().getAttribute("app");
		String realPath = this.getHeaderImage(app);
		FileInputStream fis = null;
		OutputStream outStream = null;
		try {

			fis = new FileInputStream(realPath);
			int i = fis.available(); // 得到文件大小
			byte data[] = new byte[i];
			fis.read(data); // 读数据
			response.setContentType("image/*"); // 设置返回的文件类型
			outStream = response.getOutputStream(); // 得到向客户端输出二进制数据的对象
			outStream.write(data); // 输出数据
			outStream.flush();

		} catch (Exception ex) {

		} finally {
			if (fis != null) {
				fis.close();
			}
			if (outStream != null) {
				outStream.close();
			}
		}

	}

}
