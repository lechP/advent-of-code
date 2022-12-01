package y2020.day10

import java.io.File

fun task1(input: List<String>): Int {
    val adapters = input.map { it.toInt() }.sorted()
    var ones = 1
    var threes = 1

    for (i in adapters.indices) {
        if (adapters[i + 1] - adapters[i] == 1) ones++
        if (adapters[i + 1] - adapters[i] == 3) threes++
    }

    return ones * threes
}

fun task2(input: List<String>): Long {
    val adapters = input.map { it.toInt() }.sorted()
    println(adapters)
    return count2(listOf(0) + adapters)
}

//3 - 2
//4 - 4
//5 - 7
//6 - 13
//7 - 24
// and determined that maxseqencelen = 5
// 2*4*7*7*7*7

fun count2(input: List<Int>): Long {
    var accumulator: Long = 1
    var index = 0
    var seqenceLength = 1
    while (index < input.size - 1) {
        while (index < input.size - 1 && input[index + 1] == input[index] + 1) {
            seqenceLength++
            index++
        }

        when (seqenceLength) {
            3 -> accumulator *= 2
            4 -> accumulator *= 4
            5 -> accumulator *= 7
            6 -> accumulator *= 13
        }
        seqenceLength = 1
        while (index < input.size - 1 && input[index + 1] == input[index] + 3) {
            index++
        }
    }
    return accumulator
}

const val day = 10
const val srcdir = "src/main/resources/y2020/day"
const val inputFile = "$srcdir$day/input"
const val testInputFile = "$srcdir$day/testInput"

fun main() {
    val testInput = File(testInputFile).useLines { it.toList() }
    val input = File(inputFile).useLines { it.toList() }


    println(task1(testInput))
    println(task1(input))
    println(task2(testInput))
    println(task2(input))
}