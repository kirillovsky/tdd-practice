package com.stringconcat.tdd

data class Money(
    val amount: Double,
    val currency: Currency,
) {
    enum class Currency {
        USD, CHF, EUR
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

    operator fun times(multiplier: Int): Money {
        return Money(amount * multiplier, currency)
    }
}