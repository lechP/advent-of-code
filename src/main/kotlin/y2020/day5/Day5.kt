package y2020.day5

import java.io.File
import kotlin.math.pow


fun String.toSeatId(): Int {
    val rowBinary: List<Int> = substring(0, 7).toCharArray().map {
        when (it) {
            'B' -> 1
            'F' -> 0
            else -> 0
        }
    }
    var row = 0
    for (i in rowBinary.lastIndex downTo 0) {
        row += 2.toDouble().pow(i).toInt() * rowBinary[6 - i]
    }

    val columnBinary = substring(7).toCharArray().map {
        when (it) {
            'L' -> 0
            'R' -> 1
            else -> 0
        }
    }
    var column = 0
    for (i in columnBinary.lastIndex downTo 0) {
        column += 2.toDouble().pow(i).toInt() * columnBinary[2 - i]
    }

    return row * 8 + column
}

fun main() {
    val input = File("src/main/resources/y2020/day5/input").useLines { it.toList() }
    val seatIds: List<Int> = input.map { it.toSeatId() }.sorted()
    for (i in 0 until seatIds.lastIndex) {
        if(seatIds[i+1] != seatIds[i]+1) {
            println(seatIds[i])
            println(seatIds[i+1])
        }
    }
}

