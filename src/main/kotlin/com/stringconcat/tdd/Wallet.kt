package com.stringconcat.tdd

import com.stringconcat.tdd.Money.Companion.dollar
import com.stringconcat.tdd.Money.Companion.franc
import com.stringconcat.tdd.Money.Currency
import com.stringconcat.tdd.Money.Currency.CHF
import com.stringconcat.tdd.Money.Currency.USD

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

    fun asDollars(rate: Double): Money =
        dollar(amount = amountInTargetCurrency(rate, USD))

    fun asFranc(rate: Double): Money =
        franc(amount = amountInTargetCurrency(rate, CHF))

    private fun amountInTargetCurrency(rate: Double, targetCurrency: Currency): Double {
        return money.map {
            if (it.currency != targetCurrency) it.amount / rate
            else it.amount
        }.reduce(Double::plus)
    }
}
