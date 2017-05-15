package com.currency.exchange;

import java.util.Scanner;
import java.util.function.Function;

import com.currency.exchange.builder.CurrencyExchangeBuilder;
import com.currency.exchange.builder.CurrencyFactorBuilder;
import com.currency.exchange.exception.CurrencyFactorsNotFoundException;
import com.currency.exchange.exception.InvalidCurrencyExchangeException;
import com.currency.exchange.model.CurrencyExchange;

/**
 * Hello world!
 *
 */
public class CurrencyExchangeApplication {
    public static void main(String[] args) {
	// Need file argument as input
	if (args.length != 1) {
	    System.out.println("Please Enter relative or full file path to Currency Factors");
	    System.exit(0);
	}
	// Initialise builders
	CurrencyFactorBuilder factorbuilder = null;
	CurrencyExchangeBuilder builder;
	try {
	    factorbuilder = new CurrencyFactorBuilder(args[0]);
	} catch (CurrencyFactorsNotFoundException e) {

	    System.out.println(e.getMessage());
	    System.exit(0);
	}

	builder = new CurrencyExchangeBuilder();
	builder.setValidCurrencyList(factorbuilder.getValidCurrencyList());

	String input = "";
	Scanner sc = new Scanner(System.in);
	do {
	    // Do all the work
	    input = processInputs(factorbuilder, builder, sc);
	} while (!"bbye".equals(input));

    }

    private static String processInputs(CurrencyFactorBuilder factorbuilder, CurrencyExchangeBuilder builder,
	    Scanner sc) {
	String input;
	System.out.println("* Type expression Example : AUD 1000 in CNY");
	System.out.println("* Press 1 to reload new currency rate at runtime if added to file");
	System.out.println("* Type bbye to exit.");
	System.out.println("* Type Now..");

	input = sc.nextLine();

	switch (input) {
	case "1":
	    try {
		// Used to refresh the currencyFactors on change in file without restarting application
		factorbuilder.build();
		System.out.println("Factors Reloaded..!!");
	    } catch (CurrencyFactorsNotFoundException e1) {

		System.out.println("Unable to Reload currency Factors");
	    }
	    break;

	case "bbye":
	    System.exit(0);
	    break;

	default:
	    
	    Function<Float, Float> calculator;
	    try {
		// parse User input
		CurrencyExchange currencyExchange = builder.parse(input);
		
		// get chain of currencyfactors to evaluate.
		calculator = factorbuilder.getCurrencyFactorEvaluationFunction(currencyExchange.getSourceCurrency(),
			currencyExchange.getDestinationCurrency());

		// evaluate and set domain model.
		currencyExchange.setDestinationValue(calculator.apply(currencyExchange.getSourceValue()));

		// print output.
		System.out.println(currencyExchange);

	    } catch (CurrencyFactorsNotFoundException | InvalidCurrencyExchangeException e) {
		System.out.println(e.getMessage());
	    }
	}

	System.out.println("---------\n");
	return input;
    }
}
