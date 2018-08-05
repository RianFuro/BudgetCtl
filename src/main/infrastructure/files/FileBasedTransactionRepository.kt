package infrastructure.files

import common.Money
import transactions.Category
import transactions.Participant
import transactions.Transaction
import transactions.TransactionRepository
import java.io.File
import java.time.Instant
import java.util.*

class FileBasedTransactionRepository(path: String) : TransactionRepository {
    private val fp = File(path)

    override fun all(): List<Transaction> = fp.readLines().map(this::transactionFromLine)

    override fun add(t: Transaction) {
        fp.appendText(transactionToLine(t))
    }

    private fun transactionFromLine(line: String): Transaction {
        val parts = line.split(':')
        return Transaction(
                UUID.fromString(parts[0]),
                Money(parts[1].toInt()),
                Instant.ofEpochMilli(parts[2].toLong()),
                Participant(parts[3]),
                Category(parts[4])
        )
    }

    private fun transactionToLine(t: Transaction): String =
            "${t.id}:${t.amount.cents}:${t.timestamp.toEpochMilli()}:${t.participant.name}:${t.category.name}\n"
}