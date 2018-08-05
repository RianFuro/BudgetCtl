package common

data class Money(val cents: Int) {
    fun add(other: Money) = Money(cents + other.cents)
    fun sub(other: Money) = Money(cents - other.cents)

    /**
     * This function splits [cents] into [parts] equal parts.
     * Is [cents] not equally divisible it tries to deviate
     * minimally from the geometric shape (0, [cents])
     *
     * The mathematics taken for this approach are taken from here:
     * https://math.stackexchange.com/a/1081099
     *
     * This approach gives the following guarantees:
     * - The returned list always has [parts] elements
     * - The sum of the returned items is equal to [cents]
     */
    fun split(parts: Int) =
            (0 until parts).map {
                Math.floor(cents * (it + 1) / parts.toDouble()).toInt() -
                        Math.floor(cents * it / parts.toDouble()).toInt()
            }.map(::Money)

    fun asDouble(): Double = cents / 100.0

    operator fun minus(amount: Money) = sub(amount)
    operator fun plus(amount: Money) = add(amount)

    companion object {
        fun whole(amount: Int) = Money(amount * 100)
        fun parse(amount: Double): Money {
            val amountInCents = amount * 100

            if (remainderFrom(amountInCents) > 0)
                throw IllegalArgumentException()

            return Money(amountInCents.toInt())
        }
    }
}

fun remainderFrom(amount: Double) = amount - Math.floor(amount)