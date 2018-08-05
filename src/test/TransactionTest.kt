import common.Money
import org.junit.Test
import org.junit.Assert.*
import transactions.Category
import transactions.Participant
import transactions.Transaction
import java.time.Instant
import java.util.*

class TransactionTest {
    @Test fun testHasAmount() {
        val t = makeTransaction(amount = -20)
        assertEquals(Money(-20), t.amount)
    }

    @Test fun testHasParticipant() {
        val t = makeTransaction(participant = "Billa")
        assertEquals(t.participant, Participant("Billa"))
    }

    @Test fun testHasCategory() {
        val t = makeTransaction(category = "Groceries")
        assertEquals(t.category, Category("Groceries"))
    }

    @Test fun testCanSplitOffDifferentCategory() {
        val newCategory = Category("Games")
        val t = makeTransaction(amount = -50, category = "Groceries")
        val t2 = t.splitOff(Money(-20), newCategory)

        assertEquals(newCategory, t2.category)
        assertEquals(Money(-20), t2.amount)
        assertEquals(Money(-30), t.amount)
    }

    private fun makeTransaction(
            amount: Int = 0,
            timestamp: Instant = Instant.now(),
            participant: String = "",
            category: String = "") =
            Transaction(UUID.randomUUID(), Money(amount), timestamp, Participant(participant), Category(category))
}