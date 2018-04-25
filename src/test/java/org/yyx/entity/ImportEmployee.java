package org.yyx.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于导入员工数据的实体
 * Created by 胡友云 on 2018/1/26 - 9:28
 * Concat hanyihuyouyun@163.com
 */
@Data
public class ImportEmployee extends EntityBase implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5959391615971387943L;
    /**
     * 员工姓名
     */
    private String employeeName;
    /**
     * 员工头像图片路径
     */
    private String headerImg;

}