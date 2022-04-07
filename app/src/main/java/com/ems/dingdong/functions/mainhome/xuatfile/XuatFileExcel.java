package com.ems.dingdong.functions.mainhome.xuatfile;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.ems.dingdong.app.ApplicationController;
import com.ems.dingdong.model.CommonObject;
import com.ems.dingdong.model.Item;
import com.ems.dingdong.utiles.Log;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XuatFileExcel extends ApplicationController {
    private File file_path;
    private static String EXCEL_SHEET_NAME = "Sheet1";
    private static Cell cell;
    private static Sheet sheet;

    public void XuatFileExcel(String filename, int column, List<Item> title, List<Item> list, Context mconx) {
        Workbook workbook = new HSSFWorkbook();
        file_path = new File(Environment.getExternalStorageDirectory() + filename);
        cell = null;
        // Cell style for header row
//        CellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
//        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        cellStyle.setAlignment(CellStyle.);
        // New Sheet
        sheet = null;
        sheet = workbook.createSheet(EXCEL_SHEET_NAME);
        sheet.setColumnWidth(0, (15 * 400));
        sheet.setColumnWidth(1, (15 * 400));
        sheet.setColumnWidth(2, (15 * 400));
        sheet.setColumnWidth(3, (15 * 400));
        Row row = sheet.createRow(0);

        for (int i = 0; i < column; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title.get(i).getText());
        }
        fillDataIntoExcel(list, column);

        try {
            if (!file_path.exists()) {
                file_path.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file_path);
            workbook.write(fileOutputStream);
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            Toast.makeText(mconx, "Xuất file thành công " + filename, Toast.LENGTH_SHORT).show();
//            Log.e("thanhkhiem1111", "Writing file" + file_path);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mconx, "Xuất file Thất bại " + filename, Toast.LENGTH_SHORT).show();
//            Log.e("thanhkhiem1111", file_path + "Error writing Exception: ", e);
        }
    }

    private static void fillDataIntoExcel(List<Item> dataList, int column) {
        for (int i = 0; i < dataList.size(); i++) {
            Row rowData = sheet.createRow(i + 1);
            // Create Cells for each row
            cell = rowData.createCell(0);
            cell.setCellValue(dataList.get(i).getStt());
            cell = rowData.createCell(1);
            cell.setCellValue(dataList.get(i).getMatin());
            cell = rowData.createCell(2);
            cell.setCellValue(dataList.get(i).getMabuugui());
            cell = rowData.createCell(3);
            cell.setCellValue(dataList.get(i).getSohoadon());

//            cell = rowData.createCell(1);
//            cell.setCellValue(dataList.get(i).getText());
//
//            cell = rowData.createCell(2);
//            cell.setCellValue(dataList.get(i).getText());

        }
    }
}
