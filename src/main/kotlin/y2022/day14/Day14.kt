package y2022.day14

import common.printSolutions
import kotlin.math.max
import kotlin.math.min


fun task1(input: List<String>): Int {
    val rowMax = input.maxOf { line -> line.split(" -> ").maxOf { it.split(",")[1].toInt() } }

    val cave = parseCave(input)

    var sandGrains = 0
    var grainPos = Coord(0, 500)
    while (grainPos.row <= rowMax) {
        if (cave[grainPos.down()] == null) {
            grainPos = grainPos.down()
        } else if (cave[grainPos.downLeft()] == null) {
            grainPos = grainPos.downLeft()
        } else if (cave[grainPos.downRight()] == null) {
            grainPos = grainPos.downRight()
        } else {
            cave[grainPos] = 'o'
            sandGrains++
            grainPos = Coord(0, 500)
        }
    }
    return sandGrains
}

private fun parseCave(input: List<String>): MutableMap<Coord, Char> {
    val cave = mutableMapOf<Coord, Char>()
    input.forEach { line ->
        val points = line.split(" -> ")
        for (i in 1 until points.size) {
            val prev = points[i - 1].toCoord()
            val next = points[i].toCoord()
            if (prev.row != next.row) {
                for (row in min(prev.row, next.row)..max(prev.row, next.row)) {
                    cave[Coord(row, prev.col)] = '#'
                }
            }
            if (prev.col != next.col) {
                for (col in min(prev.col, next.col)..max(prev.col, next.col)) {
                    cave[Coord(prev.row, col)] = '#'
                }
            }
        }
    }
    return cave
}

fun String.toCoord() = split(",").let { Coord(it[1].toInt(), it[0].toInt()) }

data class Coord(val row: Int, val col: Int) {
    fun down() = Coord(row + 1, col)
    fun downLeft() = Coord(row + 1, col - 1)
    fun downRight() = Coord(row + 1, col + 1)
}


fun task2(input: List<String>): Int {
    val rowMax = input.maxOf { line -> line.split(" -> ").maxOf { it.split(",")[1].toInt() } }

    val cave = parseCave(input)

    var sandGrains = 0
    val grainSource = Coord(0, 500)
    var grainPos = grainSource
    while (cave[grainSource] == null) {
        if (grainPos.row <= rowMax && cave[grainPos.down()] == null) {
            grainPos = grainPos.down()
        } else if (grainPos.row <= rowMax && cave[grainPos.downLeft()] == null) {
            grainPos = grainPos.downLeft()
        } else if (grainPos.row <= rowMax && cave[grainPos.downRight()] == null) {
            grainPos = grainPos.downRight()
        } else {
            cave[grainPos] = 'o'
            sandGrains++
            grainPos = grainSource
        }
    }
    return sandGrains
}


fun main() = printSolutions(14, 2022, { input -> task1(input) }, { input -> task2(input) })