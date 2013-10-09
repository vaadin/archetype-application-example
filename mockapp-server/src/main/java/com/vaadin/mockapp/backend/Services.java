package com.vaadin.mockapp.backend;

import java.util.HashMap;
import java.util.Map;

/**
 * @author petter@vaadin.com
 */
public class Services {

    private static final Map<Class<?>, Object> serviceMap = new HashMap<Class<?>, Object>();

    private Services() {
    }

    public synchronized static <T> T get(Class<T> serviceClass) {
        return serviceClass.cast(serviceMap.get(serviceClass));
    }

    public synchronized static <T> void add(T service, Class<? super T>... serviceClasses) {
        for (Class<?> serviceClass: serviceClasses) {
            serviceMap.put(serviceClass, service);
        }
    }

    public synchronized static void remove(Class<?>... serviceClasses) {
        for (Class<?> serviceClass : serviceClasses) {
            serviceMap.remove(serviceClass);
        }
    }

}
