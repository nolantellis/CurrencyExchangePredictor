package com.currency.exchange.model;

public class CurrencyFactor {
    String sourceCurrency;
    String destinationCurrency;
    float factor;

    public CurrencyFactor(String sourceCurrency, String destinationCurrency, float factor) {

	this.sourceCurrency = sourceCurrency;
	this.destinationCurrency = destinationCurrency;
	this.factor = factor;
    }

    public String getSourceCurrency() {
	return sourceCurrency;
    }

    public String getDestinationCurrency() {
	return destinationCurrency;
    }

    public float getFactor() {
	return factor;
    }

    public CurrencyFactor getInverseCurrencyFactor() {
	return new CurrencyFactor(destinationCurrency, sourceCurrency, (float) 1 / factor);

    }

    public float getValue(float sourceValue) {
	return sourceValue * factor;
    }

}
