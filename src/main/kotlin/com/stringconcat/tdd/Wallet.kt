package com.stringconcat.tdd

import com.stringconcat.tdd.Money.Currency

class Wallet(vararg val money: Money) {
    init {
        require(money.isNotEmpty()) {
            "Couldn't create wallet without money"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Wallet) return false
        return this.money.contentEquals(other.money)
    }

    override fun toString(): String {
        return "Wallet(money=${money.contentToString()})"
    }

    fun toMoney(exchanger: CurrencyExchanger, targetCurrency: Currency): Money {
        val amount = money.map {
            exchanger.exchange(it.amount, from = it.currency, to = targetCurrency)
        }.reduce(Double::plus)

        return Money(amount, targetCurrency)
    }
}
