package org.yyx.xf.tool.db.domain.entity;

import lombok.Data;
import org.beetl.sql.core.annotatoin.AssignID;
import org.yyx.xf.tool.string.util.UtilString;

import java.util.Date;

/**
 * 数据库基础抽象类
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/12/28-15:10
 */
@Data
public abstract class AbstractBaseEntity implements BaseEntity {

    /**
     * 数据库表记录唯一主键标识
     */
    @AssignID
    private String pkField;
    /**
     * 数据库表记录状态 0：正常(可用,可见) 1：不正常(不可用,不可见)
     */
    private short status;
    /**
     * 数据库表记录创建时间
     */
    private Date gmtCreate;
    /**
     * 数据库表记录最后一次修改时间
     */
    private Date gmtModified;

    /**
     * 初始化对象的方法
     */
    public void init() {
        this.pkField = UtilString.randomUUID();
        this.gmtCreate = new Date();
        this.status = 0;
    }
}
