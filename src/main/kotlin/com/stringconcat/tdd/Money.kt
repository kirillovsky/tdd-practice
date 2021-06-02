package com.stringconcat.tdd

import com.stringconcat.tdd.Money.Currency.CHF
import com.stringconcat.tdd.Money.Currency.EUR
import com.stringconcat.tdd.Money.Currency.USD

open class Money(
    val amount: Double,
    val currency: Currency,
) {
    enum class Currency {
        USD, CHF, EUR
    }

    companion object {
        fun dollar(amount: Double) = Money(amount, USD)
        fun franc(amount: Double) = Money(amount, CHF)
        fun euro(amount: Double) = Money(amount, EUR)
    }

    init {
        require(amount >= 0) {
            "Couldn't create money with negative amount"
        }
    }

    operator fun plus(other: Money): Wallet {
        return if (this.currency != other.currency) {
            Wallet(this, other)
        } else {
            Wallet(Money(this.amount + other.amount, this.currency))
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Money) return false
        return this.amount == other.amount && this.currency == other.currency
    }

    operator fun times(multiplier: Int): Money {
        return Money(amount * multiplier, currency)
    }

    override fun toString(): String {
        return "Money(amount=$amount, currency=$currency)"
    }
}