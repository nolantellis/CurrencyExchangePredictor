package com.currency.exchange.builder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.currency.exchange.exception.CurrencyFactorsNotFoundException;
import com.currency.exchange.model.CurrencyFactor;

public class CurrencyFactorBuilder {
    String file;
    Path path;
    List<CurrencyFactor> currencyFactor = new ArrayList<>();
    Set<String> validCurrencyList = new HashSet<>();

    public CurrencyFactorBuilder(String file) throws CurrencyFactorsNotFoundException {
	this.file = file;
	path = Paths.get(file);
	build();
    }

    public List<CurrencyFactor> getCurrencyFactor() {
	return currencyFactor;
    }

    public Set<String> getValidCurrencyList() {
	return validCurrencyList;
    }

    public void build() throws CurrencyFactorsNotFoundException {
	currencyFactor.clear();
	if (Files.exists(path)) {
	    try (Stream<String> lines = Files.lines(path)) {
		// Read All data in list
		lines.map((line) -> {
		    line = line.toUpperCase();
		    String[] splitLines = line.split(",");
		    validCurrencyList.add(splitLines[0]);
		    validCurrencyList.add(splitLines[1]);
		    return new CurrencyFactor(splitLines[0], splitLines[1], Float.valueOf(splitLines[2]));
		}).forEach(currencyFactor::add);
	    } catch (IOException e) {
		// This should not come up.
		throw new CurrencyFactorsNotFoundException("Exception file not found");
	    }
	} else {
	    throw new CurrencyFactorsNotFoundException("Currency Factor File not Found");
	}
    }

    public List<CurrencyFactor> getCurrencyFactorListForCurrency(String currency,
	    Function<CurrencyFactor, String> evaluationFunction) {

	return currencyFactor.stream().filter((e) -> {
	    return (currency.equals(e.getDestinationCurrency()) || currency.equals(e.getSourceCurrency()));
	}).map((e) -> {
	    return currency.equals(evaluationFunction.apply(e)) ? e : e.getInverseCurrencyFactor();
	}).collect(Collectors.toList());

    }

    public Function<Float, Float> getCurrencyFactorEvaluationFunction(String sourceCurrency, String destinationCurrency)
	    throws CurrencyFactorsNotFoundException {

	Function<Float, Float> evalFunction = null;

	// Same currency
	if (sourceCurrency.equals(destinationCurrency)) {
	    return (e) -> e;
	}

	// CurrencyFactors provided in file
	Optional<CurrencyFactor> directCurrencyFactor = getDirectCurrencyFactor(sourceCurrency, destinationCurrency);

	if (directCurrencyFactor.isPresent()) {
	    return directCurrencyFactor.get()::getValue;

	}
	
	// CurrencyFactors provided in file in inverse order

	Optional<CurrencyFactor> inDirectCurrencyFactor = getDirectCurrencyFactor(destinationCurrency, sourceCurrency);

	if (inDirectCurrencyFactor.isPresent()) {
	    return inDirectCurrencyFactor.get().getInverseCurrencyFactor()::getValue;

	}

	// Chaining of currency factors using base currency.
	
	List<CurrencyFactor> sourceCurrencyList = getCurrencyFactorListForCurrency(sourceCurrency,
		CurrencyFactor::getSourceCurrency);

	List<CurrencyFactor> destinationCurrencyList = getCurrencyFactorListForCurrency(destinationCurrency,
		CurrencyFactor::getDestinationCurrency);

	List<String> sourceBaseCurrencyList = sourceCurrencyList.stream().map(CurrencyFactor::getDestinationCurrency)
		.sorted().collect(Collectors.toList());
	List<String> destinationBaseCurrencyList = destinationCurrencyList.stream()
		.map(CurrencyFactor::getSourceCurrency).sorted().collect(Collectors.toList());

	// contains all base currencylist.
	sourceBaseCurrencyList.retainAll(destinationBaseCurrencyList);
	if (sourceBaseCurrencyList.isEmpty()) {
	    throw new CurrencyFactorsNotFoundException("No Exchange Rate Found");
	}

	String baseCurrency = sourceBaseCurrencyList.get(0);

	// Get evaluation function.
	evalFunction = sourceCurrencyList.stream().filter((e) -> {
	    return (sourceCurrency.equals(e.getSourceCurrency()) && baseCurrency.equals(e.getDestinationCurrency()));
	}).findAny().get()::getValue;

	// chain evaluation function with second currency factor.
	
	evalFunction = evalFunction.andThen((Function<Float, Float>) destinationCurrencyList.stream().filter((e) -> {
	    return (baseCurrency.equals(e.getSourceCurrency())
		    && destinationCurrency.equals(e.getDestinationCurrency()));
	}).findAny().get()::getValue);

	return evalFunction;

    }

    private Optional<CurrencyFactor> getDirectCurrencyFactor(String sourceCurrency, String destinationCurrency) {

	return currencyFactor.stream().filter((e) -> {

	    return (sourceCurrency.equals(e.getSourceCurrency())
		    && destinationCurrency.equals(e.getDestinationCurrency()));
	}).findAny();
    }

}
