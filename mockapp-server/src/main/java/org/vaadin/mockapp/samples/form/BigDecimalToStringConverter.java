//package org.vaadin.mockapp.samples.form;
//
//import com.vaadin.data.util.converter.Converter;
//
//import java.math.BigDecimal;
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.util.Locale;
//
///**
// * @author petter@vaadin.com
// */
//public class BigDecimalToStringConverter implements Converter<String, BigDecimal> {
//    @Override
//    public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale) throws ConversionException {
//        if (value == null || value.isEmpty()) {
//            return null;
//        } else {
//            if (locale == null) {
//                locale = Locale.getDefault();
//            }
//            try {
//                DecimalFormat df = (DecimalFormat) DecimalFormat.getNumberInstance(locale);
//                df.setParseBigDecimal(true);
//                return (BigDecimal) df.parse(value);
//            } catch (ParseException e) {
//                throw new ConversionException(e);
//            }
//        }
//    }
//
//    @Override
//    public String convertToPresentation(BigDecimal value, Class<? extends String> targetType, Locale locale) throws ConversionException {
//        if (locale == null) {
//            locale = Locale.getDefault();
//        }
//        return value == null ? null : DecimalFormat.getNumberInstance(locale).format(value);
//    }
//
//    @Override
//    public Class<BigDecimal> getModelType() {
//        return BigDecimal.class;
//    }
//
//    @Override
//    public Class<String> getPresentationType() {
//        return String.class;
//    }
//}
