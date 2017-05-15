package com.currency.exchange.exception;

public class InvalidCurrencyExchangeException extends Exception {

    String message;

    public InvalidCurrencyExchangeException(String message) {
	super(message);
	this.message = message;
    }

}
