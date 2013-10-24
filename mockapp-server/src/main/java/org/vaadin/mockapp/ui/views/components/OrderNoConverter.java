package org.vaadin.mockapp.ui.views.components;

import com.vaadin.data.util.converter.Converter;

import java.util.Locale;

/**
 * @author petter@vaadin.com
 */
public class OrderNoConverter implements Converter<String, Integer> {

    @Override
    public Integer convertToModel(String value, Class<? extends Integer> targetType, Locale locale) throws ConversionException {
        try {
            return value == null || value.isEmpty() ? null : Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new ConversionException(value + " is not a valid integer");
        }
    }

    @Override
    public String convertToPresentation(Integer value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return value == null ? null : value.toString();
    }

    @Override
    public Class<Integer> getModelType() {
        return Integer.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
