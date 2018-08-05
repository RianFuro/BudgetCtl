package infrastructure.console

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.double
import infrastructure.files.FileBasedTransactionRepository
import transactions.TransactionService
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val repo = FileBasedTransactionRepository("./transactions")
val service = TransactionService(repo)

class Main: CliktCommand() {
    override fun run() {

    }
}

class List: CliktCommand() {
    private val fromTimestampString by option("-f", "--from")

    override fun run() {
        var ts = service.getAllTransactions()

        if (!fromTimestampString.isNullOrBlank()) {
            val fromTimestamp = LocalDateTime.parse(fromTimestampString, DateTimeFormatter.ofPattern("y-M-d H:m"))
                    .toInstant(ZoneOffset.UTC)
            ts = ts.filter { it.timestamp >= fromTimestamp }
        }

        ts.forEach {
            println("${it.amount} at ${it.timestamp} for ${it.category} at ${it.participant}")
        }
    }
}

class Add: CliktCommand() {
    private val amount by option().double().required()
    private val participant by option().required()
    private val category by option().default("")

    override fun run() {
        service.addTransaction(amount, participant, category)
    }
}

fun main(args: Array<String>) = Main().subcommands(List(), Add()).main(args)