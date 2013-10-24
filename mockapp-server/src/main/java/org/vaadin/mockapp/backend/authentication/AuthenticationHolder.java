package org.vaadin.mockapp.backend.authentication;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;

/**
 * Class for retrieving and setting the {@link Authentication} object of the current session. All methods of this class
 * require that a {@link VaadinRequest} is bound to the current thread.
 *
 * @author petter@vaadin.com
 * @see com.vaadin.server.VaadinService#getCurrentRequest()
 */
public final class AuthenticationHolder {

    /**
     * The attribute key used to store the authentication token in the session.
     */
    public static final String AUTHENTICATION_SESSION_ATTRIBUTE_KEY = Authentication.class.getCanonicalName();

    private AuthenticationHolder() {
    }

    /**
     * Returns the authentication object stored in the current session, or {@link Authentication#ANONYMOUS} if no authentication is stored.
     *
     * @throws IllegalStateException if the current session cannot be accessed.
     */
    public static Authentication getAuthentication() {
        Authentication authentication = (Authentication) getCurrentRequest().getWrappedSession().getAttribute(AUTHENTICATION_SESSION_ATTRIBUTE_KEY);
        if (authentication == null) {
            return Authentication.ANONYMOUS;
        } else {
            return authentication;
        }
    }

    /**
     * Sets the authentication object to be stored in the current session. Using a {@code null} authentication will remove
     * the existing authentication object from the session.
     *
     * @throws IllegalStateException if the current session cannot be accessed.
     */
    public static void setAuthentication(Authentication authentication) {
        if (authentication == null) {
            getCurrentRequest().getWrappedSession().removeAttribute(AUTHENTICATION_SESSION_ATTRIBUTE_KEY);
        } else {
            getCurrentRequest().getWrappedSession().setAttribute(AUTHENTICATION_SESSION_ATTRIBUTE_KEY, authentication);
        }
    }

    private static VaadinRequest getCurrentRequest() {
        VaadinRequest request = VaadinService.getCurrentRequest();
        if (request == null) {
            throw new IllegalStateException("No request bound to current thread");
        }
        return request;
    }
}
