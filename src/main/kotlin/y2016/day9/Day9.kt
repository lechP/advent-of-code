package y2016.day9

import common.printSolutions

data class Marker(
    val length: Int,
    val times: Int
)


fun String.toMarker() = replace("(", "").replace(")", "").split("x").let { Marker(it[0].toInt(), it[1].toInt()) }

fun String.applyMarker(marker: Marker) = this.take(marker.length).repeat(marker.times - 1) + this

fun String.decompress(): String = substringBefore("(").let {
    if (it == this) {
        it
    } else {
        val marker = substringAfter("(").substringBefore(")").toMarker()
        it + substringAfter(")").let { remainder ->
            remainder.take(marker.length).repeat(marker.times) + remainder.takeLast(remainder.length - marker.length)
                .decompress()
        }
    }
}

fun task1(input: List<String>): Int {
    return input.first().decompress().length
}

fun task2(input: List<String>): Long {

    return len(input.first())
}

fun len(input: String): Long {
    var remainder = input
    var len: Long = 0
    while (remainder.isNotEmpty()) {
        if (remainder.contains("AJTDYZC(23x5)LSLRJJOHFVRITWVIKBZLILU")) println("remainder len ${remainder.length} ")
        len += remainder.substringBefore("(").length
        if (remainder.contains("(")) {
            val marker = remainder.substringAfter("(").substringBefore(")").toMarker()
            len += len(remainder.substringAfter(")").take(marker.length).applyMarker(marker))
            remainder = remainder.substringAfter(")").let { it.takeLast(it.length - marker.length) }
        } else {
            remainder = ""
        }
    }

    return len
}

fun main() = printSolutions(9, 2016, { input -> task1(input) }, { input -> task2(input) })