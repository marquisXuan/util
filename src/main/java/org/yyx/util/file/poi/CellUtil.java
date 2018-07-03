package org.yyx.util.file.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.util.file.poi.entity.ExcelEntity;

/**
 * 单元格工具类
 * <p>
 * create by 叶云轩 at 2017/11/21 - 19:48
 * contact by tdg_yyx@foxmail.com
 */
public class CellUtil {
    /**
     * CellUtil 日志控制器
     * Create by 叶云轩 at 2018/4/24 下午4:20
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CellUtil.class);

    /**
     * 获取单元格数据
     *
     * @param cell 单元格
     *
     * @return 封装数据类型
     */
    protected static Object getCellValue(Cell cell) {
        CellType cellTypeEnum = cell.getCellTypeEnum();
        /*
         * 获取单元格存储的数据类型
         * _NONE(-1),   未知类型
         * NUMERIC(0),  数字类型 Numeric cell type (whole numbers, fractional numbers, dates)
         * STRING(1),   字符串类型
         * FORMULA(2),  函数类型
         * BLANK(3),    空类型
         * BOOLEAN(4),  boolean类型
         * ERROR(5);    错误的类型
         */
        LOGGER.info("\n\t⌜⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓\n" +
                "\t├ [当前单元格数据类型]: {}\n" +
                "\t⌞⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓⎓", cellTypeEnum);
        Object value = null;
        switch (cellTypeEnum) {
            // NONE和BlANK可以看成一种情况
            case _NONE:
            case BLANK:
                value = "";
                break;
            case STRING:
                // String
                value = cell.getStringCellValue();
                break;
            case ERROR:
                value = null;
                break;
            case FORMULA:
                // String
                value = cell.getCellFormula();
                break;
            case BOOLEAN:
                // boolean
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                // 获取单元格的备注信息
                Comment cellComment = cell.getCellComment();
                if (cellComment != null) {
                    RichTextString comment = cellComment.getString();
                    String commentValue = comment.getString();
                    if (commentValue.contains("date") || commentValue.contains("日期")) {
                        value = cell.getDateCellValue();
                    }
                } else {
                    // double
                    value = cell.getNumericCellValue();
                }
                break;
            default:
                break;
        }
        return value;
    }

    /**
     * 判断当前单元格是否是合并单元格方法
     *
     * @param sheet  工作薄
     * @param row    当前单元格的rowIndex
     * @param column 当前单元格的columnIndex
     *
     * @return 返回封装的实体
     * 是否是合并单元格
     * 合并方式等
     */
    protected static ExcelEntity isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return new ExcelEntity(true, firstRow + 1, lastRow + 1, firstColumn + 1, lastColumn + 1);
                }
            }
        }
        return new ExcelEntity(false, 0, 0, 0, 0);
    }


}
