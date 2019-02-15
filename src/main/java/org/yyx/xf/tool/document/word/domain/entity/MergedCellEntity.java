package org.yyx.xf.tool.document.word.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import static org.yyx.xf.tool.document.word.domain.constant.MergedCellMethodConstant.COL_MERGE;
import static org.yyx.xf.tool.document.word.domain.constant.MergedCellMethodConstant.ROW_AND_COL;
import static org.yyx.xf.tool.document.word.domain.constant.MergedCellMethodConstant.ROW_MERGE;

/**
 * 合并单元格实体
 * <p>
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/3 - 下午5:19
 */
@Data
@NoArgsConstructor
public class MergedCellEntity {

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

    /**
     * 构造函数
     *
     * @param merged   是否是否跨行
     * @param startRow 合并单元格起始横（行）坐标
     * @param endRow   合并单元格终止横（行）坐标
     * @param startCol 合并单元格起始纵（列）坐标
     * @param endCol   合并单元格终止纵（列）坐标
     */
    public MergedCellEntity(boolean merged, int startRow, int endRow, int startCol, int endCol) {
        this.merged = merged;
        this.startRow = startRow;
        this.endRow = endRow;
        this.startCol = startCol;
        this.endCol = endCol;
        this.mergedMethod = merged ? ((startRow == endRow) ? COL_MERGE : ((startCol == endCol) ? ROW_MERGE : ROW_AND_COL)) : "";

    }
}
