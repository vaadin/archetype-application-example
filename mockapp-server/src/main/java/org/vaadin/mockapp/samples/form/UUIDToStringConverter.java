package org.vaadin.mockapp.samples.form;

import com.vaadin.data.util.converter.Converter;

import java.util.Locale;
import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
public class UUIDToStringConverter implements Converter<String, UUID> {

    private static final String NULL_STRING = "(none)";

    @Override
    public UUID convertToModel(String value, Class<? extends UUID> targetType, Locale locale) throws ConversionException {
        try {
            return value == null || value.isEmpty() || value.equals(NULL_STRING) ? null : UUID.fromString(value);
        } catch (IllegalArgumentException ex) {
            throw new ConversionException(ex);
        }
    }

    @Override
    public String convertToPresentation(UUID value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return value == null ? NULL_STRING : value.toString();
    }

    @Override
    public Class<UUID> getModelType() {
        return UUID.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
