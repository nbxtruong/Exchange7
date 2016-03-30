package exchange;

import java.util.HashMap;
import java.util.Map;

/**
 * The class Exchange allows to convert an amount in euro.
 * 
 * Version 7.
 */

public class Exchange {

	/*
	 * This version resolve the problem of the efficiency of the method
	 * currency(String).
	 */
	private static final Map<String, Currency> CURRENCIES = new HashMap<String, Currency>();

	static {
		String[] names = { "Franc", "Mark", "Dollard", "Euro" };

		double[] rates = { 6.55957, 1.95583, 1.2713, 1. };

		boolean[] variables = { false, false, true, false };

		for (int i = 0; i < names.length; ++i) {
			try {
				addCurrency(names[i], rates[i], variables[i]);
			} catch (ExistingCurrencyException e) {
				throw new Error(e.getMessage());
			}
		}
	}

	public static Currency currency(String name)
			throws UnknownCurrencyException {
		Currency c = CURRENCIES.get(name);
		if (c == null) {
			throw new UnknownCurrencyException(name);
		}
		return c;
	}

	public static boolean processedCurrency(String name) {
		return CURRENCIES.containsKey(name);
	}

	public static Currency[] processedCurrencies() {
		return CURRENCIES.values().toArray(new Currency[CURRENCIES.size()]);
	}

	public static void addCurrency(String name, double exchangeRate,
			boolean variable) throws ExistingCurrencyException {
		if (CURRENCIES.containsKey(name)) {
			throw new ExistingCurrencyException(CURRENCIES.get(name));
		}
		CURRENCIES.put(name, variable ? new VariableRateCurrency(name,
				exchangeRate) : new FixedRateCurrency(name, exchangeRate));
	}

	public static void addFixedRateCurreny(String name, double exchangeRate)
			throws ExistingCurrencyException {
		addCurrency(name, exchangeRate, false);
	}

	public static void addVariableRateCurreny(String name, double exchangeRate)
			throws ExistingCurrencyException {
		addCurrency(name, exchangeRate, true);
	}
}
