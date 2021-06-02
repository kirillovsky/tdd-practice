package com.stringconcat.tdd

import com.stringconcat.tdd.Money.Currency

typealias CurrencyPair = Pair<Currency, Currency>

class CurrencyExchanger(vararg rates: Pair<CurrencyPair, Double>) {
    private val ratesByCurrencyPairs: Map<CurrencyPair, Double> = rates.toMap()

    fun exchange(amount: Double, from: Currency, to: Currency): Double {
        val rate = requireNotNull(findRate(from, to)) {
            "Couldn't convert $amount $from to $to"
        }

        return amount / rate
    }

    private fun findRate(from: Currency, to: Currency): Double? {
        if (from == to) return 1.0

        return ratesByCurrencyPairs[CurrencyPair(from, to)]
    }
}