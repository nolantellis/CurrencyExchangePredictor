package com.currency.exchange.exception;

public class CurrencyFactorsNotFoundException extends Exception {
    String message;

    public CurrencyFactorsNotFoundException(String message) {
	super(message);
	this.message = message;
    }

}
