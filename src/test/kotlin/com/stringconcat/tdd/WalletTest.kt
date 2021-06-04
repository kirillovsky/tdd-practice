package com.stringconcat.tdd

import com.stringconcat.tdd.Money.Currency
import com.stringconcat.tdd.Money.Currency.CHF
import com.stringconcat.tdd.Money.Currency.EUR
import com.stringconcat.tdd.Money.Currency.USD
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class WalletTest {
    @Test
    fun `shouldn't create wallet without money`() {
        val exception = shouldThrow<IllegalArgumentException> {
            Wallet()
        }

        exception.message shouldBe "Couldn't create wallet without money"
    }

    @Test
    fun `wallet should be equals to another wallet with same money`() {
        Wallet(Money(2.0, USD)) shouldBe Wallet(Money(2.0, USD))
    }

    @Test
    fun `wallet should be equals to another wallet by containing money regardless it's order`() {
        val actualWallet = Wallet(Money(2.0, USD), Money(3.0, EUR), Money(4.0, CHF))

        actualWallet shouldBe Wallet(Money(3.0, EUR), Money(2.0, USD), Money(4.0, CHF))
    }

    @ParameterizedTest(name = "wallet should convert {0} {1} to {1} regardless rate")
    @CsvSource("2, CHF", "3, USD")
    fun `wallet should convert own money to targetCurrency regardless rate`(amount: Double, targetCurrency: Currency) {
        val actualConvertedMoney = Wallet(Money(amount, targetCurrency)).toMoney(CurrencyExchanger(), targetCurrency)

        actualConvertedMoney shouldBe Money(amount, targetCurrency)
    }

    @ParameterizedTest(name = "wallet should convert own money ({0}, {1}) to another currency ({2}) using rate 1:{3}")
    @CsvSource(
        "2, USD, CHF, 2, 4",
        "2, CHF, USD, 2, 4",
    )
    fun `wallet should convert own money to another currency using rate`(
        convertedAmount: Double,
        convertedCurrency: Currency,
        targetCurrency: Currency,
        rateValue: Double,
        expectedAmount: Double
    ) {
        val exchanger = CurrencyExchanger(CurrencyPair(convertedCurrency, targetCurrency) to rateValue)
        val originalMoney = Money(convertedAmount, convertedCurrency)
        val actualMoney = Wallet(originalMoney).toMoney(exchanger, targetCurrency)

        actualMoney shouldBe Money(expectedAmount, targetCurrency)
    }

    @ParameterizedTest
    @CsvSource(
        "2, USD, CHF, 2, 8",
        "2, USD, EUR, 0.5, 5",
        "4, CHF, USD, 2, 12",
        "5, CHF, USD, 5, 29",
    )
    fun `wallet should convert own monies to another currency using rate if it needs`(
        convertedAmount: Double,
        convertedCurrency: Currency,
        targetCurrency: Currency,
        rateValue: Double,
        resultAmount: Double
    ) {
        val exchanger = CurrencyExchanger(CurrencyPair(convertedCurrency, targetCurrency) to rateValue)
        val convertedMoney = Money(convertedAmount, convertedCurrency)
        val wallet = Wallet(convertedMoney, Money(4.0, targetCurrency))

        val actualMoney = wallet.toMoney(exchanger, targetCurrency)

        actualMoney shouldBe Money(resultAmount, targetCurrency)
    }

    @Test
    fun `wallet should convert own monies to another currency using different rates`() {
        val exchanger = CurrencyExchanger(
            CurrencyPair(USD, EUR) to 0.5,
            CurrencyPair(CHF, EUR) to 0.25
        )

        val actualMoney = Wallet(Money(4.0, USD), Money(2.0, CHF)).toMoney(exchanger, EUR)

        actualMoney shouldBe Money(2.5, EUR)
    }

    @Test
    fun `wallet should be added with money with another currency`() {
        val actualWallet = Wallet(Money(30.0, USD)) + Money(30.0, CHF)

        actualWallet shouldBe Wallet(Money(30.0, USD), Money(30.0, CHF))
    }

    @Test
    fun `wallet should be added with money with same currency`() {
        val actualWallet = Wallet(Money(30.0, USD), Money(30.0, CHF)) + Money(30.0, USD)

        actualWallet shouldBe Wallet(Money(60.0, USD), Money(30.0, CHF))
    }

    @Test
    fun `money should be added with wallet with another currency`() {
        val actualWallet = Money(3.0, EUR) + Wallet(Money(2.0, USD))

        actualWallet shouldBe Wallet(Money(2.0, USD), Money(3.0, EUR))
    }

    @Test
    fun `wallet should be added with another wallet by merge it's moneys`() {
        val actualWallet = Wallet(Money(30.0, USD), Money(20.0, CHF)) + Wallet(Money(20.0, CHF), Money(30.0, EUR))

        actualWallet shouldBe Wallet(Money(30.0, USD), Money(30.0, EUR), Money(40.0, CHF))
    }
}