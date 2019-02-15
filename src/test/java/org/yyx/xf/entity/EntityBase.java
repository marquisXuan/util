package org.yyx.xf.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 胡友云 on 2017/11/24 - 18:05
 * Concat hanyihuyouyun@163.com
 */
@Data
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
}