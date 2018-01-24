package org.yyx.util.file.poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.constant.FileConstant;
import org.yyx.exception.ParamException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel 文件导入工具类
 * create by 叶云轩 at 2017/11/17 - 10:12
 * contact by tdg_yyx@foxmail.com
 */
public class UtilExcelImport {

    private UtilExcelImport(){}

    /**
     * ExcelImportUtil 日志控制器
     * Create by 叶云轩 at 2018/1/24 19:35
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilExcelImport.class);

    /**
     * 导入Excel表格并生成对应实体类的集合
     * <b>[强制条件] Excel表头必须与实体类的属性名称一致</b>
     * 注：
     * 1.由于效率问题,当前暂支持一个工作薄的导入
     * 2.表头必须以 (中文名-实体对象属性名)为头。否则导入失败
     *
     * @param file  Excel表格文件 支持.xls .xlsx
     * @param clazz 映射的实体类类型
     * @return 实体集合
     * @throws IOException 文件读取异常
     */
    public static <T> List<T> importExcel(File file, Class clazz) throws IOException {
        if (file == null) {
            throw new ParamException("文件为空");
        }
        String fileName = file.getName();
        if (fileName.endsWith(FileConstant.SUFFIX_XLSX)) {
            return importExcelXlsx(new FileInputStream(file), clazz);
        } else if (fileName.endsWith(FileConstant.SUFFIX_XLS)) {
            FileInputStream inputStream = new FileInputStream(file);
            return importExcelXls(inputStream, clazz);
        } else return null;
    }

    /**
     * 导入Xlsx的Excel文件
     *
     * @param fileInputStream 文件输入流
     * @param clazz           映射的实体类类型
     * @return 实体集合
     * @throws IOException
     */
    public static <T> List<T> importExcelXlsx(InputStream fileInputStream, Class clazz) throws IOException {
        List<T> list = new ArrayList<>();
        // XSSFWorkbook 加载Excel文件
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
        // 获取文件中第一个工作薄
        XSSFSheet firstSheet = xssfWorkbook.getSheetAt(0);
        // 获取工作薄中总行数
        int rows = firstSheet.getPhysicalNumberOfRows();
        // 线程问题尚未考虑
        new Thread(((Runnable) () -> {
            // 从第二行开始计算数据
            for (int i = 1; i < rows; i++) {
                // 获取首行数据
                XSSFRow firstSheetRow = firstSheet.getRow(0);
                // 获取第i行数据
                XSSFRow row = firstSheet.getRow(i);
                // 获取第i行的总列数
                int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                // 要封装成的结果 实体类
                T o = null;
                try {
                    o = (T) clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < physicalNumberOfCells; j++) {
                    // 首行单元格
                    XSSFCell firstSheetRowCell = firstSheetRow.getCell(j);
                    // 第i行第j列的单元格
                    XSSFCell cell = row.getCell(j);
                    // 类中属性
                    Field[] fields = clazz.getDeclaredFields();
                    // 设置允许访问私有属性
                    Field.setAccessible(fields, true);
                    // 通过反射设置对象属性
                    reflexObject(fields, firstSheetRowCell, cell, o);
                }
                list.add(o);
            }
        })).start();
        return list;
    }

    /**
     * 导入后缀名为xls的Excel文件
     *
     * @param fileInputStream 文件输入流
     * @param clazz           要导成的实体类型
     * @param <T>             泛型
     * @return 数据集合
     * @throws IOException
     */
    public static <T> List<T> importExcelXls(InputStream fileInputStream, Class clazz) throws IOException {
        List<T> list = new ArrayList<>();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet firstSheet = hssfWorkbook.getSheetAt(0);
        int physicalNumberOfRows = firstSheet.getPhysicalNumberOfRows();
        // 从第二行开始计算数据
        for (int i = 1; i < physicalNumberOfRows; i++) {
            // 获取首行数据
            HSSFRow firstSheetRow = firstSheet.getRow(0);
            // 获取第i行数据
            HSSFRow row = firstSheet.getRow(i);
            // 获取第i行的总列数
            int physicalNumberOfCells = row.getPhysicalNumberOfCells();
            // 要封装成的结果 实体类
            T o = null;
            try {
                o = (T) clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            for (int j = 0; j < physicalNumberOfCells; j++) {
                // 首行单元格
                HSSFCell firstSheetRowCell = firstSheetRow.getCell(j);
                // 第i行第j列的单元格
                HSSFCell cell = row.getCell(j);
                // 类中属性
                Field[] fields = clazz.getDeclaredFields();
                // 设置允许访问私有属性
                Field.setAccessible(fields, true);
                // 通过反射设置对象属性
                reflexObject(fields, firstSheetRowCell, cell, o);
            }
            list.add(o);
        }
        return list;
    }

    /**
     * 反射设置对象属性值
     *
     * @param fields            对象的属性数组
     * @param firstSheetRowCell 首行单元格（表头的内容）
     * @param cell              除表头外的数据
     * @param o                 待设置值的对象
     */
    private static void reflexObject(Field[] fields, Cell firstSheetRowCell, Cell cell, Object o) {
        if (firstSheetRowCell != null && "".equals(firstSheetRowCell.toString())) {
            return;
        }
        for (int k = 0; k < fields.length; k++) {
            Field field = fields[k];
            String name = field.getName();
            String s = firstSheetRowCell.toString().split("-")[1];
            // Excel列与属性名对比
            if (name.equals(s)) {
                // 反射获取属性类型
                Class<?> type = field.getType();
                Object cellValue = CellUtil.getCellValue(cell);
                Class<?> valueClass = cellValue.getClass();
                Object cast;
                if (type.getName().endsWith(valueClass.getName())) {
                    // 类型相同
                    cast = type.cast(cellValue);
                } else {
                    // 单元格数据强转成与属性相同的类型
                    if ("java.lang.Integer".equals(type.getName())) {
                        if ("java.lang.Double".equals(cellValue.getClass().getName())) {
                            Double d = (Double) cellValue;
                            cast = d.intValue();
                        } else {
                            // 有需求再变。现在不想通用的
                            cast = null;
                        }
                    } else if ("java.lang.Byte".equals(type.getName())) {
                        if ("java.lang.Double".equals(cellValue.getClass().getName())) {
                            Double d = (Double) cellValue;
                            cast = d.byteValue();
                        } else {
                            // 有需求再变。现在不想通用的
                            cast = null;
                        }
                    } else {
                        cast = null;
                    }
                }
                // 给对象赋值
                try {
                    field.set(o, cast);
                } catch (IllegalAccessException e) {
                    LOGGER.error("[赋值失败] {}", e.getMessage());
                }
                break;
            }
        }
    }
}
