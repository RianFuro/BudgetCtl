package transactions

import common.Money
import java.time.Instant
import java.util.*

data class Participant(val name: String)

data class Category(val name: String)

class Transaction(
        val id: UUID,
        amount: Money,
        val timestamp: Instant,
        val participant: Participant,
        val category: Category
) {
    var amount: Money = amount
        private set

    fun splitOff(amount: Money, category: Category): Transaction {
        this.amount -= amount
        return Transaction(UUID.randomUUID(), amount, timestamp, participant, category)
    }
}