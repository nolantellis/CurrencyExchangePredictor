package com.currency.exchange.model;

import java.text.DecimalFormat;
import java.util.Objects;

public class CurrencyExchange {

    String sourceCurrency;
    String destinationCurrency;
    Float sourceValue = Float.NaN;
    Float destinationValue = Float.NaN;

    public CurrencyExchange(String sourceCurrency, String destinationCurrency, float sourceValue) {

	this.sourceCurrency = sourceCurrency;
	this.destinationCurrency = destinationCurrency;
	this.sourceValue = sourceValue;
    }

    public String getSourceCurrency() {
	return sourceCurrency;
    }

    public String getDestinationCurrency() {
	return destinationCurrency;
    }

    public Float getSourceValue() {
	return sourceValue;
    }

    public Float getDestinationValue() {
	return destinationValue;
    }

    public void setDestinationValue(Float destinationValue) {
	this.destinationValue = destinationValue;
    }

    public String toString() {

	String source = "";
	String dest = "";

	source = getDisplayValue(sourceCurrency, sourceValue);
	dest = getDisplayValue(destinationCurrency, destinationValue);
	return sourceCurrency + " " + source + " = " + dest + " " + destinationCurrency;

    }

    private String getDisplayValue(String currency, Float value) {
	DecimalFormat df = new DecimalFormat();
	df.setMaximumFractionDigits(2);
	if ("JPY".equals(currency)) {

	    df.setMaximumFractionDigits(0);

	}
	return df.format(value.floatValue());
    }

}
