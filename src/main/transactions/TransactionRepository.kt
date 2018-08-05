package transactions

interface TransactionRepository {
    fun all(): List<Transaction>
    fun add(t: Transaction): Unit
}