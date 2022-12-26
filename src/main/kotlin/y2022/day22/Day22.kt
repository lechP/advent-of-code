package y2022.day22

import common.Coordinate
import common.printSolutions


fun task1(input: List<String>): Int {
    val map = input.dropLast(1)
    val paddedRowLength = map.maxOf { it.length } + 2
    // add empty row at start, empty col at left and right
    val rows = listOf("".padEnd(paddedRowLength)) + map.map { " $it".padEnd(paddedRowLength) }
    val cols = rows.first().indices.map { r ->
        String(rows.map { it[r] }.toCharArray())
    }
    val (lengths, turns) = input.last().parseInstruction()

    var position = Coordinate(row = 1, col = map.first().indexOf('.'))
    var direction = "right"

    for (i in turns.indices) {
        position = nextPosition(position, direction, lengths[i], rows, cols)
        direction = nextDirection(direction, turns[i])
    }
    position = nextPosition(position, direction, lengths.last(), rows, cols)

    return 1000 * position.row + 4 * position.col + facingValue(direction)
}

fun facingValue(direction: String) = when (direction) {
    "right" -> 0
    "down" -> 1
    "left" -> 2
    "up" -> 3
    else -> throw RuntimeException("unrecognized direction: $direction")
}

fun String.firstCell() = if (indexOf('#') > -1) kotlin.math.min(indexOf('.'), indexOf('#')) else indexOf('.')
fun String.lastCell() = if (indexOf('#') > -1) kotlin.math.max(lastIndexOf('.'), lastIndexOf('#')) else lastIndexOf('.')

fun nextPosition(
    current: Coordinate,
    direction: String,
    moveLength: Int,
    rows: List<String>,
    cols: List<String>
): Coordinate {
    var currentPos = current
    for (i in 1..moveLength) {
        val (currentRow, currentCol) = currentPos
        val nextPos = when (direction) {
            "up" -> {
                val nextRow = currentRow - 1
                if (rows[nextRow][currentCol] != ' ') {
                    Coordinate(nextRow, currentCol)
                } else {
                    Coordinate(cols[currentCol].lastCell(), currentCol)
                }
            }

            "down" -> {
                val nextRow = currentRow + 1
                if (rows[nextRow][currentCol] != ' ') {
                    Coordinate(nextRow, currentCol)
                } else {
                    Coordinate(cols[currentCol].firstCell(), currentCol)
                }
            }

            "left" -> {
                val nextCol = currentCol - 1
                if (rows[currentRow][nextCol] != ' ') {
                    Coordinate(currentRow, nextCol)
                } else {
                    Coordinate(currentRow, rows[currentRow].lastCell())
                }
            }

            "right" -> {
                val nextCol = currentCol + 1
                if (rows[currentRow][nextCol] != ' ') {
                    Coordinate(currentRow, nextCol)
                } else {
                    Coordinate(currentRow, rows[currentRow].firstCell())
                }
            }

            else -> throw RuntimeException("unrecognized direction: $current")
        }
        if (rows[nextPos.row][nextPos.col] == '#') {
            break
        } else {
            currentPos = nextPos
        }
    }
    return currentPos
}

fun nextDirection(current: String, turn: Char) = when (current) {
    "up" -> if (turn == 'R') "right" else "left"
    "down" -> if (turn == 'R') "left" else "right"
    "left" -> if (turn == 'R') "up" else "down"
    "right" -> if (turn == 'R') "down" else "up"
    else -> throw RuntimeException("unrecognized direction: $current")
}

fun String.parseInstruction(): Pair<List<Int>, List<Char>> {
    val lengths = split('R').flatMap { it.split('L') }.map { it.toInt() }
    val dirs = filter { it == 'R' || it == 'L' }.toCharArray().toList()
    return lengths to dirs
}

fun task2(input: List<String>): Int {
    val map = input.dropLast(1)
    val paddedRowLength = map.maxOf { it.length } + 2
    // add empty row at start, empty col at left and right
    val rows = listOf("".padEnd(paddedRowLength)) + map.map { " $it".padEnd(paddedRowLength) }
    // jeszcze lepiej - zrob kostke i kazdy element niech zna swoich sasiadow
    val cube: Map<Coordinate, Char> = rows.flatMapIndexed { rowId, row ->
        row.mapIndexedNotNull { colId, char ->
            if (char != ' ') {
                Coordinate(rowId, colId) to char
            } else null
        }
    }.toMap()
    var position = Coordinate(row = 1, col = rows[1].indexOf('.'))
    var direction = "right"

    val (lengths, turns) = input.last().parseInstruction()
    val sideSize = input.first().trim().length
    val cubePassthroughs = cubePassthroughs(sideSize)
    val dirSwitches = dirSwitches(sideSize)

    for (i in turns.indices) {
        val res = nextPosition2(position, direction, lengths[i], cube, cubePassthroughs, dirSwitches)
        position = res.first
        direction = res.second
        direction = nextDirection(direction, turns[i])
    }
    val finalRes = nextPosition2(position, direction, lengths.last(), cube, cubePassthroughs, dirSwitches)
    position = finalRes.first

    return 1000 * position.row + 4 * position.col + facingValue(direction)
}

fun nextPosition2(
    current: Coordinate,
    direction: String,
    moveLength: Int,
    cube: Map<Coordinate, Char>,
    cubePassthroughs: Map<Coordinate, Coordinate>,
    dirSwitches: Map<Coordinate, String>,
): Pair<Coordinate, String> {
    var currentPos = current
    var currentDir = direction
    for (i in 1..moveLength) {
        val (currentRow, currentCol) = currentPos
        val nextPos = when (currentDir) {
            "up" -> {
                val nextRow = currentRow - 1
                val nextCandidate = Coordinate(nextRow, currentCol)
                if (cube.containsKey(nextCandidate)) {
                    nextCandidate
                } else {
                    currentDir = dirSwitches[currentPos]!!
                    cubePassthroughs[currentPos]!!
                }
            }

            "down" -> {
                val nextRow = currentRow + 1
                val nextCandidate = Coordinate(nextRow, currentCol)
                if (cube.containsKey(nextCandidate)) {
                    nextCandidate
                } else {
                    currentDir = dirSwitches[currentPos]!!
                    cubePassthroughs[currentPos]!!
                }
            }

            "left" -> {
                val nextCol = currentCol - 1
                val nextCandidate = Coordinate(currentRow, nextCol)
                if (cube.containsKey(nextCandidate)) {
                    nextCandidate
                } else {
                    currentDir = dirSwitches[currentPos]!!
                    cubePassthroughs[currentPos]!!
                }
            }

            "right" -> {
                val nextCol = currentCol + 1
                val nextCandidate = Coordinate(currentRow, nextCol)
                if (cube.containsKey(nextCandidate)) {
                    nextCandidate
                } else {
                    currentDir = dirSwitches[currentPos]!!
                    cubePassthroughs[currentPos]!!
                }
            }

            else -> throw RuntimeException("unrecognized direction: $current")
        }
        if (cube[nextPos] == '#') {
            break
        } else {
            currentPos = nextPos
        }
    }
    return currentPos to currentDir
}

fun List<Pair<Coordinate, Coordinate>>.andReverse() =
    this + map { (first, second) -> second to first }

fun IntProgression.col(col: Int) = map { Coordinate(it, col) }
fun IntProgression.row(row: Int) = map { Coordinate(row, it) }


fun main() = printSolutions(22, 2022, { input -> task1(input) }, { input -> task2(input) })