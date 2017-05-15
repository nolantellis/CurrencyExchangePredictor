package com.currency.exchange;

import static org.junit.Assert.assertEquals;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import com.currency.exchange.builder.CurrencyExchangeBuilder;
import com.currency.exchange.builder.CurrencyFactorBuilder;
import com.currency.exchange.exception.CurrencyFactorsNotFoundException;
import com.currency.exchange.exception.InvalidCurrencyExchangeException;
import com.currency.exchange.model.CurrencyExchange;

public class CurrencyFactorCalCulationTest {

    CurrencyFactorBuilder factorbuilder;
    CurrencyExchangeBuilder builder;

    @Before
    public void setUp() {
	try {
	    factorbuilder = new CurrencyFactorBuilder(
		    System.getProperty("user.dir") + "/src/test/resource/currencyFactors.txt");
	} catch (CurrencyFactorsNotFoundException e) {

	    e.printStackTrace();
	}

	builder = new CurrencyExchangeBuilder();
	builder.setValidCurrencyList(factorbuilder.getValidCurrencyList());

    }

    @Test
    public void testCalculation1() throws CurrencyFactorsNotFoundException, InvalidCurrencyExchangeException {

	CurrencyExchange actualEurrencyExchange = builder.parse("AUD 1000 in CNY");

	Function<Float, Float> calculator = factorbuilder.getCurrencyFactorEvaluationFunction(
		actualEurrencyExchange.getSourceCurrency(), actualEurrencyExchange.getDestinationCurrency());

	actualEurrencyExchange.setDestinationValue(calculator.apply(actualEurrencyExchange.getSourceValue()));

	assertEquals(actualEurrencyExchange.getDestinationValue(), 5166, .5);

    }

    @Test
    public void testCalculation2() throws CurrencyFactorsNotFoundException, InvalidCurrencyExchangeException {

	CurrencyExchange actualEurrencyExchange = builder.parse("AUD 100.00 in AUD");

	Function<Float, Float> calculator = factorbuilder.getCurrencyFactorEvaluationFunction(
		actualEurrencyExchange.getSourceCurrency(), actualEurrencyExchange.getDestinationCurrency());

	actualEurrencyExchange.setDestinationValue(calculator.apply(actualEurrencyExchange.getSourceValue()));

	assertEquals(actualEurrencyExchange.getDestinationValue(), 100, 0);

    }

    @Test
    public void testCalculationDirect() throws CurrencyFactorsNotFoundException, InvalidCurrencyExchangeException {

	CurrencyExchange actualEurrencyExchange = builder.parse("AUD 100.00 in USD");

	Function<Float, Float> calculator = factorbuilder.getCurrencyFactorEvaluationFunction(
		actualEurrencyExchange.getSourceCurrency(), actualEurrencyExchange.getDestinationCurrency());

	actualEurrencyExchange.setDestinationValue(calculator.apply(actualEurrencyExchange.getSourceValue()));

	assertEquals(actualEurrencyExchange.getDestinationValue(), 83.71, 0.5);

    }

    @Test
    public void testCalculationInDirect() throws CurrencyFactorsNotFoundException, InvalidCurrencyExchangeException {

	CurrencyExchange actualEurrencyExchange = builder.parse("USD 83.71 in AUD");

	Function<Float, Float> calculator = factorbuilder.getCurrencyFactorEvaluationFunction(
		actualEurrencyExchange.getSourceCurrency(), actualEurrencyExchange.getDestinationCurrency());

	actualEurrencyExchange.setDestinationValue(calculator.apply(actualEurrencyExchange.getSourceValue()));

	assertEquals(actualEurrencyExchange.getDestinationValue(), 100, 0.5);

    }

    @Test(expected = InvalidCurrencyExchangeException.class)
    public void testCalculationNoCurrency() throws CurrencyFactorsNotFoundException, InvalidCurrencyExchangeException {

	CurrencyExchange actualEurrencyExchange = builder.parse("KRW 1000.00 in FJD");

	Function<Float, Float> calculator = factorbuilder.getCurrencyFactorEvaluationFunction(
		actualEurrencyExchange.getSourceCurrency(), actualEurrencyExchange.getDestinationCurrency());

	actualEurrencyExchange.setDestinationValue(calculator.apply(actualEurrencyExchange.getSourceValue()));

	assertEquals(actualEurrencyExchange.getDestinationValue(), 100, 0.5);

    }

}
