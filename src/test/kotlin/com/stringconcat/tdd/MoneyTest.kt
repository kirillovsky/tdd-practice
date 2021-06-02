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
    fun `5 dollars is 5 dollars`() {
        dollar(5.0) shouldBe dollar(5.0)
    }

    @Test
    fun `5 dollars is NOT 2 dollars`() {
        dollar(5.0) shouldNotBe dollar(2.0)
    }

    @Test
    fun `5 dollars * 2 is 10 dollars`() {
        dollar(5.0) * 2 shouldBe dollar(10.0)
    }

    @Test
    fun `5 dollars * 3 is 15 dollars`() {
        dollar(5.0) * 3 shouldBe dollar(15.0)
    }

    @Test
    fun `5 franc is 5 franc`() {
        franc(5.0) shouldBe franc(5.0)
    }

    @Test
    fun `5 franc is NOT 2 franc`() {
        franc(5.0) shouldNotBe franc(2.0)
    }

    @Test
    fun `5 franc * 2 is 10 franc`() {
        franc(5.0) * 2 shouldBe franc(10.0)
    }

    @Test
    fun `5 franc * 3 is 15 franc`() {
        franc(5.0) * 3 shouldBe franc(15.0)
    }

    @Test
    fun `5 CHF + 5 CHF is 10 CHF`() {
        franc(5.0) + franc(5.0) shouldBe Wallet(franc(10.0))
    }

    @Test
    fun `5 dollar + 5 dollar is 10 dollars`() {
        dollar(5.0) + dollar(5.0) shouldBe Wallet(dollar(10.0))
    }

    @Test
    fun `5 dollars is not 5 francs`() {
        dollar(5.0) shouldNotBe franc(5.0)
    }

    @Test
    fun `2 CHF + 4 USD is wallet that contains 2 CHF and 4 USD`() {
        franc(2.0) + dollar(4.0) shouldBe Wallet(franc(2.0), dollar(4.0))
    }

    @Test
    fun `4,2 dollars is 4,2 dollars`() {
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
    fun `3,2 euro is 3,3 euro`() {
        euro(3.2) shouldBe euro(3.2)
    }

    @Test
    fun `same money should have some hashCodes`() {
        dollar(3.0).hashCode() shouldBe dollar(3.0).hashCode()
    }
}