package org.yyx.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作类型注解
 * Create by 叶云轩 at 2018/1/26 17:05
 * Concat at tdg_yyx@foxmail.com
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationMessage {

    /**
     * 方法名称
     *
     * @return 方法名称
     */
    String methods() default "";

    /**
     * 模块名称
     *
     * @return 模块名称
     */
    String modules() default "";

    /**
     * 参数解释
     *
     * @return 参数的解释
     */
    String[] params() default "";

    /**
     * 操作类型
     *
     * @return 操作类型
     */
    OperationMessageEnum type();

    /**
     * 枚举类型
     */
    enum OperationMessageEnum {
        INSERT("插入"), UPDATE("更新"), DELETE("删除"), AUTO("系统自动操作");

        private String type;

        private OperationMessageEnum(String type) {
            this.type = type;
        }
    }
}
