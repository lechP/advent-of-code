package y2022.day6

import common.printSolutions

fun String.indexAfterUnique(length: Int): Int {
    windowed(length).forEachIndexed { index, string ->
        if (string.toCharArray().toSet().size == length) return index + length
    }
    return -1
}

fun task1(input: List<String>) = input.map {
    it.indexAfterUnique(4)
}

fun task2(input: List<String>): List<Int> = input.map {
    it.indexAfterUnique(14)
}


fun main() = printSolutions(6, 2022, { input -> task1(input) }, { input -> task2(input) })