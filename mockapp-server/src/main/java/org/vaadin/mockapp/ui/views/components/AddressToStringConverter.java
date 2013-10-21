package org.vaadin.mockapp.ui.views.components;

import com.vaadin.data.util.converter.Converter;
import org.vaadin.mockapp.backend.domain.Address;

import java.util.Locale;

/**
 * @author petter@vaadin.com
 */
public class AddressToStringConverter implements Converter<String, Address> {
    private static boolean hasValue(String s) {
        return s != null && !s.isEmpty();
    }

    @Override
    public Address convertToModel(String value, Class<? extends Address> targetType, Locale locale) throws ConversionException {
        throw new ConversionException("This is a one way converter");
    }

    @Override
    public String convertToPresentation(Address value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        if (value == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            if (hasValue(value.getStreet())) {
                sb.append(value.getStreet());
            }
            if (sb.length() > 0 && hasValue(value.getPostalCode()) || hasValue(value.getCity())) {
                sb.append(",");
            }
            if (hasValue(value.getPostalCode())) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(value.getPostalCode());
            }
            if (hasValue(value.getCity())) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(value.getCity());
            }
            return sb.toString();
        }
    }

    @Override
    public Class<Address> getModelType() {
        return Address.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
