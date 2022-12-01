package common

import java.io.File

fun printSolutions(day: Int, year: Int, firstSolution: (List<String>) -> Any, secondSolution: (List<String>) -> Any) {
    val srcdir = "src/main/resources/y$year/day"
    val inputFile = "$srcdir$day/input"
    val testInputFile = "$srcdir$day/testInput"

    val testInput = File(testInputFile).useLines { it.toList() }
    val input = File(inputFile).useLines { it.toList() }


    println(firstSolution(testInput))
    println(firstSolution(input))
    println(secondSolution(testInput))
    println(secondSolution(input))
}


fun main() {
    val l = listOf(1, 2, 3, 4, 5, 6, 7, 8)
    val result = l.withIndex().groupBy { (index, _) -> index % 2 }.map { it.value.map { valueWithIndex -> valueWithIndex.value } }
    println(result)
}