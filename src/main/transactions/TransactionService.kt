package transactions

import common.Money
import java.time.Instant
import java.util.*

data class TransactionDescriptor(
        val id: UUID,
        val amount: Double,
        val timestamp: Instant,
        val category: String,
        val participant: String
) {
    companion object {
        fun fromTransaction(t: Transaction): TransactionDescriptor =
                TransactionDescriptor(
                        t.id,
                        t.amount.asDouble(),
                        t.timestamp,
                        t.category.name,
                        t.participant.name
                )
    }
}

class TransactionService(private val repository: TransactionRepository) {
    fun getAllTransactions(): List<TransactionDescriptor> =
            repository.all().map(TransactionDescriptor.Companion::fromTransaction)
//
//    fun getTransactionsFrom(timestamp: Instant): List<TransactionDescriptor> =
//            repository.all().filter { it.timestamp >= timestamp }.map(TransactionDescriptor.Companion::fromTransaction)

    fun addTransaction(amount: Double, participant: String, category: String) =
            repository.add(Transaction(
                    UUID.randomUUID(),
                    Money((amount * 100).toInt()),
                    Instant.now(),
                    Participant(participant),
                    Category(category)
            ))
}