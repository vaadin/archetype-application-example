package org.vaadin.mockapp.samples.table;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.vaadin.data.util.converter.StringToBigDecimalConverter;

public class EuroConverter extends StringToBigDecimalConverter {

	private static String suffix = " €";

	@Override
	public BigDecimal convertToModel(String value,
			Class<? extends BigDecimal> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (value.endsWith(suffix)) {
			value = value.substring(0, value.length() - 1 - suffix.length());
		}
		return super.convertToModel(value, targetType, locale);
	}

	@Override
	protected NumberFormat getFormat(Locale locale) {
		// Always display currency with two decimals
		NumberFormat format = super.getFormat(locale);
		if (format instanceof DecimalFormat) {
			((DecimalFormat) format).setMaximumFractionDigits(2);
			((DecimalFormat) format).setMinimumFractionDigits(2);
		}
		return format;
	}

	@Override
	public String convertToPresentation(BigDecimal value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return super.convertToPresentation(value, targetType, locale) + suffix;
	}
}
