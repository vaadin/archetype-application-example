package org.vaadin.mockapp.ui.views.components;

import com.vaadin.data.util.converter.Converter;
import org.vaadin.mockapp.backend.domain.OrderState;

import java.util.Locale;

/**
 * @author petter@vaadin.com
 */
public class OrderStateToStringConverter implements Converter<String, OrderState> {
    @Override
    public OrderState convertToModel(String value, Class<? extends OrderState> targetType, Locale locale) throws ConversionException {
        throw new ConversionException("This is a one way converter");
    }

    @Override
    public String convertToPresentation(OrderState value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return value == null ? null : value.getDisplayName();
    }

    @Override
    public Class<OrderState> getModelType() {
        return OrderState.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
