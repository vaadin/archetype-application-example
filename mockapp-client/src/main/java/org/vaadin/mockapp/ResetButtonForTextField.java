package org.vaadin.mockapp;

import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.TextField;

/**
 * @see <a href="https://vaadin.com/blog/-/blogs/2656782">Extending components in Vaadin 7</a>
 */
public class ResetButtonForTextField extends AbstractExtension {

    public static void extend(TextField field) {
        new ResetButtonForTextField().extend((AbstractClientConnector) field);
    }
}
