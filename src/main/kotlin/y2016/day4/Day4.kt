package y2016.day4

import common.printSolutions

data class Room(
    val name: String,
    val sectorId: Int,
    val checksum: String
)

fun String.toRoom() =
    split("-").let {
        Room(
            name = it.subList(0, it.lastIndex).reduceRight { a, b -> a + b },
            sectorId = it.last().substringBefore("[").toInt(),
            checksum = it.last().substringAfter("[").substringBefore("]")
        )
    }

fun Room.isReal() =
    name.groupBy { it }.map { it.key to it.value.size }.sortedWith(compareBy({ -it.second }, { it.first }))
        .map { it.first }.take(5) == checksum.toCharArray().toList()


fun task1(input: List<String>): Int =
    input.map { it.toRoom() }.filter { it.isReal() }.sumBy { it.sectorId }

fun task2(input: List<String>): Int {
    input.map { it.toRoom().decrypt() }.filter { it.name.contains("north")}
        .forEach { println(it) }
    return -1
}

fun Room.decrypt() =
    copy(
        name = String(name.map { ((it.toInt() - 97 + sectorId % 26) % 26 + 97).toChar() }.toCharArray())
    )

fun main() = printSolutions(4, 2016, { input -> task1(input) }, { input -> task2(input) })