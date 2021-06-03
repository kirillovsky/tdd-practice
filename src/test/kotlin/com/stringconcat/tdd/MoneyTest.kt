package com.stringconcat.tdd

import com.stringconcat.tdd.Money.Currency.CHF
import com.stringconcat.tdd.Money.Currency.EUR
import com.stringconcat.tdd.Money.Currency.USD
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class MoneyTest {

    @Test
    fun `5 USD is 5 USD`() {
        Money(5.0, USD) shouldBe Money(5.0, USD)
    }

    @Test
    fun `5 USD is NOT 2 USD`() {
        Money(5.0, USD) shouldNotBe Money(2.0, USD)
    }

    @Test
    fun `5 USD * 2 is 10 USD`() {
        Money(5.0, USD) * 2 shouldBe Money(10.0, USD)
    }

    @Test
    fun `5 USD * 3 is 15 USD`() {
        Money(5.0, USD) * 3 shouldBe Money(15.0, USD)
    }

    @Test
    fun `5 CHF is 5 CHF`() {
        Money(5.0, CHF) shouldBe Money(5.0, CHF)
    }

    @Test
    fun `5 CHF is NOT 2 CHF`() {
        Money(5.0, CHF) shouldNotBe Money(2.0, CHF)
    }

    @Test
    fun `5 CHF * 2 is 10 CHF`() {
        Money(5.0, CHF) * 2 shouldBe Money(10.0, CHF)
    }

    @Test
    fun `5 CHF * 3 is 15 CHF`() {
        Money(5.0, CHF) * 3 shouldBe Money(15.0, CHF)
    }

    @Test
    fun `5 CHF + 5 CHF is 10 CHF`() {
        Money(5.0, CHF) + Money(5.0, CHF) shouldBe Wallet(Money(10.0, CHF))
    }

    @Test
    fun `5 USD + 5 USD is 10 USD`() {
        Money(5.0, USD) + Money(5.0, USD) shouldBe Wallet(Money(10.0, USD))
    }

    @Test
    fun `5 USD is not 5 CHF`() {
        Money(5.0, USD) shouldNotBe Money(5.0, CHF)
    }

    @Test
    fun `2 CHF + 4 USD is wallet that contains 2 CHF and 4 USD`() {
        Money(2.0, CHF) + Money(4.0, USD) shouldBe Wallet(Money(2.0, CHF), Money(4.0, USD))
    }

    @Test
    fun `4,2 USD is 4,2 USD`() {
        Money(4.2, USD) shouldBe Money(4.2, USD)
    }

    @Test
    fun `shouldn't create money with negative amount`() {
        val exception = shouldThrow<IllegalArgumentException> {
            Money(-1.0, USD)
        }

        exception.message shouldBe "Couldn't create money with negative amount"
    }

    @Test
    fun `3,2 EUR is 3,3 EUR`() {
        Money(3.2, EUR) shouldBe Money(3.2, EUR)
    }

    @Test
    fun `same money should have some hashCodes`() {
        Money(3.0, USD).hashCode() shouldBe Money(3.0, USD).hashCode()
    }
}