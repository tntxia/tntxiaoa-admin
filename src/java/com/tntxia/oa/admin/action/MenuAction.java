package com.tntxia.oa.admin.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.excel.ExcelData;
import com.tntxia.excel.ExcelRow;
import com.tntxia.excel.ExcelUtils;
import com.tntxia.httptrans.HttpTransfer;
import com.tntxia.httptrans.HttpTransferFactory;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.entity.MultipartForm;

public class MenuAction extends BaseAction {

	private DBManager dbManager = this.getDBManager("oa_back");

	public Map<String, Object> update(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String name = runtime.getParam("name");
		String key_name = runtime.getParam("key_name");
		String url = runtime.getParam("url");
		String order_no = runtime.getParam("order_no");

		String sql = "update menu set name = ? , url=?,order_no=?,key_name=? where id=?";
		dbManager.update(sql, new Object[] { name, url, order_no,key_name, id });

		return this.success();

	}

	public Map<String, Object> add(WebRuntime runtime) throws Exception {

		String name = runtime.getParam("name");
		String url = runtime.getParam("url");
		String order_no = runtime.getParam("order_no");

		String sql = "insert into menu (name,url,order_no) values(?,?,?)";
		dbManager.update(sql, new Object[] { name, url, order_no });

		return this.success();

	}

	public Map<String, Object> delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String id = request.getParameter("id");
		String sql = "delete from menu where id = ?";
		dbManager.update(sql, new Object[] { id });

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> list(WebRuntime runtime) throws Exception {

		String sql = "select * from menu order by order_no";
		List list = dbManager.queryForList(sql, true);

		sql = "select count(*) from menu";
		int count = dbManager.getCount(sql);

		return this.getPagingResult(list, runtime, count);
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Object> export(WebRuntime runtime) throws Exception {
		String sql = "select * from menu";
		List list = dbManager.queryForList(sql, true);
		String[] cols = new String[] { "序号", "名称", "URL", "排序" };

		ExcelData data = new ExcelData();

		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			ExcelRow row = new ExcelRow();
			row.add(i + 1);
			row.add(map.get("name"));
			row.add(map.get("url"));
			row.add(map.get("order_no"));
			data.add(row);
		}

		String excelPath = ExcelUtils.makeCommonExcel("菜单", cols, data);

		HttpTransfer trans = HttpTransferFactory.generate("file_center");
		Map<String, Object> transResult = trans.uploadFile("file!upload", excelPath, new HashMap<String, String>());
		String uuid = (String) transResult.get("uuid");
		return this.success("uuid", uuid);

	}

	private Object getCellValue(HSSFRow row, int i) {
		HSSFCell cell = row.getCell(i);
		if (cell == null) {
			return null;
		}
		if (cell.getCellTypeEnum() == CellType.NUMERIC) {
			return cell.getNumericCellValue();
		}
		return cell.getStringCellValue();
	}

	public Map<String, Object> importMenu(WebRuntime runtime) throws Exception {
		MultipartForm form = runtime.getMultipartForm();
		List<FileItem> fileItemList = form.getFileItemList();
		String uploadTempPath = "D:\\temp_upload";
		File uploadTempDir = new File(uploadTempPath);
		uploadTempDir.mkdirs();
		for (FileItem item : fileItemList) {

			String fileName = item.getName();
			String fileExt = FilenameUtils.getExtension(fileName);
			String uuid = UUID.randomUUID().toString();
			String savePath = uploadTempDir.getAbsolutePath() + File.separator + uuid + "." + fileExt;
			File file = new File(savePath);
			item.write(file);

			HSSFWorkbook workbook = null;

			workbook = new HSSFWorkbook(new FileInputStream(savePath));
			HSSFSheet sheet = workbook.getSheetAt(0);
			int amount = sheet.getLastRowNum();

			for (int r = 2; r <= amount; r++) {

				HSSFRow row = sheet.getRow(r);

				Object name = this.getCellValue(row, 1);
				Object url = this.getCellValue(row, 2);
				Object orderNo = ((Double) this.getCellValue(row, 3)).intValue();

				String sql = "insert into menu(name,url,order_no) values(?,?,?)";

				dbManager.executeUpdate(sql, new Object[] { name, url, orderNo });

			}
			workbook.close();

		}

		return this.success();

	}

	public Map<String, Object> detail(WebRuntime runtime) throws Exception {

		String id = runtime.getParam("id");

		String sql = "select * from menu where id = ?";
		return dbManager.queryForMap(sql, new Object[] { id }, true);

	}

}
