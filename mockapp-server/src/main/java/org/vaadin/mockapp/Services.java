package org.vaadin.mockapp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for looking up services by their interfaces.
 *
 * @author petter@vaadin.com
 */
public class Services {

    private static final Map<Class<?>, ServiceProvider<?>> serviceMap = Collections.synchronizedMap(new HashMap<Class<?>, ServiceProvider<?>>());

    private Services() {
    }

    /**
     * Returns an instance of the specified service interface, or {@code null} if no service has been registered.
     */
    public synchronized static <T> T get(Class<T> serviceInterface) {
        ServiceProvider serviceProvider = serviceMap.get(serviceInterface);
        if (serviceProvider == null) {
            Logger.getLogger(Services.class.getCanonicalName()).log(Level.WARNING, "No service found for {0}", serviceInterface);
            return null;
        }
        return serviceInterface.cast(serviceProvider.get());
    }

    /**
     * Registers the specified service instance for the specified service interface.
     */
    public static <T> void register(final T service, Class<? super T> serviceInterface) {
        register(new StaticServiceProvider<T>(service), serviceInterface);
    }

    /**
     * Registers the specified service provider for the specified service interface.
     */
    public static <T> void register(ServiceProvider<T> serviceProvider, Class<? super T> serviceInterface) {
        serviceMap.put(serviceInterface, serviceProvider);
        Logger.getLogger(Services.class.getCanonicalName()).log(Level.INFO, "Registered service provider {0} for {1}", new Object[]{serviceProvider, serviceInterface});
    }

    /**
     * Removes previously registered service instances for the specified service interfaces.
     */
    public static void remove(Class<?>... serviceInterfaces) {
        for (Class<?> serviceInterface : serviceInterfaces) {
            serviceMap.remove(serviceInterface);
            Logger.getLogger(Services.class.getCanonicalName()).log(Level.INFO, "Removed service registration for {0}", serviceInterface);
        }
    }

    /**
     *
     */
    public interface ServiceProvider<T> {

        T get();
    }

    /**
     *
     */
    public static class StaticServiceProvider<T> implements ServiceProvider<T> {

        private final T service;

        /**
         * @param service
         */
        public StaticServiceProvider(T service) {
            this.service = service;
        }


        @Override
        public T get() {
            return service;
        }

        @Override
        public String toString() {
            return String.format("%s[%s]", StaticServiceProvider.class.getSimpleName(), service);
        }
    }
}
