package y2020.day24

import java.io.File

fun task1(input: List<String>): Int {

    return -1
}

fun task2(input: List<String>): Int {

    return -1
}

const val day = 24
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