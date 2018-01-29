package org.yyx.util.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yyx.annotation.OperationMessage;
import org.yyx.entity.OperationMessageEntity;

import java.lang.reflect.Method;

/**
 * 一个简单的日志实体信息封装
 * Create by 叶云轩 at 2018/1/26 20:20
 * Concat at tdg_yyx@foxmail.com
 */
public class UtilLog {

    /**
     * UtilLog 日志控制器
     * Create by 叶云轩 at 2018/1/26 20:24
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilLog.class);

    public static OperationMessageEntity encapsulationEntity(ProceedingJoinPoint proceedingJoinPoint) {
        // 当前正在执行的类
        Object targetClass = proceedingJoinPoint.getTarget();
        LOGGER.info("[拦截类] {}", targetClass);
        Signature signature = proceedingJoinPoint.getSignature();
        // 当前正在执行的方法名
        String methodName = signature.getName();
        // 当前方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        LOGGER.info("[拦截方法] {}({})", methodName, args);
        MethodSignature methodSignature;
        // 拦截参数类型
        methodSignature = (MethodSignature) signature;
        Class<?>[] parameterTypes = methodSignature.getMethod().getParameterTypes();
        LOGGER.info("[参数类型] {}", parameterTypes);
        Method method = null;
        try {
            // 获取方法
            method = targetClass.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            LOGGER.error("[没有此方法] {}", methodName);
        }
        StringBuilder stringBuilder;
        // 是否有自定义注解
        if (method != null) {
            if (method.isAnnotationPresent(OperationMessage.class)) {
                OperationMessage annotation = method.getAnnotation(OperationMessage.class);
                String modules = annotation.modules();
                String methods = annotation.methods();
                String[] params = annotation.params();
                OperationMessage.OperationMessageEnum type = annotation.type();
                LOGGER.info("[模块] {}", modules);
                LOGGER.info("[方法] {}", methods);
                LOGGER.info("[参数解释] {}", params);
                DateTime now = DateTime.now();
                stringBuilder = new StringBuilder("在[ ")
                        .append(now)
                        .append(" ]对[ ")
                        .append(modules).append(" ]模块进行了[ ").append(type).append(" ]操作")
                        .append("。调用方法为：[ ").append(methods).append(" ]参数为：{ ")
                ;
                // 长度
                int length = parameterTypes.length - 1;
                for (int i = 0; i < length; i++) {
                    stringBuilder.append(params[i]).append("：").append(args[i]);
                    if (i == length - 1) {
                        break;
                    } else {
                        stringBuilder.append(" ; ");
                    }
                }
                OperationMessageEntity operationMessageEntity = new OperationMessageEntity();
                operationMessageEntity.setGmtCreate(now.toDate());
                operationMessageEntity.setOperationDescription(stringBuilder.toString());
                operationMessageEntity.setOperationMethod(methods);
                operationMessageEntity.setOperationStatus(1);
                operationMessageEntity.setOperationType(type.name());
                return operationMessageEntity;
            }
        }
        return null;
    }
}
