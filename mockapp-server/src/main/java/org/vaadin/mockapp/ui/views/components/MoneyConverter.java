package org.vaadin.mockapp.ui.views.components;

import com.vaadin.data.util.converter.Converter;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * @author petter@vaadin.com
 */
public class MoneyConverter implements Converter<String, BigDecimal> {
    @Override
    public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale) throws ConversionException {
        return new BigDecimal(value);
    }

    @Override
    public String convertToPresentation(BigDecimal value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        if (value == null) {
            return null;
        } else {
            return String.format("%,.2f", value);
        }
    }

    @Override
    public Class<BigDecimal> getModelType() {
        return BigDecimal.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
