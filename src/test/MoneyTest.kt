import org.junit.Assert.*
import org.junit.Test
import common.Money
import org.junit.rules.ExpectedException

class MoneyTest {
    @Test fun testExists() {
        Money(2000)
    }

    @Test fun testMoneyWithSameAmountsAreEqual() {
        val m1 = Money(1000)
        val m2 = Money(1000)

        assertTrue(m1 == m2)
    }

    @Test fun testHasConvenienceFactoryToConstructWholeAmounts() {
        val m = Money.whole(20)
        assertEquals(Money(2000), m)
    }

    @Test fun testCanAddMoreMoney() {
        val m = Money.whole(20)
        val m2 = m.add(Money.whole(30))

        assertEquals(Money.whole(50), m2)
        assertEquals(Money.whole(20), m)
    }

    @Test fun testCanSubtractMoney() {
        val m = Money.whole(50)
        val m2 = m.sub(Money.whole(20))

        assertEquals(Money.whole(30), m2)
        assertEquals(Money.whole(50), m)
    }

    @Test fun testCanSplitIntoEqualParts() {
        val m = Money.whole(50)
        val ms = m.split(2)

        assertEquals(2, ms.count())
        assertEquals(Money.whole(25), ms[0])
        assertEquals(Money.whole(25), ms[1])
    }

    @Test fun testSplitReturnsUnevenPartsIfAmountIsNotDivisible() {
        val m = Money(25)
        val ms = m.split(2)

        assertEquals(2, ms.count())
        assertEquals(Money(12), ms[0])
        assertEquals(Money(13), ms[1])
    }

    @Test fun testSplitForFloatingPointWeirdness() {
        run {
            val m = Money(1)
            val ms = m.split(3)

            assertEquals(3, ms.count())
            assertEquals(Money(0), ms[0])
            assertEquals(Money(0), ms[1])
            assertEquals(Money(1), ms[2])
        }

        run {
            val m = Money(8)
            val ms = m.split(3)

            assertEquals(3, ms.count())
            assertEquals(Money(2), ms[0])
            assertEquals(Money(3), ms[1])
            assertEquals(Money(3), ms[2])
        }
    }

    @Test fun testCanFormatToDouble() {
        val m = Money(1250)
        val d = m.asDouble()

        assertEquals(12.50, d, 0.0001)
    }

    @Test fun testCanBeCreatedFromDouble() {
        val m = Money.parse(12.50)
        assertEquals(Money(1250), m)
    }

    @Test(expected = IllegalArgumentException::class) fun testParsingDoubleThrowsIfTooPrecise() {
        Money.parse(12.503)
    }
}