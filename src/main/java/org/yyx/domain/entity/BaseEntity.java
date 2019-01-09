package org.yyx.domain.entity;

import java.util.Date;

/**
 * 数据库基础抽象类接口
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/12/28-15:14
 */
public interface BaseEntity {

    /**
     * 获取记录创建时间
     *
     * @return 记录创建时间
     */
    Date getGmtCreate();

    /**
     * 获取记录最后一次修改时间
     *
     * @return 记录最后一次修改时间
     */
    Date getGmtModified();

    /**
     * 获取记录唯一标识
     *
     * @return 记录唯一标识
     */
    String getPkField();

    /**
     * 判断当前记录是否可用
     *
     * @return true：可用 false：不可用
     */
    default boolean isEnable() {
        return this.getStatus() == 0;
    }

    /**
     * 获取记录状态
     *
     * @return 记录状态 0：正常(可用,可见) 1：不正常(不可用,不可见)
     */
    short getStatus();
}
