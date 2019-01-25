package com.deliver.util;

/**
 * Created by pdl on 2018/11/19.
 */
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.deliver.entity.HumanInfo;
import com.deliver.mapbody.HumanExcel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class POIReadExcelTool {

    public static void main(String[] args) throws Exception {
        String path = "D:/student.xls";
        List<HumanExcel> list = readXls(path);

        for(HumanExcel humanExcel : list){
            System.out.println(humanExcel.getHumanName());
            System.out.println(humanExcel.getSchoolID());
            System.out.println(humanExcel.getGradeNum());
            System.out.println(humanExcel.getClassNum());
            System.out.println(humanExcel.getTel());
        }
    }

    public static List<HumanExcel> readXls(String path) throws Exception {
        InputStream is = new FileInputStream(path);

        XSSFWorkbook excel = new XSSFWorkbook(is);
        HumanExcel humanExcel = null;
        List<HumanExcel> list = new ArrayList<HumanExcel>();

        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < excel.getNumberOfSheets(); numSheet++) {
            XSSFSheet sheet = excel.getSheetAt(numSheet);
            if (sheet == null)
                continue;
            // 循环行Row
            for (int rowNum = 1; rowNum < sheet.getLastRowNum()+1; rowNum++) {
                XSSFRow row = sheet.getRow(rowNum);
                if (row == null)
                    continue;
                humanExcel = new HumanExcel();
                // 循环列Cell
                // 0学号 1姓名 2年龄 3生日
                /*System.out.println((int)cell0.getNumericCellValue());
                System.out.println(cell1.getStringCellValue());
                System.out.println((int)cell2.getNumericCellValue());
                System.out.println(cell3.getStringCellValue());*/
                /**console:
                 *         1
                 张三
                 16
                 1997-03-12
                 2
                 李四
                 17
                 1996-08-12
                 */
                XSSFCell cell0 = row.getCell(0);
                if (cell0 == null)
                    continue;
                humanExcel.setHumanName(cell0.getStringCellValue());
                XSSFCell cell1 = row.getCell(1);
                if (cell1 == null)
                    continue;
                humanExcel.setSchoolID((int)cell1.getNumericCellValue());
                XSSFCell cell2 = row.getCell(2);
                if (cell2 == null)
                    continue;
                humanExcel.setGradeNum((int)cell2.getNumericCellValue());

                XSSFCell cell3 = row.getCell(3);
                if (cell3 == null)
                    continue;
                humanExcel.setClassNum((int)cell3.getNumericCellValue());
                XSSFCell cell4 = row.getCell(4);
                /*if (cell4 == null)
                    continue;*/
                if (cell4 != null)
                    humanExcel.setParentName(cell4.getStringCellValue());
                XSSFCell cell5 = row.getCell(5);
                /*if (cell5 == null)
                    continue;*/
                if (cell5 != null)
                    humanExcel.setTel(cell5.getRawValue());
                XSSFCell cell6 = row.getCell(6);
                /*if (cell6 == null)
                    continue;*/
                if (cell6 != null)
                    humanExcel.setRel(cell6.getStringCellValue());
                list.add(humanExcel);
            }
        }

        return list;
    }

    @SuppressWarnings("unused")
    private static String getValue(HSSFCell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型 值
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            //返回数值类型的值
            return String.valueOf(cell.getNumericCellValue());
        } else {
            //返回字符串类型的值
            return cell.getStringCellValue();
        }
    }
}
