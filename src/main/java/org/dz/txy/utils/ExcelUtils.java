package org.dz.txy.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
    /**
     * 根据文件格式创建Workbook对象
     *
     * @param in
     * @param fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream in, String fileName) throws Exception {
        Workbook workbook = null;
        // 获取文件类型
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(fileType);
        switch (fileType) {
            case ".xls":
                workbook = new HSSFWorkbook(in);
                break;
            case ".xlsx":
                workbook = new XSSFWorkbook(in);
                break;
            default:
                throw new Exception("请上传Excel文件");
        }
        return workbook;
    }

    /**
     * @param in        输入流
     * @param fileName  文件名
     * @param dataRow   数据开始行（从0开始
     * @param dataCol   数据开始列（从0开始
     * @param ignoreCol 忽略的列（从0开始
     * @param title     列对应的字段名，为map的key
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> parsExcel(InputStream in, String fileName, int dataRow, int dataCol, int[] ignoreCol, String[] title) throws Exception {
        Workbook workbook = getWorkbook(in, fileName);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (null == workbook) {
            throw new Exception("创建Excel工作簿为空");
        }
        // 创建页对象
        Sheet sheet = null;

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sh = workbook.getSheetAt(i);
            parsSheet(sh, list, dataRow, dataCol, ignoreCol, title);
        }
        return list;
    }


    public static List<String[]> parsExcel(InputStream in, String fileName, int dataRow, int dataCol, int[] ignoreCol) throws Exception {
        Workbook workbook = getWorkbook(in, fileName);
        List<String[]> list = new ArrayList<>();
        if (null == workbook) {
            throw new Exception("创建Excel工作簿为空");
        }
        // 创建页对象
        Sheet sheet = null;

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sh = workbook.getSheetAt(i);
            parsSheet(sh, list, dataRow, dataCol, ignoreCol);
        }
        return list;
    }

    public static List<String[]> parsSheet(Sheet sh, List<String[]> list, int dataRow, int dataCol, int[] ignoreCol) throws Exception {
        // 创建行对象
        Row row = null;
        // 创建单元格对象
        Cell cell = null;
        if (sh == null) {
            return list;
        }
        String[] str = null;
        for (int i = dataRow; i < sh.getLastRowNum(); i++) {
            int strCount = 0;
            row = sh.getRow(i);
            str = new String[8];
            for (int j = dataCol; j + 1 < row.getLastCellNum(); j++) {
                if (!isIgnore(ignoreCol,j)) {
                    cell = row.getCell(j);
                    String value = getCellValue(cell);
                    str[strCount] = value;
                    strCount++;
                }
            }
            list.add(str);
        }
        return list;
    }

    /**
     * @param sh
     * @param list      结果集
     * @param dataRow   数据开始行（从0开始
     * @param dataCol   数据开始列（从0开始
     * @param ignoreCol 忽略的列（从0开始
     * @param title     列对应的字段名，为map的key
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> parsSheet(Sheet sh, List<Map<String, Object>> list, int dataRow, int dataCol, int[] ignoreCol, String[] title) throws Exception {
        // 创建行对象
        Row row = null;
        // 创建单元格对象
        Cell cell = null;
        if (sh == null) {
            return list;
        }
        Map<String, Object> map = null;
        // getLastRowNum 返回最后一行索引
        for (int i = dataRow; i < sh.getLastRowNum(); i++) {
            int titleCount = 0;
            row = sh.getRow(i);
            map = new HashMap<>();
            // getLastCellNum为实际单元格数
            for (int j = dataCol; j + 1 < row.getLastCellNum(); j++) {
               if (!isIgnore(ignoreCol,j)) {
                   cell = row.getCell(j);
                   String value = getCellValue(cell);
                   map.put(title[titleCount], value);
                   titleCount++;
               }
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 获取单元格值（只支持数字和字符型）
     *
     * @param cell
     * @return
     * @throws Exception
     */
    public static String getCellValue(Cell cell) throws Exception {
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BLANK:
                return "";
            default:
                throw new Exception("不是数字或者字符串类型");
        }
    }

    public static boolean isIgnore(int[] ignoreCol,int i) {
        for (int k = 0; k < ignoreCol.length; k++) {
            if (ignoreCol[k] == i)
                return true;
        }
        return false;
    }
}
