package org.yyx.util.file.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.yyx.util.file.poi.entity.ExcelEntity;

/**
 * 单元格工具类
 * <p>
 * create by 叶云轩 at 2017/11/21 - 19:48
 * contact by tdg_yyx@foxmail.com
 */
public class CellUtil {

    /**
     * 获取单元格数据
     *
     * @param cell 单元格
     * @return 封装成字符串类型
     */
    protected static Object getCellValue(Cell cell) {
        Object value = null;
        if (cell != null) {
            switch (cell.getCellTypeEnum()) {
                case _NONE:
                case BLANK:
                    value = "";
                    break;
                case STRING:
                    value = cell.getStringCellValue();
                    break;
                case ERROR:
                    value = null;
                    break;
                case FORMULA:
                    value = cell.getCellFormula();
                    break;
                case BOOLEAN:
                    value = cell.getBooleanCellValue();
                    break;
                case NUMERIC:
                    value = cell.getNumericCellValue();
            }
        }
        return value;
    }

    /**
     * 判断当前单元格是否是合并单元格方法
     *
     * @param sheet  工作薄
     * @param row    当前单元格的rowIndex
     * @param column 当前单元格的columnIndex
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
