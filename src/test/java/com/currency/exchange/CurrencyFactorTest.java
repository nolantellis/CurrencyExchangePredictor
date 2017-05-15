package com.currency.exchange;

import static org.junit.Assert.*;

import org.junit.Test;

import com.currency.exchange.model.CurrencyFactor;

public class CurrencyFactorTest {

    @Test
    public void testCurrencyFactor() {
	CurrencyFactor factor1 = new CurrencyFactor("AUD", "USD", (float) 0.8371);
	CurrencyFactor factor2 = factor1.getInverseCurrencyFactor();

	assertFactors(factor1, factor2);

    }

    @Test
    public void testCurrencyFactor2() {

	CurrencyFactor factor1 = new CurrencyFactor("NZD", "USD", (float) 0.7750);
	CurrencyFactor factor2 = factor1.getInverseCurrencyFactor();
	assertFactors(factor1, factor2);

    }

    private void assertFactors(CurrencyFactor factor1, CurrencyFactor factor2) {
	assertEquals(factor1.getSourceCurrency(), factor2.getDestinationCurrency());
	assertEquals(factor2.getSourceCurrency(), factor1.getDestinationCurrency());
	assertEquals(factor2.getFactor(), 1 / factor1.getFactor(), 0.1);
    }

}
