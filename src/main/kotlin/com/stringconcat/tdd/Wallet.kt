package com.stringconcat.tdd

import com.stringconcat.tdd.Money.Currency

class Wallet {
    private val monies: Map<Currency, Money>

    constructor(vararg money: Money) {
        require(money.isNotEmpty()) {
            "Couldn't create wallet without money"
        }
        this.monies = money.associateBy { it.currency }
    }

    private constructor(monies: Map<Currency, Money>) {
        this.monies = monies
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Wallet) return false
        return this.monies == other.monies
    }

    override fun toString(): String =
        "Wallet(monies=$monies)"

    fun toMoney(exchanger: CurrencyExchanger, targetCurrency: Currency): Money {
        val amount = monies.map { (_, money) ->
            exchanger.exchange(money.amount, from = money.currency, to = targetCurrency)
        }.reduce(Double::plus)

        return Money(amount, targetCurrency)
    }

    operator fun plus(money: Money): Wallet {
        val oldValue = monies[money.currency]
        val newValue = if (oldValue != null) {
            Money(oldValue.amount + money.amount, money.currency)
        } else money

        return Wallet(monies + (money.currency to newValue))
    }

    operator fun plus(wallet: Wallet): Wallet =
        monies.values.fold(wallet) { acc, money ->
            acc + money
        }
}

operator fun Money.plus(wallet: Wallet): Wallet = wallet + this