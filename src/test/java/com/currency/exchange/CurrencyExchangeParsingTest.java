package com.currency.exchange;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.currency.exchange.builder.CurrencyExchangeBuilder;
import com.currency.exchange.exception.InvalidCurrencyExchangeException;
import com.currency.exchange.model.CurrencyExchange;

public class CurrencyExchangeParsingTest {

    static Set<String> validCurrencyList = new HashSet<>();
    CurrencyExchangeBuilder builder;

    @BeforeClass
    public static void beforeClassSetup() {

	validCurrencyList.add("USD");
	validCurrencyList.add("INR");
	validCurrencyList.add("AUD");
	validCurrencyList.add("CAD");
	validCurrencyList.add("CNY");
	validCurrencyList.add("EUR");
	validCurrencyList.add("GPB");

    }

    @Before
    public void setup() {
	builder = new CurrencyExchangeBuilder();
	builder.setValidCurrencyList(validCurrencyList);

    }

    @Test
    public void testParsingValidUserInput() throws InvalidCurrencyExchangeException {
	CurrencyExchange expectedCurrencyExchange = new CurrencyExchange("AUD", "INR", 1000);
	CurrencyExchange actualEurrencyExchange = builder.parse("AUD 1000 in INR");
	assertCurrency(expectedCurrencyExchange, actualEurrencyExchange);
    }

    @Test
    public void testParsingValidUserInput1() throws InvalidCurrencyExchangeException {
	CurrencyExchange expectedCurrencyExchange = new CurrencyExchange("INR", "CAD", 1000);
	CurrencyExchange actualEurrencyExchange = builder.parse("INR 1000 in cad");
	assertCurrency(expectedCurrencyExchange, actualEurrencyExchange);
    }

    @Test
    public void testParsingValidUserInput2() throws InvalidCurrencyExchangeException {
	CurrencyExchange expectedCurrencyExchange = new CurrencyExchange("AUD", "AUD", (float) 1000.23456);
	CurrencyExchange actualEurrencyExchange = builder.parse("AUD 1000.23456 IN aud");
	System.out.println(actualEurrencyExchange);
	assertCurrency(expectedCurrencyExchange, actualEurrencyExchange);
    }

    @Test
    public void testParsingValidUserInput4() throws InvalidCurrencyExchangeException {
	CurrencyExchange expectedCurrencyExchange = new CurrencyExchange("AUD", "AUD", (float) 1000);
	CurrencyExchange actualEurrencyExchange = builder.parse("AUD 1000. IN aud");
	System.out.println(actualEurrencyExchange);
	assertCurrency(expectedCurrencyExchange, actualEurrencyExchange);
    }

    @Test(expected = InvalidCurrencyExchangeException.class)
    public void testParsingInValidValidUserInput() throws InvalidCurrencyExchangeException {

	CurrencyExchange actualEurrencyExchange = builder.parse("AUD 1000 to INR");

    }

    @Test(expected = InvalidCurrencyExchangeException.class)
    public void testParsingInValidValidUserInput3() throws InvalidCurrencyExchangeException {

	CurrencyExchange actualEurrencyExchange = builder.parse("AUD 1000 in INI");

    }

    @Test(expected = InvalidCurrencyExchangeException.class)
    public void testParsingInValidValidUserInput1() throws InvalidCurrencyExchangeException {
	CurrencyExchange actualEurrencyExchange = builder.parse("AUD 1000 to INR invalid");
    }

    @Test(expected = InvalidCurrencyExchangeException.class)
    public void testParsingInValidValidUserInput2() throws InvalidCurrencyExchangeException {
	CurrencyExchange actualEurrencyExchange = builder.parse(null);
    }

    private void assertCurrency(CurrencyExchange expectedCurrencyExchange, CurrencyExchange actualEurrencyExchange) {
	assertEquals(expectedCurrencyExchange.getSourceCurrency(), actualEurrencyExchange.getSourceCurrency());
	assertEquals(expectedCurrencyExchange.getDestinationCurrency(),
		actualEurrencyExchange.getDestinationCurrency());
	assertEquals(expectedCurrencyExchange.getSourceValue(), actualEurrencyExchange.getSourceValue());
    }

}
