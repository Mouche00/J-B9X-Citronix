package org.citronix.utils;

import java.lang.reflect.Method;

public class ReflectionUtil {
    public String getId(Object entity) {
        try {
            Method getIdMethod = entity.getClass().getMethod("getId");
            return (String) getIdMethod.invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access getId method", e);
        }
    }
}
