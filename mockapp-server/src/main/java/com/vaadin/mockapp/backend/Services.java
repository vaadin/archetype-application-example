package com.vaadin.mockapp.backend;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for looking up services by their interfaces. Service singletons are registered with this class,
 * e.g. by servlet context listeners upon application startup, and can then be accessed by different application components.
 *
 * @author petter@vaadin.com
 */
public class Services {

    private static final Map<Class<?>, Object> serviceMap = Collections.synchronizedMap(new HashMap<Class<?>, Object>());

    private Services() {
    }

    /**
     * Returns an instance of the specified service interface, or {@code null} if no service has been registered.
     */
    @Nullable
    public synchronized static <T> T get(@NotNull Class<T> serviceInterface) {
        T service = serviceInterface.cast(serviceMap.get(serviceInterface));
        if (service == null) {
            Logger.getLogger(Services.class.getCanonicalName()).log(Level.WARNING, "No service instance found for {0}", serviceInterface);
        }
        return service;
    }

    /**
     * Registers the specified service instance for the specified service interfaces.
     */
    public static void register(@NotNull Object service, @NotNull Class<?>... serviceInterfaces) {
        for (Class<?> serviceInterface : serviceInterfaces) {
            serviceMap.put(serviceInterface, service);
            Logger.getLogger(Services.class.getCanonicalName()).log(Level.INFO, "Registered service instance {0} for {1}", new Object[]{service, serviceInterface});
        }
    }

    /**
     * Removes previously registered service instances for the specified service interfaces.
     */
    public static void remove(@NotNull Class<?>... serviceInterfaces) {
        for (Class<?> serviceInterface : serviceInterfaces) {
            serviceMap.remove(serviceInterface);
            Logger.getLogger(Services.class.getCanonicalName()).log(Level.INFO, "Removed service registration for {0}", serviceInterface);
        }
    }

}
