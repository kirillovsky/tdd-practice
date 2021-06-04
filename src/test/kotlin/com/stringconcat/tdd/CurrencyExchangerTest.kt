package com.stringconcat.tdd

import com.stringconcat.tdd.Money.Currency.CHF
import com.stringconcat.tdd.Money.Currency.USD
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class CurrencyExchangerTest {
    @Test
    fun `exchanger should throw exception if it hasn't appropriate rate to exchange`() {
        val exception = shouldThrow<IllegalArgumentException> {
            CurrencyExchanger().exchange(1.0, USD, CHF)
        }

        exception.message shouldBe "Couldn't convert 1.0 USD to CHF"
    }

    @Test
    fun `exchanger should return amount if from and to currencies are equals`() {
        CurrencyExchanger().exchange(100500.0, USD, USD) shouldBe 100500.0
    }

    @Test
    fun `exchanger should convert amount using rate`() {
        val exchanger = CurrencyExchanger(CurrencyPair(USD, CHF) to 0.9)

        exchanger.exchange(10.0, USD, CHF) shouldBe 9.0
    }
}