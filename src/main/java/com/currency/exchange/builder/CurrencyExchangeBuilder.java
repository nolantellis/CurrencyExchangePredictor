package com.currency.exchange.builder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.currency.exchange.exception.InvalidCurrencyExchangeException;
import com.currency.exchange.model.CurrencyExchange;

public class CurrencyExchangeBuilder {
    Set<String> validCurrencyList = new HashSet();
    private static String inputExpression = "[A-Z]{3} \\d+\\.{0,1}\\d* IN [A-Z]{3}";

    public Set<String> getValidCurrencyList() {
	return validCurrencyList;
    }

    public void setValidCurrencyList(Set<String> validCurrencyList) {
	this.validCurrencyList = validCurrencyList;
    }

    public CurrencyExchange parse(String currencyQuery) throws InvalidCurrencyExchangeException {
	if (Objects.isNull(currencyQuery)) {
	    throw new InvalidCurrencyExchangeException("Please provide an input. Example : AUD 100 IN USD");
	}

	currencyQuery = currencyQuery.toUpperCase();

	if (currencyQuery.matches(inputExpression)) {
	    String[] splitString = currencyQuery.split(" ");
	    String sourceCurrency = splitString[0];
	    String destinationCurrency = splitString[3];
	    float value = Float.valueOf(splitString[1]);

	    if (isValidCurrency(sourceCurrency) && isValidCurrency(destinationCurrency)) {
		return new CurrencyExchange(sourceCurrency, destinationCurrency, value);
	    } else {
		throw new InvalidCurrencyExchangeException(
			"Unable to find rate for " + sourceCurrency + "/" + destinationCurrency);
	    }

	} else {
	    throw new InvalidCurrencyExchangeException("Please provide valid input. Example : AUD 100 IN USD");
	}

    }

    private boolean isValidCurrency(String sourceCurrency) {
	return validCurrencyList.contains(sourceCurrency);
    }

}
