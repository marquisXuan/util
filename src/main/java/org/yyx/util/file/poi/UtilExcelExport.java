package org.yyx.util.file.poi;


import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.yyx.exception.file.FileException;
import org.yyx.util.date.UtilDate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 导出成Excel
 * <p>
 * create by 叶云轩 at 2017/11/17 - 15:55
 * contact by tdg_yyx@foxmail.com
 */
public class UtilExcelExport {

    /**
     * 导出到Excel表方法
     * 若实体中包含序列化字段，请将其更改为serialVersionUID否则将输出该字段
     *
     * @param excelHeaders 表头 可为空 若为空则为实体类中文意义
     * @param excelBody    表中的数据
     * @param sheetName    工作薄名 若为空 则默认为WookBook
     * @param clazz        实体类类型
     * @return 生成的XSSFWorkBook对象, 可以以流的形式输出
     */
    public static XSSFWorkbook exportExcel(List<String> excelHeaders
            , Collection excelBody, String sheetName, Class clazz) {
        // 创建一个workBook
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        if (sheetName == null) {
            sheetName = "WorkBook";
        }
        // region 样式设定
        // 创建一个名为 sheetName的工作薄
        XSSFSheet sheet = xssfWorkbook.createSheet(sheetName);
        // 设置表头风格
        sheet.setDefaultColumnWidth(25); // 宽度25
        XSSFCellStyle headerRowStyle = xssfWorkbook.createCellStyle();
        headerRowStyle.setFillBackgroundColor(IndexedColors.AQUA.index); // 背景色
        headerRowStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerRowStyle.setAlignment(HorizontalAlignment.CENTER);    // 居中显示
        XSSFFont font = xssfWorkbook.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setFontName("黑体");
        font.setBold(true);
        headerRowStyle.setFont(font);
        // endregion
        // region 封装表头
        // 获取列数
        int rowColumnCount;
        if (excelHeaders == null || excelHeaders.size() == 0) {
            Field[] declaredFields = clazz.getDeclaredFields();
            rowColumnCount = declaredFields.length;
            final List<String> finalExcelHeaders = new ArrayList<>();
            new Thread(() -> {
                for (Field declaredField : declaredFields) {
                    String name = declaredField.getName();
                    StringBuilder sb = new StringBuilder();
                    char[] chars = name.toCharArray();
                    for (char aChar : chars) {
                        // 大写
                        if (aChar >= 65 && aChar <= 90) {
                            sb.append(aChar);
                        } else {
                            sb.append(aChar);
                        }
                    }
                    String s = sb.toString();
                    finalExcelHeaders.add(s);
                }
            }).start();
            excelHeaders = finalExcelHeaders;
        } else {
            rowColumnCount = excelHeaders.size();
        }
        // endregion

        // region 填充表头
        // 设置首行的列值
        XSSFRow header = sheet.createRow(0);
        for (int i = 0; i < rowColumnCount; ++i) {
            XSSFCell cell = header.createCell(i);
            cell.setCellValue(excelHeaders.get(i));
            cell.setCellStyle(headerRowStyle);
        }
        // endregion

        // region 填充数据区
        // 获取数据行
        XSSFCellStyle dataRowStyle = xssfWorkbook.createCellStyle();
        dataRowStyle.setAlignment(HorizontalAlignment.CENTER);
        Iterator iterator = excelBody.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            ++index;
            // 填写行
            XSSFRow row = sheet.createRow(index);
            Object next = iterator.next();
            Field[] declaredFields = next.getClass().getDeclaredFields();
//            new Thread(() -> {
            int j = 0;
            for (int i = 0; i < declaredFields.length; ++i) {
                XSSFCell cell = row.createCell(j);
                j++;
                Field declaredField = declaredFields[i];
                String name = declaredField.getName();
                // 排除序列化字段
                if ("serialVersionUID".equals(name)) {
                    j--;
                    continue;
                }
                String getMethodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Class<?> nextClass = next.getClass();
                Method method = null;
                try {
                    method = nextClass.getMethod(getMethodName);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                Object invoke = null;
                try {
                    if (method != null) {
                        invoke = method.invoke(next);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                String value = null;
                if (invoke != null) {
                    if (invoke instanceof Date) {
                        value = UtilDate.javaUtilDateToString((Date) invoke, "yyyy年MM月dd日 HH:mm:SSS");
                    } else {
                        value = invoke.toString();
                    }
                }
                cell.setCellStyle(dataRowStyle);
                cell.setCellValue(value);
            }
//            }).start();
        }
        // endregion
        return xssfWorkbook;
    }

    /**
     * 导出到Excel表方法
     *
     * @param excelHeaders 表头 可为空 若为空则为实体类中文意义
     * @param excelBody    表中的数据
     * @param sheetName    工作薄名 若为空 则默认为WookBook
     * @param clazz        实体类类型
     * @param fileName     完成的文件名 （文件全路径 + 文件名）
     * @return 生成的文件名
     * @throws FileException 文件找不到异常
     */
    public static String exportExcelFile(List<String> excelHeaders, Collection excelBody, String sheetName, String fileName, Class clazz) {
        XSSFWorkbook xssfWorkbook = exportExcel(excelHeaders, excelBody, sheetName, clazz);
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new FileException("文件找不到异常，文件名为：" + fileName);
        }
        try {
            xssfWorkbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

}
