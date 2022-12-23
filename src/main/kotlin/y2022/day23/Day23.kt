package y2022.day23

import common.Coordinate
import common.printSolutions


fun task1(input: List<String>): Int {
    val elvesPositions = getElvesPositions(input)
    val directions = mutableListOf("N", "S", "W", "E")

    for (i in 1..10) {
        val proposals = elvesPositions.associateWith {
            moveProposal(it, elvesPositions, directions)
        }.filter { (_, v) -> v != null }
        val duplicates = proposals.values.groupBy { it!! }.filter { (_, v) -> v.size > 1 }.keys
        proposals.forEach { (current, next) ->
            if (next !in duplicates) {
                elvesPositions.remove(current)
                elvesPositions.add(next!!)
            }
        }

        directions.rotate()
    }

    val minRow = elvesPositions.minBy { it.row }.row
    val maxRow = elvesPositions.maxBy { it.row }.row
    val minCol = elvesPositions.minBy { it.col }.col
    val maxCol = elvesPositions.maxBy { it.col }.col

    return (maxRow - minRow + 1) * (maxCol - minCol + 1) - elvesPositions.size
}

private fun moveProposal(position: Coordinate, allPositions: Set<Coordinate>, directions: List<String>): Coordinate? =
    if (position.hasNoNeighbors(allPositions)) {
        null
    } else {
        directions.firstNotNullOfOrNull { direction ->
            if (position.directionNeighbors(direction).none { it in allPositions }) {
                position.moveInDirection(direction)
            } else null
        }
    }

private fun Coordinate.hasNoNeighbors(allPositions: Set<Coordinate>) =
    (-1..1).flatMap { r ->
        (-1..1).mapNotNull { c ->
            if(r==0 && c==0) null else Coordinate(row + r, col + c)
        }
    }.none { it in allPositions }

private fun Coordinate.directionNeighbors(direction: String): Set<Coordinate> =
    when (direction) {
        "N" -> (-1..1).map { Coordinate(row - 1, col + it) }.toSet()
        "S" -> (-1..1).map { Coordinate(row + 1, col + it) }.toSet()
        "W" -> (-1..1).map { Coordinate(row + it, col - 1) }.toSet()
        "E" -> (-1..1).map { Coordinate(row + it, col + 1) }.toSet()
        else -> throw RuntimeException()
    }

private fun Coordinate.moveInDirection(direction: String): Coordinate =
    when (direction) {
        "N" -> Coordinate(row - 1, col)
        "S" -> Coordinate(row + 1, col)
        "W" -> Coordinate(row, col - 1)
        "E" -> Coordinate(row, col + 1)
        else -> throw RuntimeException()
    }

private fun <E> MutableList<E>.rotate() {
    val first = removeFirst()
    add(first)
}

private fun getElvesPositions(input: List<String>) = input.flatMapIndexed { row, line ->
    line.mapIndexedNotNull { col, c ->
        if (c == '#') Coordinate(row, col) else null
    }
}.toMutableSet()

fun task2(input: List<String>): Int {
    val t0 = System.currentTimeMillis()
    val elvesPositions = getElvesPositions(input)
    val directions = mutableListOf("N", "S", "W", "E")

    var rounds = 0

    while (true) {
        rounds += 1
        val proposals = elvesPositions.associateWith {
            moveProposal(it, elvesPositions, directions)
        }.filter { (_, v) -> v != null }
        if (proposals.isEmpty()) {
            println(System.currentTimeMillis()-t0)
            return rounds
        }
        val duplicates = proposals.values.groupBy { it!! }.filter { (_, v) -> v.size > 1 }.keys
        proposals.forEach { (current, next) ->
            if (next !in duplicates) {
                elvesPositions.remove(current)
                elvesPositions.add(next!!)
            }
        }

        directions.rotate()
    }
}


fun main() = printSolutions(23, 2022, { input -> task1(input) }, { input -> task2(input) })