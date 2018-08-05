package infrastructure.files.migrations

import java.io.File
import java.time.Instant

fun main(args: Array<String>) {
    val file = File("./transactions")
    val migratedFile = file.readLines().map { it.split(":") }.map {
        "${it[0]}:${it[1]}:${Instant.now().toEpochMilli()}:${it[2]}:${it[3]}"
    }.joinToString("\n")
    file.writeText(migratedFile)
}