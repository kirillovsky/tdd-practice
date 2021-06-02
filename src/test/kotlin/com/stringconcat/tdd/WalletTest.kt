package com.stringconcat.tdd

import com.stringconcat.tdd.Money.Companion.dollar
import com.stringconcat.tdd.Money.Companion.euro
import com.stringconcat.tdd.Money.Companion.franc
import com.stringconcat.tdd.Money.Currency.CHF
import com.stringconcat.tdd.Money.Currency.EUR
import com.stringconcat.tdd.Money.Currency.USD
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class WalletTest {
    @Test
    fun `shouldn't create wallet without money`() {
        val exception = shouldThrow<IllegalArgumentException> {
            Wallet()
        }

        exception.message shouldBe "Couldn't create wallet without money"
    }

    @Test
    fun `wallet containing 2 dollars is another wallet containing 2 dollars`() {
        Wallet(dollar(2.0)) shouldBe Wallet(dollar(2.0))
    }

    @Test
    fun `wallet that contains 2 USD returns 2 dollars regardless rate`() {
        Wallet(dollar(2.0)).toMoney(CurrencyExchanger(), USD) shouldBe dollar(2.0)
    }

    @Test
    fun `wallet that contains 2 CHF returns 1 dollars if rate CHF to USD = 2`() {
        val exchanger = CurrencyExchanger(CurrencyPair(CHF, USD) to 2.0)

        Wallet(franc(2.0)).toMoney(exchanger, USD) shouldBe dollar(4.0)
    }

    @Test
    fun `wallet that contains 2 USD and 4 CHF should returns 4 USD if rate CHF to USD = 2`() {
        val exchanger = CurrencyExchanger(CurrencyPair(CHF, USD) to 2.0)

        Wallet(dollar(2.0), franc(4.0)).toMoney(exchanger, USD) shouldBe dollar(10.0)
    }

    @Test
    fun `wallet that contains 2 USD and 5 CHF should returns 4 USD if rate CHF to USD = 5`() {
        val exchanger = CurrencyExchanger(CurrencyPair(CHF, USD) to 5.0)

        val actualMoney = Wallet(dollar(2.0), franc(5.0)).toMoney(exchanger, USD)

        actualMoney shouldBe dollar(27.0)
    }

    @Test
    fun `wallet that contains 2 CHF returns 1 CHF regardless rate`() {
        val actualMoney = Wallet(franc(2.0)).toMoney(CurrencyExchanger(), CHF)

        actualMoney shouldBe franc(2.0)
    }

    @Test
    fun `wallet that contains 2 USD returns 1 CHF if rate USD to CHF = 2`() {
        val exchanger = CurrencyExchanger(CurrencyPair(USD, CHF) to 2.0)

        val actualMoney = Wallet(dollar(2.0)).toMoney(exchanger, CHF)

        actualMoney shouldBe franc(4.0)
    }

    @Test
    fun `wallet that contains 2 USD and 4 CHF should returns 5 CHF if rate USD to CHF = 0,5`() {
        val exchanger = CurrencyExchanger(CurrencyPair(USD, CHF) to 0.5)

        val actualMoney = Wallet(dollar(2.0), franc(4.0)).toMoney(exchanger, CHF)

        actualMoney shouldBe franc(5.0)
    }

    @Test
    fun `wallet that contains 2 USD and 4 CHF should returns 8 CHF if rate USD to CHF = 2`() {
        val exchanger = CurrencyExchanger(CurrencyPair(USD, CHF) to 2.0)

        val actualMoney = Wallet(dollar(2.0), franc(4.0)).toMoney(exchanger, targetCurrency = CHF)

        actualMoney shouldBe franc(8.0)
    }

    @Test
    fun `wallet that contains 2 EUR and 1 CHF should returns 8,5 USD if rates EUR to USD = 2 and CHF to USD = 0,5`() {
        val exchanger = CurrencyExchanger(
            CurrencyPair(EUR, USD) to 2.0,
            CurrencyPair(CHF, USD) to 0.5
        )

        val actualMoney = Wallet(euro(2.0), franc(1.0)).toMoney(exchanger, targetCurrency = USD)

        actualMoney shouldBe dollar(4.5)
    }

    @Test
    fun `wallet that contains 4 USD and 2 CHF should returns 2,5 EUR if rates USD to EUR = 0,5 and CHF to EUR = 0,25`() {
        val exchanger = CurrencyExchanger(
            CurrencyPair(USD, EUR) to 0.5,
            CurrencyPair(CHF, EUR) to 0.25
        )

        val actualMoney = Wallet(dollar(4.0), franc(2.0)).toMoney(exchanger, targetCurrency = EUR)

        actualMoney shouldBe euro(2.5)
    }
}