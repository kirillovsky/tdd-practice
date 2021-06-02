package com.stringconcat.tdd

import com.stringconcat.tdd.Money.Companion.dollar
import com.stringconcat.tdd.Money.Companion.franc
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import java.lang.IllegalArgumentException
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
        Wallet(dollar(2)) shouldBe Wallet(dollar(2))
    }

    @Test
    fun `wallet that contains 2 USD returns 2 dollars regardless rate`() {
        Wallet(dollar(2)).asDollars(100500.0) shouldBe dollar(2)
    }

    @Test
    fun `wallet that contains 2 CHF returns 1 dollars if rate 2 to 1`() {
        Wallet(franc(2)).asDollars(2.0) shouldBe dollar(1)
    }

    @Test
    fun `waller that contains 2 USD and 4 CHF should returns 4 USD if rate 2 to 1`() {
        Wallet(dollar(2), franc(4)).asDollars(2.0) shouldBe dollar(4)
    }

    @Test
    fun `waller that contains 2 USD and 5 CHF should returns 4 USD if rate 5 to 1`() {
        Wallet(dollar(2), franc(5)).asDollars(5.0) shouldBe dollar(3)
    }

    @Test
    fun `wallet that contains 2 CHF returns 1 CHF regardless rate`() {
        Wallet(franc(2)).asFranc(100500.0) shouldBe franc(2)
    }

    @Test
    fun `wallet that contains 2 USD returns 1 CHF if rate 2 to 1`() {
        Wallet(dollar(2)).asFranc(2.0) shouldBe franc(1)
    }

    @Test
    fun `waller that contains 2 USD and 4 CHF should returns 5 CHF if rate 2 to 1`() {
        Wallet(dollar(2), franc(4)).asFranc(2.0) shouldBe franc(5)
    }
}