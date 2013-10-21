package org.vaadin.mockapp.backend.domain;

/**
 * @author petter@vaadin.com
 */
public final class ObjectUtils {

    private ObjectUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends Cloneable> T nullSafeClone(T original) {
        if (original == null) {
            return null;
        }
        try {
            return (T) original.getClass().getMethod("clone").invoke(original);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
