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
    // remember coordinates of '.' and '#'
    val cube: Map<Coordinate, Char> = rows.flatMapIndexed { rowId, row ->
        row.mapIndexedNotNull { colId, char ->
            if (char != ' ') {
                Coordinate(rowId, colId) to char
            } else null
        }
    }.toMap()

    var orientation = Orientation(Coordinate(row = 1, col = rows[1].indexOf('.')), "right")
    val (lengths, turns) = input.last().parseInstruction()

    for (i in turns.indices) {
        orientation = nextOrientation(orientation, lengths[i], cube)
        orientation = orientation.copy(direction = nextDirection(orientation.direction, turns[i]))
    }
    val finalOrientation = nextOrientation(orientation, lengths.last(), cube)

    return 1000 * finalOrientation.position.row + 4 * finalOrientation.position.col + facingValue(finalOrientation.direction)
}

fun nextOrientation(
    orientation: Orientation,
    moveLength: Int,
    cube: Map<Coordinate, Char>,
): Orientation {
    var current = orientation
    for (i in 1..moveLength) {
        val (currentRow, currentCol) = current.position

        val nextPosition = when (current.direction) {
            "up" -> Coordinate(currentRow - 1, currentCol)
            "down" -> Coordinate(currentRow + 1, currentCol)
            "left" -> Coordinate(currentRow, currentCol - 1)
            "right" -> Coordinate(currentRow, currentCol + 1)
            else -> throw RuntimeException("unrecognized direction: $current")
        }
        val next = if (cube.containsKey(nextPosition)) {
            Orientation(nextPosition, current.direction)
        } else {
            orientationMappings[current]!!
        }

        if (cube[next.position] == '#') {
            break
        } else {
            current = next
        }
    }
    return current
}

data class Orientation(
    val position: Coordinate,
    val direction: String,
)


fun main() = printSolutions(22, 2022, { input -> task1(input) }, { input -> task2(input) }, inputs = listOf("prod"))