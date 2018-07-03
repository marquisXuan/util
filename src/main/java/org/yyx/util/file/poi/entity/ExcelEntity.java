package org.yyx.util.file.poi.entity;

/**
 * 单元格实体
 * <p>
 * create by 叶云轩 at 2017/11/21 - 19:45
 * contact by tdg_yyx@foxmail.com
 */
public class ExcelEntity {

    /**
     * 跨行
     */
    private static final String ROW_MERGE = "row";
    /**
     * 跨列
     */
    private static final String COL_MERGE = "col";
    /**
     * 跨行跨列
     */
    private static final String ROW_AND_COL = "row_col";
    /**
     * 是否是否跨行
     */
    private boolean merged;
    /**
     * 合并单元格起始横（行）坐标
     */
    private int startRow;
    /**
     * 合并单元格终止横（行）坐标
     */
    private int endRow;
    /**
     * 合并单元格起始纵（列）坐标
     */
    private int startCol;
    /**
     * 合并单元格终止纵（列）坐标
     */
    private int endCol;
    /**
     * 合并的方式
     */
    private String mergedMethod;

    public ExcelEntity(boolean merged, int startRow, int endRow
            , int startCol, int endCol) {
        this.merged = merged;
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
        this.mergedMethod =
                merged ? ((startRow == endRow) ? COL_MERGE : ((startCol == endCol) ? ROW_MERGE : ROW_AND_COL)) : "";

    }

    public static String getRowMerge() {
        return ROW_MERGE;
    }

    public static String getColMerge() {
        return COL_MERGE;
    }

    public static String getRowAndCol() {
        return ROW_AND_COL;
    }

    public boolean isMerged() {
        return merged;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndCol() {
        return endCol;
    }

    public String getMergedMethod() {
        return mergedMethod;
    }

    @Override
    public String toString() {
        return "Result{" +
                "merged=" + merged +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", startCol=" + startCol +
                ", endCol=" + endCol +
                ", mergedMethod='" + mergedMethod + '\'' +
                '}';
    }
}
