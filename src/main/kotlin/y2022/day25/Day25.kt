package y2022.day25

import common.printSolutions
import kotlin.math.pow


fun task1(input: List<String>): Int {
    val sum = input.sumOf { it.snafuNumber() }
    println(sum)
    // manual binary search
    println("2=020-===0-1===2=020".snafuNumber())
    return -1
}

private fun String.snafuNumber() = reversed().mapIndexed { index, char ->
    char.snafuDigit() * 5.0.pow(index).toLong()
}.sum()

private fun Char.snafuDigit() = when(this) {
    '2' -> 2
    '1' -> 1
    '0' -> 0
    '-' -> -1
    '=' -> -2
    else -> throw RuntimeException()
}

fun task2(input: List<String>): Int {
    return -1
}


fun main() = printSolutions(25, 2022, { input -> task1(input) }, { input -> task2(input) })