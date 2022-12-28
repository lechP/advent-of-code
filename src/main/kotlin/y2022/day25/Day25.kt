package y2022.day25

import common.printSolutions
import kotlin.math.pow


fun task1(input: List<String>): String {
    val sum = input.sumOf { it.fromSnafu() }
    return sum.toSnafu()
}

private fun Long.toBase(base: Int) =
    generateSequence(this) { it / base }.takeWhile { it != 0L }.map { it % base }.joinToString("").reversed()

private fun Long.toSnafu() = generateSequence(this) { (it + 2) / 5 }.takeWhile { it != 0L }.map {
    "012=-"[(it % 5).toInt()]
}.joinToString("").reversed()

private fun String.fromSnafu() = reversed().mapIndexed { index, char ->
    char.snafuDigit() * 5.0.pow(index).toLong()
}.sum()

private fun Char.snafuDigit() = when (this) {
    '2' -> 2
    '1' -> 1
    '0' -> 0
    '-' -> -1
    '=' -> -2
    else -> throw RuntimeException()
}

fun main() = printSolutions(25, 2022, { input -> task1(input) }, { })