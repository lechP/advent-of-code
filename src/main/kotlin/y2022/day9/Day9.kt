package y2022.day9

import common.printSolutions
import kotlin.math.abs


fun task1(input: List<String>): Int {
    val instructions = input.map { it.toInstruction() }
    var head = Position()
    var tail = Position()
    val visitedPositions = mutableSetOf(tail)
    instructions.forEach { instruction ->
        for (i in 1..instruction.steps) {
            head = head.move(instruction.direction)
            tail = tail.moveAccordingly(head)
            visitedPositions.add(tail)
        }
    }
    return visitedPositions.size
}

fun String.toInstruction() = split(" ").let { Instruction(it[0], it[1].toInt()) }

fun task2(input: List<String>): Int {
    val instructions = input.map { it.toInstruction() }
    val tailSize = 9
    val rope = (0..tailSize).map { Position() }.toMutableList()
    val visitedPositions = mutableSetOf(rope.last())
    instructions.forEach { instruction ->
        for (i in 1..instruction.steps) {
            rope[0] = rope[0].move(instruction.direction)
            for (k in 1..tailSize) {
                rope[k] = rope[k].moveAccordingly(rope[k - 1])
            }
            visitedPositions.add(rope.last())
        }
    }
    return visitedPositions.size
}


fun main() = printSolutions(9, 2022, { input -> task1(input) }, { input -> task2(input) })

data class Instruction(
    val direction: String,
    val steps: Int
)

data class Position(
    val row: Int = 0,
    val column: Int = 0,
) {
    fun move(direction: String): Position = when (direction) {
        "R" -> Position(row, column + 1)
        "L" -> Position(row, column - 1)
        "U" -> Position(row + 1, column)
        "D" -> Position(row - 1, column)
        else -> throw RuntimeException("incorrect direction: $direction")
    }

    fun moveAccordingly(headPosition: Position): Position =
        if (abs(headPosition.column - column) == 2 && abs(headPosition.row - row) == 2) Position(
            (row + headPosition.row) / 2,
            (column + headPosition.column) / 2
        )
        else if (abs(headPosition.column - column) == 2) Position(headPosition.row, (column + headPosition.column) / 2)
        else if (abs(headPosition.row - row) == 2) Position((row + headPosition.row) / 2, headPosition.column)
        else this

    override fun toString(): String = "[$row, $column]"
}