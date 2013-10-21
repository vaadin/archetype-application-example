package org.vaadin.mockapp.ui.views.components;

import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.vaadin.mockapp.ui.theme.MockAppTheme;

import java.util.Date;
import java.util.Locale;

/**
 * @author petter@vaadin.com
 */
public class DateTimeField extends CustomField<DateTime> {

    private DateField datePart;
    private TextField timePart;
    private Button now;
    private Button clear;

    public DateTimeField() {
        datePart = new DateField();
        datePart.setResolution(Resolution.DAY);
        datePart.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                DateTime existingValue = getInternalValue();
                Date newValue = (Date) event.getProperty().getValue();
                if (newValue == null) {
                    setValue(null);
                } else {
                    if (existingValue == null) {
                        setValue(new DateTime(newValue));
                    } else {
                        setValue(new LocalDate(newValue).toDateTime(existingValue));
                    }
                }
            }
        });
        datePart.setImmediate(true);

        timePart = new TextField();
        timePart.setNullRepresentation("");
        timePart.setNullSettingAllowed(true);
        timePart.setDescription("HH:MM:SS");
        timePart.setWidth(6, Unit.EM);
        timePart.setConverter(new LocalTimeConverter());
        timePart.setEnabled(false);
        timePart.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                DateTime existingValue = getInternalValue();
                LocalTime newValue = (LocalTime) timePart.getConvertedValue();
                if (existingValue != null) {
                    setValue(newValue.toDateTime(existingValue));
                }
            }
        });
        timePart.setImmediate(true);

        now = new Button("Now", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setValue(DateTime.now());
            }
        });
        now.addStyleName(MockAppTheme.BUTTON_LINK);

        clear = new Button("Clear", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                setValue(null);
            }
        });
        clear.addStyleName(MockAppTheme.BUTTON_LINK);
    }

    public DateTimeField(String caption) {
        this();
        setCaption(caption);
    }

    @Override
    protected Component initContent() {
        HorizontalLayout layout = new HorizontalLayout(datePart, timePart, now, clear);
        layout.setSpacing(true);
        layout.setComponentAlignment(now, Alignment.MIDDLE_LEFT);
        layout.setComponentAlignment(clear, Alignment.MIDDLE_LEFT);
        return layout;
    }

    @Override
    protected void setInternalValue(DateTime newValue) {
        super.setInternalValue(newValue);
        if (newValue == null) {
            datePart.setValue(null);
            timePart.setValue(null);
            timePart.setEnabled(false);
        } else {
            datePart.setValue(newValue.toDate());
            timePart.setConvertedValue(newValue.toLocalTime());
            timePart.setEnabled(true);
        }
    }

    @Override
    public void setLocale(Locale locale) {
        super.setLocale(locale);
        datePart.setLocale(locale);
    }

    public String getDateFormat() {
        return datePart.getDateFormat();
    }

    public void setDateFormat(String dateFormat) {
        datePart.setDateFormat(dateFormat);
    }

    @Override
    public Class<? extends DateTime> getType() {
        return DateTime.class;
    }

    private static class LocalTimeConverter implements Converter<String, LocalTime> {

        @Override
        public LocalTime convertToModel(String value, Class<? extends LocalTime> targetType, Locale locale) throws ConversionException {
            if (value == null || value.isEmpty()) {
                return new LocalTime(0, 0);
            } else {
                try {
                    return LocalTime.parse(value);
                } catch (IllegalArgumentException e) {
                    throw new ConversionException(value + " is not a valid time", e);
                }
            }
        }

        @Override
        public String convertToPresentation(LocalTime value, Class<? extends String> targetType, Locale locale) throws ConversionException {
            return value == null ? null : value.toString("HH:mm:ss");
        }

        @Override
        public Class<LocalTime> getModelType() {
            return LocalTime.class;
        }

        @Override
        public Class<String> getPresentationType() {
            return String.class;
        }
    }
}
