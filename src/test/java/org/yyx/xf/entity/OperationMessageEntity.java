package org.yyx.xf.entity;

import java.util.Date;
import java.util.Objects;

/**
 * 操作信息实体
 * Create by 叶云轩 at 2018/1/26 20:10
 * Concat at tdg_yyx@foxmail.com
 */
public class OperationMessageEntity {

    /**
     * 主键
     */
    private int pkField;
    /**
     * 操作人员编号
     */
    private int operationEmployeeId;
    /**
     * 操作人姓名
     */
    private String operationEmployeeName;
    /**
     * 操作类型
     */
    private String operationType;
    /**
     * 操作方法
     */
    private String operationMethod;
    /**
     * 操作状态
     */
    private int operationStatus;
    /**
     * 操作说明
     */
    private String operationDescription;
    /**
     * 创建时间
     */
    private Date gmtCreate;

    public OperationMessageEntity() {
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    public int getOperationEmployeeId() {
        return operationEmployeeId;
    }

    public void setOperationEmployeeId(int operationEmployeeId) {
        this.operationEmployeeId = operationEmployeeId;
    }

    public String getOperationEmployeeName() {
        return operationEmployeeName;
    }

    public void setOperationEmployeeName(String operationEmployeeName) {
        this.operationEmployeeName = operationEmployeeName;
    }

    public String getOperationMethod() {
        return operationMethod;
    }

    public void setOperationMethod(String operationMethod) {
        this.operationMethod = operationMethod;
    }

    public int getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(int operationStatus) {
        this.operationStatus = operationStatus;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public int getPkField() {

        return pkField;
    }

    public void setPkField(int pkField) {
        this.pkField = pkField;
    }

    @Override
    public int hashCode() {

        return Objects.hash(pkField, operationEmployeeId, operationEmployeeName, operationType, operationMethod, operationStatus, operationDescription, gmtCreate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationMessageEntity that = (OperationMessageEntity) o;
        return pkField == that.pkField &&
                operationEmployeeId == that.operationEmployeeId &&
                operationStatus == that.operationStatus &&
                Objects.equals(operationEmployeeName, that.operationEmployeeName) &&
                Objects.equals(operationType, that.operationType) &&
                Objects.equals(operationMethod, that.operationMethod) &&
                Objects.equals(operationDescription, that.operationDescription) &&
                Objects.equals(gmtCreate, that.gmtCreate);
    }

    @Override
    public String toString() {
        return "OperationMessageEntity{" +
                "pkField=" + pkField +
                ", operationEmployeeId=" + operationEmployeeId +
                ", operationEmployeeName='" + operationEmployeeName + '\'' +
                ", operationType='" + operationType + '\'' +
                ", operationMethod='" + operationMethod + '\'' +
                ", operationStatus=" + operationStatus +
                ", operationDescription='" + operationDescription + '\'' +
                ", gmtCreate=" + gmtCreate +
                '}';
    }
}
