package common

import java.io.File

fun printSolutions(
    day: Int,
    year: Int,
    firstSolution: (List<String>) -> Any,
    secondSolution: (List<String>) -> Any,
    inputs: List<String> = listOf("test", "prod")
) {
    val srcdir = "src/main/resources/y$year/day"
    val inputFile = "$srcdir$day/input"
    val testInputFile = "$srcdir$day/testInput"

    val testInput = File(testInputFile).useLines { it.toList() }
    val input = File(inputFile).useLines { it.toList() }


    if("test" in inputs) {
        println(firstSolution(testInput))
        println(secondSolution(testInput))
    }
    if ("prod" in inputs) {
        println(firstSolution(input))
        println(secondSolution(input))
    }
}

fun printGrid(valuesMap: Map<Coordinate, Char>, rows: IntRange, cols: IntRange) {
    println()
    for (r in rows.reversed()) {
        for (c in cols) {
            print(valuesMap.getOrDefault(Coordinate(r, c), '.'))
        }
        println()
    }
}