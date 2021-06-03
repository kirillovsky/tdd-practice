package com.stringconcat.tdd

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
    fun `wallet containing 2 USD is another wallet containing 2 USD`() {
        Wallet(Money(2.0, USD)) shouldBe Wallet(Money(2.0, USD))
    }

    @Test
    fun `wallet containing 2 USD, 3 EUR and 4 CHF is another wallet containing 3 EUR, 2 USD and 4 CHF`() {
        val actualWallet = Wallet(Money(2.0, USD), Money(3.0, EUR), Money(4.0, CHF))

        actualWallet shouldBe Wallet(Money(3.0, EUR), Money(2.0, USD), Money(4.0, CHF))
    }

    @Test
    fun `wallet that contains 2 USD returns 2 USD regardless rate`() {
        Wallet(Money(2.0, USD)).toMoney(CurrencyExchanger(), USD) shouldBe Money(2.0, USD)
    }

    @Test
    fun `wallet that contains 2 CHF returns 1 USD if rate CHF to USD = 2`() {
        val exchanger = CurrencyExchanger(CurrencyPair(CHF, USD) to 2.0)

        Wallet(Money(2.0, CHF)).toMoney(exchanger, USD) shouldBe Money(4.0, USD)
    }

    @Test
    fun `wallet that contains 2 USD and 4 CHF should returns 4 USD if rate CHF to USD = 2`() {
        val exchanger = CurrencyExchanger(CurrencyPair(CHF, USD) to 2.0)

        Wallet(Money(2.0, USD), Money(4.0, CHF)).toMoney(exchanger, USD) shouldBe Money(10.0, USD)
    }

    @Test
    fun `wallet that contains 2 USD and 5 CHF should returns 4 USD if rate CHF to USD = 5`() {
        val exchanger = CurrencyExchanger(CurrencyPair(CHF, USD) to 5.0)

        val actualMoney = Wallet(Money(2.0, USD), Money(5.0, CHF)).toMoney(exchanger, USD)

        actualMoney shouldBe Money(27.0, USD)
    }

    @Test
    fun `wallet that contains 2 CHF returns 1 CHF regardless rate`() {
        val actualMoney = Wallet(Money(2.0, CHF)).toMoney(CurrencyExchanger(), CHF)

        actualMoney shouldBe Money(2.0, CHF)
    }

    @Test
    fun `wallet that contains 2 USD returns 1 CHF if rate USD to CHF = 2`() {
        val exchanger = CurrencyExchanger(CurrencyPair(USD, CHF) to 2.0)

        val actualMoney = Wallet(Money(2.0, USD)).toMoney(exchanger, CHF)

        actualMoney shouldBe Money(4.0, CHF)
    }

    @Test
    fun `wallet that contains 2 USD and 4 CHF should returns 5 CHF if rate USD to CHF = 0,5`() {
        val exchanger = CurrencyExchanger(CurrencyPair(USD, CHF) to 0.5)

        val actualMoney = Wallet(Money(2.0, USD), Money(4.0, CHF)).toMoney(exchanger, CHF)

        actualMoney shouldBe Money(5.0, CHF)
    }

    @Test
    fun `wallet that contains 2 USD and 4 CHF should returns 8 CHF if rate USD to CHF = 2`() {
        val exchanger = CurrencyExchanger(CurrencyPair(USD, CHF) to 2.0)

        val actualMoney = Wallet(Money(2.0, USD), Money(4.0, CHF)).toMoney(exchanger, targetCurrency = CHF)

        actualMoney shouldBe Money(8.0, CHF)
    }

    @Test
    fun `wallet that contains 2 EUR and 1 CHF should returns 8,5 USD if rates EUR to USD = 2 and CHF to USD = 0,5`() {
        val exchanger = CurrencyExchanger(
            CurrencyPair(EUR, USD) to 2.0,
            CurrencyPair(CHF, USD) to 0.5
        )

        val actualMoney = Wallet(Money(2.0, EUR), Money(1.0, CHF)).toMoney(exchanger, targetCurrency = USD)

        actualMoney shouldBe Money(4.5, USD)
    }

    @Test
    fun `wallet that contains 4 USD and 2 CHF should returns 2,5 EUR if rates USD to EUR = 0,5 and CHF to EUR = 0,25`() {
        val exchanger = CurrencyExchanger(
            CurrencyPair(USD, EUR) to 0.5,
            CurrencyPair(CHF, EUR) to 0.25
        )

        val actualMoney = Wallet(Money(4.0, USD), Money(2.0, CHF)).toMoney(exchanger, targetCurrency = EUR)

        actualMoney shouldBe Money(2.5, EUR)
    }

    @Test
    fun `wallet that contains 30 USD plus 30 CHF should be wallet that contains 30 USD and 30 CHF`() {
        val actualWallet = Wallet(Money(30.0, USD)) + Money(30.0, CHF)

        actualWallet shouldBe Wallet(Money(30.0, USD), Money(30.0, CHF))
    }

    @Test
    fun `wallet that contains 30 USD and 30 CHF plus 30 USD should be wallet that contains 60 USD and 30 CHF`() {
        val actualWallet = Wallet(Money(30.0, USD), Money(30.0, CHF)) + Money(30.0, USD)

        actualWallet shouldBe Wallet(Money(60.0, USD), Money(30.0, CHF))
    }

    @Test
    fun `3 EUR plus wallet with 2 USD should be wallet with 3 EUR and 2 USD`() {
        val actualWallet = Money(3.0, EUR) + Wallet(Money(2.0, USD))

        actualWallet shouldBe Wallet(Money(2.0, USD), Money(3.0, EUR))
    }

    @Test
    fun `wallet should be added with another wallet by merge it's moneys`() {
        val actualWallet = Wallet(Money(30.0, USD), Money(20.0, CHF)) + Wallet(Money(20.0, CHF), Money(30.0, EUR))

        actualWallet shouldBe Wallet(Money(30.0, USD), Money(30.0, EUR), Money(40.0, CHF))
    }
}