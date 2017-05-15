package com.currency.exchange.builder;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.currency.exchange.exception.CurrencyFactorsNotFoundException;
import com.currency.exchange.model.CurrencyFactor;

public class CurrencyFactorBuilderTest {
    CurrencyFactorBuilder builder;

    @Before
    public void setUp() {
	try {
	    builder = new CurrencyFactorBuilder(
		    System.getProperty("user.dir") + "/src/test/resource/currencyFactors.txt");
	} catch (CurrencyFactorsNotFoundException e) {

	}
    }

    @Test
    public void testParseCurrencyFactors() throws CurrencyFactorsNotFoundException {

	assertEquals(10, builder.getCurrencyFactor().size());
    }

    @Test
    public void testParseCurrencyFactors1() throws CurrencyFactorsNotFoundException {

	assertEquals(11, builder.getValidCurrencyList().size());
    }

    @Test
    public void testParseCurrencyFactorsAndRunTimeRebuild() throws CurrencyFactorsNotFoundException {

	assertEquals(10, builder.getCurrencyFactor().size());
	builder.build();
	assertEquals(10, builder.getCurrencyFactor().size());
    }

    @Test
    public void testCurrencyListSource() throws CurrencyFactorsNotFoundException {

	List<CurrencyFactor> list = builder.getCurrencyFactorListForCurrency("USD", CurrencyFactor::getSourceCurrency);
	assertEquals(7, list.size());

	list = builder.getCurrencyFactorListForCurrency("CNY", CurrencyFactor::getSourceCurrency);
	assertEquals(1, list.size());

	list = builder.getCurrencyFactorListForCurrency("CXY", CurrencyFactor::getSourceCurrency);
	assertEquals(0, list.size());

	list = builder.getCurrencyFactorListForCurrency("CAD", CurrencyFactor::getDestinationCurrency);
	assertEquals(1, list.size());

    }

    @Test(expected = CurrencyFactorsNotFoundException.class)
    public void testParseInValidFile() throws CurrencyFactorsNotFoundException {
	CurrencyFactorBuilder builder = new CurrencyFactorBuilder(
		System.getProperty("user.dir") + "/src/test/resource/currencyFactors1.txt");
	assertEquals(10, builder.getCurrencyFactor().size());
    }

}
