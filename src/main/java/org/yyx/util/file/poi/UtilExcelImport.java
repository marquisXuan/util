package org.yyx.util.file.poi;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.constant.FileConstant;
import org.yyx.exception.FileException;
import org.yyx.exception.ParamException;
import org.yyx.exception.io.StreamCloseException;
import org.yyx.exception.io.StreamException;
import org.yyx.util.date.UtilDate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Excel 文件导入工具类
 * 即从Excel文件中获取数据
 * create by 叶云轩 at 2017/11/17 - 10:12
 * contact by tdg_yyx@foxmail.com
 */
public class UtilExcelImport {

    /**
     * ExcelImportUtil 日志控制器
     * Create by 叶云轩 at 2018/1/24 19:35
     * Concat at tdg_yyx@foxmail.com
     */
    // region ExcelImportUtil 日志控制器
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilExcelImport.class);
    // endregion

    // region 私有构造器
    private UtilExcelImport() {
    }
    // endregion

    /**
     * 从输入流中导入一个Excel文件
     * <b>
     * Excel文件中若有日期格式的单元格，
     * 必须对此单元格的表头添加批注，否则日期将以天数记录
     * </b>
     *
     * @param fileInputStream   Excel文件流
     * @param isNeedTableHeader 是否需要表头 true:需要表头 false:不需要表头
     * @return 文件中所有Sheet数据封装成的Json数据数组。数组以Sheet为单位
     */
    public static String[] importExcel(InputStream fileInputStream, boolean isNeedTableHeader) {
        // 声明一个XSSFWorkbook对象
        XSSFWorkbook xssfWorkbook;
        try {
            // 给XSSFWorkbook赋值
            xssfWorkbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            throw new FileException("文件读取失败");
        }
        // 获取当前工作薄中有多少个工作表
        int numberOfSheets = xssfWorkbook.getNumberOfSheets();
        LOGGER.info("--- [sheet] {}张", numberOfSheets);
        // 创建一个与工作表一样多的数组，用于方法完成后返回
        String[] resultObj = new String[numberOfSheets];
        // 工作表序列
        int sheetNum = 0;
        // 迭代每一张工作表
        Iterator<Sheet> sheetIterator = xssfWorkbook.sheetIterator();
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            // 调用获取工作表中的数据
            List<Object> dataList = getSheetData(sheet, isNeedTableHeader);
//            Sheet sheet = sheetIterator.next();
//            // 工作表名
//            String sheetName = sheet.getSheetName();
//            LOGGER.info("--- [当前工作表名] {}", sheetName);
//            // 物理行数
//            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
//            LOGGER.info("--- [当前工作表的行数为] {}行", physicalNumberOfRows);
//            // 用于保存表单数据的对象集合
//            List<Object> dataList = new ArrayList<>(physicalNumberOfRows);
//            // 表头行
//            Row rowHeader = sheet.getRow(0);
//            // 定义一个 jsonObject 用来接收表头数据
//            JSONObject headJson = new JSONObject();
//            // region 表头行
//            // 表头行
//            Iterator<Cell> iterator = rowHeader.iterator();
//            // 最坏的情况，所有列都是时间列
//            List<Integer> dataColList = new ArrayList<>();
//            int columnNum = 0;
//            while (iterator.hasNext()) {
//                Cell cell = iterator.next();
//                /*
//                 * 如果导入数据中包含日期格式 着重说明Excel文件中对应单元格内添加批注
//                 */
//                Object cellValue = CellUtil.getCellValue(cell);
//                if (cellValue instanceof Date) {
//                    LOGGER.info("--- [日期列] 第{}列", cell.getColumnIndex() + 1);
//                    cellValue = cell.getDateCellValue();
//                    dataColList.add(cell.getColumnIndex());
//                }
//                headJson.put(columnNum + "", cellValue);
//                columnNum++;
//                LOGGER.info("--- [cellValue] {}", cellValue);
//            }
//            // endregion
//            // 返回集合中需要表头
//            if (isNeedTableHeader) {
//                // 添加表头数据到数据中
//                dataList.add(headJson);
//            }
//
//            // region 数据行
//            for (int i = 1; i < physicalNumberOfRows; i++) {
//                JSONObject jsonObject = new JSONObject();
//                // 遍历数据行
//                Row dataRow = sheet.getRow(i);
//                Iterator<Cell> cellIterator = dataRow.iterator();
//                // 列序列
//                columnNum = 0;
//                // 迭代列
//                while (cellIterator.hasNext()) {
//                    // 数据区域
//                    Cell cell = cellIterator.next();
//                    if (dataColList.size() != 0) {
//                        // 日期列迭代
//                        Iterator<Integer> iteratorDataCol = dataColList.iterator();
//                        while (iteratorDataCol.hasNext()) {
//                            Integer next = iteratorDataCol.next();
//                            // 第j列是日期列
//                            if (next == columnNum) {
//                                // 获取单元格的值
//                                jsonObject.put(columnNum + "", cell.getDateCellValue());
//                                // 迭代时移出next
//                                iteratorDataCol.remove();
//                                break;
//                            }
//                        }
//                    } else {
//                        jsonObject.put(columnNum + "", CellUtil.getCellValue(cell));
//                    }
//                    columnNum++;
//                }
//                dataList.add(jsonObject);
//            }
//            // endregion
//
            String s = JSONObject.toJSONString(dataList);
            resultObj[sheetNum] = s;
            sheetNum++;
        }

        return resultObj;
    }
    // endregion

    /**
     * 获取一张工作表中的数据
     * <b>
     * 如果导入数据中包含日期格式 着重说明Excel文件中对应单元格内添加批注
     * </b>
     *
     * @param sheet             工作表
     * @param isNeedTableHeader 是否需要表头数据，如果需要，传入:true 不需要，传入:false
     */
    private static List<Object> getSheetData(Sheet sheet, boolean isNeedTableHeader) {
        // 工作表名
        String sheetName = sheet.getSheetName();
        LOGGER.info("--- [当前工作表名] {}", sheetName);
        // 物理行数
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        LOGGER.info("--- [当前工作表的行数为] {}行", physicalNumberOfRows);
        // 表头行
        Row rowHeader = sheet.getRow(0);
        // region 表头行

        // 最坏的情况，所有列都是时间列
        List<Integer> columnDate = new ArrayList<>();

        // 列序列
        int columnNum = 0;

        // 定义一个 jsonObject 用来接收表头数据
        JSONObject headJson = new JSONObject();

        // 表头行
        Iterator<Cell> iterator = rowHeader.iterator();
        while (iterator.hasNext()) {
            Cell cell = iterator.next();
            /*
             * 如果导入数据中包含日期格式 着重说明Excel文件中对应单元格内添加批注
             */
            Object cellValue = CellUtil.getCellValue(cell);
            if (cellValue instanceof Date) {
                int columnIndex = cell.getColumnIndex();
                // 设置单元格数据
                cellValue = cell.getDateCellValue();
                columnDate.add(columnIndex);
            }
            headJson.put(columnNum + "", cellValue);
            columnNum++;
        }
        // endregion

        // 用于保存表单数据的对象集合
        List<Object> dataList = new ArrayList<>(physicalNumberOfRows);

        // 返回集合中需要表头
        if (isNeedTableHeader) {
            // 添加表头数据到数据中
            dataList.add(headJson);
        }
        // region 数据行
        for (int i = 1; i < physicalNumberOfRows; i++) {
            // 遍历数据行
            Row dataRow = sheet.getRow(i);
            getRowData(dataRow, columnDate, dataList);
        }
        // endregion
        return dataList;
    }

    /**
     * 获取行数据
     *
     * @param dataRow    数据行
     * @param columnDate 时间列
     * @param dataList   数据集合
     */
    private static void getRowData(Row dataRow, List<Integer> columnDate, List<Object> dataList) {
        JSONObject jsonObject = new JSONObject();
        Iterator<Cell> cellIterator = dataRow.iterator();
        // 列序列
        int columnNum = 0;
        // 迭代列
        while (cellIterator.hasNext()) {
            // 数据区域
            Cell cell = cellIterator.next();
            if (columnDate.size() != 0) {
                // 日期列迭代
                Iterator<Integer> iteratorDataCol = columnDate.iterator();
                while (iteratorDataCol.hasNext()) {
                    Integer next = iteratorDataCol.next();
                    // 第j列是日期列
                    if (next == columnNum) {
                        // 获取单元格的值
                        jsonObject.put(columnNum + "", cell.getDateCellValue());
                        // 迭代时移出next
                        iteratorDataCol.remove();
                        break;
                    }
                }
            } else {
                jsonObject.put(columnNum + "", CellUtil.getCellValue(cell));
            }
            columnNum++;
        }
        dataList.add(jsonObject);

    }
    // endregion

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
     * @throws StreamException      文件读取异常
     * @throws FileException        文件异常
     * @throws StreamCloseException 流关闭异常
     */
    // region 导入Excel表格并生成对应实体类的集合
    public static <T> List<T> importExcel(File file, Class clazz) {
        if (file == null) {
            throw new ParamException("文件参数为空!");
        }
        if (clazz == null) {

        }
        // 获取文件名
        String fileName = file.getName();
        // 文件后缀为：.xlsx
        if (fileName.endsWith(FileConstant.SUFFIX_XLSX)) {
            try {
                // 调用导入XLXS方法
                return importExcelXlsx(new FileInputStream(file), clazz);
            } catch (IOException e) {
                throw new StreamException("导入.xlsx文件失败，文件名：[" + fileName + "]，异常信息：" + e.getMessage());
            }
            // 文件后缀为：.xls
        } else if (fileName.endsWith(FileConstant.SUFFIX_XLS)) {
            FileInputStream inputStream;
            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new FileException("文件找不到异常，文件路径为：" + file.getAbsolutePath());
            }
            List<T> objects = null;
            try {
                objects = importExcelXls(inputStream, clazz);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new StreamCloseException("文件流关闭失败：" + e.getMessage());
            }
            return objects;
        } else
            return null;
    }
    // endregion

    /**
     * 导入Xlsx的Excel文件
     *
     * @param fileInputStream 文件输入流
     * @param clazz           映射的实体类类型
     * @return 实体集合
     */
    // region 导入Xlsx的Excel文件
    public static <T> List<T> importExcelXlsx(InputStream fileInputStream, Class clazz) {
        List<T> list = new ArrayList<>();
        // XSSFWorkbook 加载Excel文件
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (xssfWorkbook != null) {
            // 获取文件中第一个工作薄
            XSSFSheet firstSheet = xssfWorkbook.getSheetAt(0);
            // 获取工作薄中总行数
            int rows = firstSheet.getPhysicalNumberOfRows();
            LOGGER.info("----------------[工作薄中总行数]----------------{} 行", rows);
            // 从第二行开始计算数据
            for (int i = 1; i < rows; i++) {
                // 获取首行数据
                XSSFRow firstSheetRow = firstSheet.getRow(0);
                // 获取第i行数据
                XSSFRow row = firstSheet.getRow(i);
                if (row != null) {
                    // 获取第i行的总列数
                    int physicalNumberOfCells = row.getLastCellNum();
                    LOGGER.info("----------------[共计{}列]----------------", physicalNumberOfCells);
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
                        // 获取当前类的父类
                        Class superclass = clazz.getSuperclass();
                        // 两层继承关系
                        if (Object.class != superclass) {
                            // 字段数组   如果不是Object类
                            Field[] fields = superclass.getDeclaredFields();
                            // 设置私有变量可以被访问
                            Field.setAccessible(fields, true);
                            // 通过反射设置对象属性
                            reflexObject(fields, firstSheetRowCell, cell, o);
                        }
                        // 类中属性
                        Field[] fields = clazz.getDeclaredFields();
                        // 设置允许访问私有属性
                        Field.setAccessible(fields, true);
                        // 通过反射设置对象属性
                        reflexObject(fields, firstSheetRowCell, cell, o);
                    }
                    list.add(o);
                }
            }
        }
        return list;
    }

    /**
     * 导入后缀名为xls的Excel文件
     *
     * @param fileInputStream 文件输入流
     * @param clazz           要导成的实体类型
     * @param <T>             泛型: 要导成的实体类型
     * @return 数据集合
     * @throws IOException IO异常
     */
    // region 导入后缀名为xls的Excel文件
    public static <T> List<T> importExcelXls(InputStream fileInputStream, Class clazz) throws IOException {
        // 定义一个数组集合，用于返回导入数据
        List<T> list = new ArrayList<>();
        // 从文件中获取工作薄
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);
        // 获取一个工作表
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
        fileInputStream.close();
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
    // region 反射设置对象属性值
    private static void reflexObject(Field[] fields, Cell firstSheetRowCell, Cell cell, Object o) {
        if (firstSheetRowCell != null && "".equals(firstSheetRowCell.toString())) {
            return;
        }
        for (int k = 0; k < fields.length; k++) {
            Field field = fields[k];
            String name = field.getName();
            if ("serialVersionUID".equals(name)) {
                continue;
            }
            // 表格行单元格值
            String sheetRowCellValue = null;
            if (firstSheetRowCell != null) {
                // 获取表头对应的实体字段的属性值
                sheetRowCellValue = firstSheetRowCell.toString().split("-")[1];
            }
            // Excel列与属性名对比
            if (name.equals(sheetRowCellValue)) {
                // 反射获取属性类型
                Class<?> type = field.getType();
                // 单元格
                Object cellValue = CellUtil.getCellValue(cell);
                if (cellValue != null) {
                    // 单元格数据类型
                    Class<?> cellValueClass = cellValue.getClass();
                    // 设置一个变量用于统一单元格数据
                    Object cast = null;
                    // 实体类属性类型名
                    String entityFieldClassName = type.getName();
                    // 单元格中数据类型名称
                    String cellValueClassName = cellValueClass.getName();
                    // 类型相同
                    if (entityFieldClassName.endsWith(cellValueClassName)) {
                        cast = type.cast(cellValue);
                    } else {
                        // 单元格数据类型是Double
                        if ("java.lang.Double".equals(cellValueClassName)) {
                            Double d = (Double) cellValue;
                            // 如果实体类类型为Double
                            if ("java.lang.Double".equals(entityFieldClassName) || "double".equals(entityFieldClassName)) {
                                cast = cellValue;
                            } else if ("java.lang.Integer".equals(entityFieldClassName) || "int".equals(entityFieldClassName)) {
                                cast = d.intValue();
                            } else if ("java.lang.Byte".equals(entityFieldClassName) || "byte".equals(entityFieldClassName)) {
                                cast = d.byteValue();
                            } else if ("java.lang.String".equals(entityFieldClassName)) {
                                cast = String.valueOf(cellValue);
                            } else {
                                cast = cellValue;
                            }
                        } else if ("java.lang.String".equals(cellValueClassName)) {
                            String cellValueStr = cellValue.toString();
                            if (cellValueStr != null && !"".equals(cellValueStr)) {
                                if ("java.util.Date".equals(entityFieldClassName)) {
                                    cast = UtilDate.stringToJavaUtilDate(cellValueStr, "yyyy-MM-dd");
                                } else if ("java.lang.Byte".equals(entityFieldClassName) || "byte".equals(entityFieldClassName)) {
                                    byte[] bytes = cellValueStr.getBytes();
                                    cast = bytes[0];
                                } else if ("java.lang.Integer".equals(entityFieldClassName) || "int".equals(entityFieldClassName)) {
                                    cast = Integer.valueOf(cellValueStr);
                                } else {
                                    cast = cellValue;
                                }
                            } else {
                                cast = null;
                            }
                        }
                    }
                    try {
                        if (cast != null)
                            // 给对象赋值
                            field.set(o, cast);
                    } catch (IllegalAccessException e) {
                        LOGGER.error("[单元格数据转实体异常] 异常实体：{},异常信息：{}", o.toString(), e.getMessage());
                    }
                }
                break;
            }
        }
    }
}
