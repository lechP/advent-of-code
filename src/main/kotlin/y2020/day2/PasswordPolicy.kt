package y2020.day2

import java.io.File

class PasswordPolicy {

    private fun String.toRecord(): Record {
        val parts = this.split(" ")
        val ocurrences = parts[0].split("-")
        return Record(
            ocurrences[0].toInt(),
            ocurrences[1].toInt(),
            parts[1].first(),
            parts[2]
        )
    }

    private fun String.isValid(): Boolean {
        val record = toRecord()
        val occurences = record.password.count { it == record.letter }
        return occurences in record.minOccurences..record.maxOccurences
    }

    fun countValid(input: List<String>): Int = input.count { it.isValid() }

    private fun String.toRecord2(): Record2 {
        val parts = this.split(" ")
        val ocurrences = parts[0].split("-")
        return Record2(
            ocurrences[0].toInt(),
            ocurrences[1].toInt(),
            parts[1].first(),
            parts[2]
        )
    }

    private fun String.isValid2(): Boolean {
        val record = toRecord2()
        val firstMatch = record.password[record.firstPosition - 1] == record.letter
        val secondMatch = record.password[record.secondPosition - 1] == record.letter
        return firstMatch.xor(secondMatch)
    }

    fun countValid2(input: List<String>): Int = input.count { it.isValid2() }

}

data class Record(
    val minOccurences: Int,
    val maxOccurences: Int,
    val letter: Char,
    val password: String
)

data class Record2(
    val firstPosition: Int,
    val secondPosition: Int,
    val letter: Char,
    val password: String
)

fun main() {
    val input = File("src/main/resources/y2020.input").useLines { it.toList() }
    println(PasswordPolicy().countValid(input))
    println(PasswordPolicy().countValid2(input))
}