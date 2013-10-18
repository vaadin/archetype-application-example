package org.vaadin.mockapp.ui.views;

import com.vaadin.data.util.converter.Converter;
import org.joda.time.DateTime;

import java.util.Locale;

/**
 * @author petter@vaadin.com
 */
public class DateTimeToStringConverter implements Converter<String, DateTime> {

    @Override
    public DateTime convertToModel(String value, Class<? extends DateTime> targetType, Locale locale) throws ConversionException {
        throw new ConversionException("This is a one way converter");
    }

    @Override
    public String convertToPresentation(DateTime value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        if (value == null) {
            return null;
        } else {
            return value.toString("dd.MM.yyyy HH:mm:ss");
        }
    }

    @Override
    public Class<DateTime> getModelType() {
        return DateTime.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
