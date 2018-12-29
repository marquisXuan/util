package org.yyx.domain.entity;

import java.util.Date;

/**
 * 数据库基础抽象类
 * <p>
 *
 * @author 叶云轩 at tdg_yyx@foxmail.com
 * @date 2018/12/28-15:10
 */
public abstract class AbsBaseEntity implements BaseEntity {

    /**
     * 数据库表记录唯一主键标识
     */
    private long pkField;
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

    @Override
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public Date getGmtModified() {
        return gmtModified;
    }

    @Override
    public long getPkField() {
        return pkField;
    }

    public void setPkField(long pkField) {
        this.pkField = pkField;
    }

    @Override
    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}
