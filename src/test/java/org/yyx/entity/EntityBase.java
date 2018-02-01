package org.yyx.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 胡友云 on 2017/11/24 - 18:05
 * Concat hanyihuyouyun@163.com
 */
public class EntityBase implements Serializable {
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -550424576949009049L;
    /**
     * 主键
     */
    private Integer pkField;
    /**
     * 记录创建时间
     */
    private Date gmtCreate;
    /**
     * 记录最后一次修改时间
     */
    private Date gmtModified;

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getPkField() {
        return pkField;
    }

    public void setPkField(Integer pkField) {
        this.pkField = pkField;
    }

    @Override
    public int hashCode() {
        int result = pkField != null ? pkField.hashCode() : 0;
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtModified != null ? gmtModified.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityBase that = (EntityBase) o;

        if (pkField != null ? !pkField.equals(that.pkField) : that.pkField != null) return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null) return false;
        return gmtModified != null ? gmtModified.equals(that.gmtModified) : that.gmtModified == null;
    }

    @Override
    public String toString() {
        return "EntityBase{" +
                "pkField=" + pkField +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}