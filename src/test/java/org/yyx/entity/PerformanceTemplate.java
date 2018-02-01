package org.yyx.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 绩效考核模板项表
 * <p>
 * Create by 叶云轩 at 2018/1/30 16:50
 * Concat at tdg_yyx@foxmail.com
 */
public class PerformanceTemplate extends EntityBase implements Serializable {
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 2798435704083721300L;

    /**
     * 绩效考核定义表编号 外键关系
     */
    private int defineId;

    /**
     * 模板中字段的名称
     */
    private String fieldName;
    /**
     * 模板中字段的等级
     */
    private int fieldLevel;
    /**
     * 上级字段编号，外键关系
     */
    private int fieldParentId;
    /**
     * 字段的分值
     */
    private int fieldGrade;

    /**
     * 子级信息集合
     */
    private List<PerformanceTemplate> children;

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public List<PerformanceTemplate> getChildren() {
        return children;
    }

    public void setChildren(List<PerformanceTemplate> children) {
        this.children = children;
    }

    public int getDefineId() {
        return defineId;
    }

    public void setDefineId(int defineId) {
        this.defineId = defineId;
    }

    public int getFieldGrade() {
        return fieldGrade;
    }

    public void setFieldGrade(int fieldGrade) {
        this.fieldGrade = fieldGrade;
    }

    public int getFieldLevel() {
        return fieldLevel;
    }

    public void setFieldLevel(int fieldLevel) {
        this.fieldLevel = fieldLevel;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getFieldParentId() {
        return fieldParentId;
    }

    public void setFieldParentId(int fieldParentId) {
        this.fieldParentId = fieldParentId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), defineId, fieldName, fieldLevel, fieldParentId, fieldGrade);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PerformanceTemplate that = (PerformanceTemplate) o;
        return defineId == that.defineId &&
                fieldLevel == that.fieldLevel &&
                fieldParentId == that.fieldParentId &&
                fieldGrade == that.fieldGrade &&
                Objects.equals(fieldName, that.fieldName);
    }

    @Override
    public String toString() {
        return super.toString() +
                "PerformanceTemplate{" +
                "defineId=" + defineId +
                ", fieldName='" + fieldName + '\'' +
                ", fieldLevel=" + fieldLevel +
                ", fieldParentId=" + fieldParentId +
                ", fieldGrade=" + fieldGrade +
                ", children=" + children +
                '}';
    }

}
