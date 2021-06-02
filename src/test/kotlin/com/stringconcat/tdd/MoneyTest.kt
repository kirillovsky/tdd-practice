package com.stringconcat.tdd

import com.stringconcat.tdd.Money.Companion.dollar
import com.stringconcat.tdd.Money.Companion.euro
import com.stringconcat.tdd.Money.Companion.franc
import com.stringconcat.tdd.Money.Currency.USD
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

internal class MoneyTest {

    @Test
    fun `5 USD is 5 USD`() {
        dollar(5.0) shouldBe dollar(5.0)
    }

    @Test
    fun `5 USD is NOT 2 USD`() {
        dollar(5.0) shouldNotBe dollar(2.0)
    }

    @Test
    fun `5 USD * 2 is 10 USD`() {
        dollar(5.0) * 2 shouldBe dollar(10.0)
    }

    @Test
    fun `5 USD * 3 is 15 USD`() {
        dollar(5.0) * 3 shouldBe dollar(15.0)
    }

    @Test
    fun `5 CHF is 5 CHF`() {
        franc(5.0) shouldBe franc(5.0)
    }

    @Test
    fun `5 CHF is NOT 2 CHF`() {
        franc(5.0) shouldNotBe franc(2.0)
    }

    @Test
    fun `5 CHF * 2 is 10 CHF`() {
        franc(5.0) * 2 shouldBe franc(10.0)
    }

    @Test
    fun `5 CHF * 3 is 15 CHF`() {
        franc(5.0) * 3 shouldBe franc(15.0)
    }

    @Test
    fun `5 CHF + 5 CHF is 10 CHF`() {
        franc(5.0) + franc(5.0) shouldBe Wallet(franc(10.0))
    }

    @Test
    fun `5 USD + 5 USD is 10 USD`() {
        dollar(5.0) + dollar(5.0) shouldBe Wallet(dollar(10.0))
    }

    @Test
    fun `5 USD is not 5 CHF`() {
        dollar(5.0) shouldNotBe franc(5.0)
    }

    @Test
    fun `2 CHF + 4 USD is wallet that contains 2 CHF and 4 USD`() {
        franc(2.0) + dollar(4.0) shouldBe Wallet(franc(2.0), dollar(4.0))
    }

    @Test
    fun `4,2 USD is 4,2 USD`() {
        dollar(4.2) shouldBe dollar(4.2)
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
        euro(3.2) shouldBe euro(3.2)
    }

    @Test
    fun `same money should have some hashCodes`() {
        dollar(3.0).hashCode() shouldBe dollar(3.0).hashCode()
    }
}