package com.stringconcat.tdd

import com.stringconcat.tdd.Money.Currency
import com.stringconcat.tdd.Money.Currency.CHF
import com.stringconcat.tdd.Money.Currency.EUR
import com.stringconcat.tdd.Money.Currency.USD
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jdk.jfr.DataAmount
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.provider.CsvSource

internal class MoneyTest {
    @ParameterizedTest(name = "{0} {1} is not {2} {3}")
    @CsvSource(
        "5, USD, 2, USD",
        "5, CHF, 2, CHF",
        "5, USD, 5, CHF"
    )
    fun `money should be different with another money by amount and currency`(
        moneyAmount: Double,
        moneyCurrency: Currency,
        otherMoneyAmount: Double,
        otherMoneyCurrency: Currency,
    ) {
        Money(moneyAmount, moneyCurrency) shouldNotBe Money(otherMoneyAmount, otherMoneyCurrency)
    }

    @ParameterizedTest(name = "{0} {1} is {0} {1}")
    @CsvSource("5, USD", "5, CHF", "4.2, USD", "3.2, EUR")
    fun `money should be same with another money by amount and currency`(moneyAmount: Double, moneyCurrency: Currency) {
        Money(moneyAmount, moneyCurrency) shouldBe Money(moneyAmount, moneyCurrency)
    }

    @ParameterizedTest(name = "{0} {1} * {2} is {3} {1}")
    @CsvSource(
        "5, USD, 2, 10",
        "5, USD, 3, 15",
        "5, CHF, 2, 10",
        "5, CHF, 3, 15",
        "5, EUR, 2, 10",
        "5, EUR, 3, 15"
    )
    fun `money should be multiplied to int`(
        originalAmount: Double,
        currency: Currency,
        multiplier: Int,
        resultAmount: Double,
    ) {
        Money(originalAmount, currency) * multiplier shouldBe Money(resultAmount, currency)
    }

    @ParameterizedTest(name = "{0} {3} + {1} {3} is {2} {3}")
    @CsvSource(
        "5, 5, 10, USD",
        "6, 3, 9, CHF",
        "2, 9, 11, EUR",
    )
    fun `money should be added to another money with same currency`(
        amount: Double,
        otherAmount: Double,
        resultAmount: Double,
        currency: Currency,
    ) {
        Money(amount, currency) + Money(otherAmount, currency) shouldBe Wallet(Money(resultAmount, currency))
    }


    @ParameterizedTest(name = "money ({0}, {1}) should be added to another money ({2}, {3}) with another currency")
    @CsvSource(
        "2, CHF, 4, USD",
        "3, CHF, 5, EUR",
        "7, EUR, 9, USD"
    )
    fun `money should be added to another money with another currency`(
        amount: Double,
        currency: Currency,
        anotherAmount: Double,
        anotherCurrency: Currency,
    ) {
        val actualWallet = Money(amount, currency) + Money(anotherAmount, anotherCurrency)

        actualWallet shouldBe Wallet(Money(amount, currency), Money(anotherAmount, anotherCurrency))
    }

    @Test
    fun `shouldn't create money with negative amount`() {
        val exception = shouldThrow<IllegalArgumentException> {
            Money(-1.0, USD)
        }

        exception.message shouldBe "Couldn't create money with negative amount"
    }

    @Test
    fun `same money should have some hashCodes`() {
        Money(3.0, USD).hashCode() shouldBe Money(3.0, USD).hashCode()
    }
}