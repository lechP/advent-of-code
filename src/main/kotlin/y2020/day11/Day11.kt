package y2020.day11

import java.io.File
import java.lang.RuntimeException

enum class State {
    FLOOR, EMPTY, OCCUPIED
}

fun task1(input: List<String>): Int {
    return countOccupiedSeats(input) { area, i, j, rows, cols -> countState(area, i, j, rows, cols) }
}

fun task2(input: List<String>): Int {
    return countOccupiedSeats(input) { area, i, j, rows, cols -> countState2(area, i, j, rows, cols) }
}

fun List<List<State>>.draw() =
    joinToString("\n") {
        String(it.map { state ->
            when (state) {
                State.EMPTY -> 'L'
                State.FLOOR -> '.'
                State.OCCUPIED -> '#'
            }
        }.toCharArray())
    }

fun countOccupiedSeats(
    input: List<String>,
    nextState: (area: List<List<State>>, i: Int, j: Int, rows: Int, cols: Int) -> State
): Int {
    val rows = input.size
    val cols = input.first().length
    var area: List<List<State>> = input.map { row ->
        row.toCharArray().map {
            when (it) {
                'L' -> State.EMPTY
                '.' -> State.FLOOR
                '#' -> State.OCCUPIED
                else -> throw RuntimeException("unrecognized: $it")
            }
        }
    }

    var nextArea: MutableList<MutableList<State>> = MutableList(rows) { MutableList(cols) { State.FLOOR } }
    while (true) {
//        println(area.draw())
//        println("\n\n")
        for (i in 0 until rows)
            for (j in 0 until cols) {
                nextArea[i][j] = nextState(area, i, j, rows, cols)
            }

        if (area == nextArea) {
            return area.map { it.count { state -> state == State.OCCUPIED } }.sum()
        } else {
            area = nextArea
            nextArea = MutableList(rows) { MutableList(cols) { State.FLOOR } }
        }
    }
}


fun countState(area: List<List<State>>, i: Int, j: Int, rows: Int, cols: Int): State {
    var occupied = 0
    if (i > 0) {
        if (j > 0 && area[i - 1][j - 1] == State.OCCUPIED) occupied++
        if (area[i - 1][j] == State.OCCUPIED) occupied++
        if (j < cols - 1 && area[i - 1][j + 1] == State.OCCUPIED) occupied++
    }
    if (j > 0 && area[i][j - 1] == State.OCCUPIED) occupied++
    if (j < cols - 1 && area[i][j + 1] == State.OCCUPIED) occupied++
    if (i < rows - 1) {
        if (j > 0 && area[i + 1][j - 1] == State.OCCUPIED) occupied++
        if (area[i + 1][j] == State.OCCUPIED) occupied++
        if (j < cols - 1 && area[i + 1][j + 1] == State.OCCUPIED) occupied++
    }
    return when {
        area[i][j] == State.EMPTY && occupied == 0 -> State.OCCUPIED
        area[i][j] == State.OCCUPIED && occupied >= 4 -> State.EMPTY
        else -> area[i][j]
    }
}

fun countState2(area: List<List<State>>, i: Int, j: Int, rows: Int, cols: Int): State {
    var occupied = 0

    occupied += checkDirection(area, i, j, -1, -1) //NW -1 -1
    occupied += checkDirection(area, i, j, -1, 0) //N  -1  0
    occupied += checkDirection(area, i, j, -1, 1) //NE  -1  1
    occupied += checkDirection(area, i, j, 0, 1) //E  0  1
    occupied += checkDirection(area, i, j, 0, -1) //W  0  1
    occupied += checkDirection(area, i, j, 1, 1) //SE  1  1
    occupied += checkDirection(area, i, j, 1, 0) //S  1  0
    occupied += checkDirection(area, i, j, 1, -1) //SW  1  -1

    return when {
        area[i][j] == State.EMPTY && occupied == 0 -> State.OCCUPIED
        area[i][j] == State.OCCUPIED && occupied >= 5 -> State.EMPTY
        else -> area[i][j]
    }
}

fun checkDirection(area: List<List<State>>, i: Int, j: Int, iOffset: Int, jOffset: Int): Int {
    var multiplier = 1
    while (i + iOffset * multiplier in area.indices && j + jOffset * multiplier in area.first().indices) {
        if (area[i + iOffset * multiplier][j + jOffset * multiplier] == State.OCCUPIED) return 1
        if (area[i + iOffset * multiplier][j + jOffset * multiplier] == State.EMPTY) return 0
        multiplier++
    }
    return 0
}

const val day = 11
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