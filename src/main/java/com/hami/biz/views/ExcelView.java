package com.hami.biz.views;

import com.hami.biz.sample.dao.CustomerDAO;import com.hami.biz.sample.model.Customer;import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import com.hami.biz.sample.dao.CustomerDAO;
import com.hami.biz.sample.model.Customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

/**
 * <pre>
 * <li>Program Name : ExcelView
 * <li>Description  :
 * <li>History      : 2017. 11. 26.
 * </pre>
 *
 * @author HHG
 */
@Component("excelView")
public class ExcelView extends AbstractXlsView{
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");

        // 워크시트 생성
        Sheet sheet = workbook.createSheet();
        // 행 생성
        Row row = sheet.createRow(0);
        // 쎌 생성
        Cell cell;

        // 헤더 정보 구성
        cell = row.createCell(0);
        cell.setCellValue("ID");

        cell = row.createCell(1);
        cell.setCellValue("FirstName");

        cell = row.createCell(2);
        cell.setCellValue("LastName");

        cell = row.createCell(3);
        cell.setCellValue("Email");

        cell = row.createCell(4);
        cell.setCellValue("Moblie");

        // 리스트의 size 만큼 row를 생성
        ArrayList<Customer> list;
        CustomerDAO cdo = new CustomerDAO();
        list = (ArrayList<Customer>) cdo.list();
        Customer vo;

        for(int rowIdx=0; rowIdx < list.size(); rowIdx++) {
            vo = list.get(rowIdx);

            // 행 생성
            row = sheet.createRow(rowIdx+1);

            cell = row.createCell(0);
            cell.setCellValue(vo.getId());

            cell = row.createCell(1);
            cell.setCellValue(vo.getFirstName());

            cell = row.createCell(2);
            cell.setCellValue(vo.getLastName());

            cell = row.createCell(3);
            cell.setCellValue(vo.getEmail());

            cell = row.createCell(4);
            cell.setCellValue(vo.getMobile());

        }
    }
}
