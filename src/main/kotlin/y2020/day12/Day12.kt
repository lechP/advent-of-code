package y2020.day12

import java.io.File
import kotlin.math.abs

val directions = listOf("E", "S", "W", "N")

data class Instruction(
    val type: String,
    val value: Int
)

fun task1(input: List<String>): Any {
    var currentDir = "E"
    var distN = 0
    var distE = 0
    val instructions = input.map { Instruction(it.take(1), it.takeLast(it.length - 1).toInt()) }

    instructions.forEach {
        when (it.type) {
            "N" -> distN += it.value
            "S" -> distN -= it.value
            "E" -> distE += it.value
            "W" -> distE -= it.value
            "F" -> when (currentDir) {
                "N" -> distN += it.value
                "S" -> distN -= it.value
                "E" -> distE += it.value
                "W" -> distE -= it.value
            }
            "R" -> currentDir = directions[(directions.indexOf(currentDir) + it.value / 90) % 4]
            "L" -> currentDir = directions[(directions.indexOf(currentDir) - it.value / 90 + 4) % 4]
        }
    }

    return abs(distN) + abs(distE)
}

fun task2(input: List<String>): Any {
    var distN = 0
    var distE = 0
    val instructions = input.map { Instruction(it.take(1), it.takeLast(it.length - 1).toInt()) }
    var waypointE = 10
    var waypointN = 1

    instructions.forEach {
        when (it.type) {
            "N" -> waypointN += it.value
            "S" -> waypointN -= it.value
            "E" -> waypointE += it.value
            "W" -> waypointE -= it.value
            "F" -> {
                distN += waypointN * it.value
                distE += waypointE * it.value
            }
            "R" -> when (it.value / 90) {
                1 -> {
                    val newN = -waypointE
                    val newE = waypointN
                    waypointN = newN
                    waypointE = newE
                }
                2 -> {
                    waypointN = -waypointN
                    waypointE = -waypointE
                }
                3 -> {
                    val newN = waypointE
                    val newE = -waypointN
                    waypointN = newN
                    waypointE = newE
                }
            }
            "L" -> when(it.value / 90 ) {
                1 -> {
                    val newN = waypointE
                    val newE = -waypointN
                    waypointN = newN
                    waypointE = newE
                }
                2 -> {
                    waypointN = -waypointN
                    waypointE = -waypointE
                }
                3 -> {
                    val newN = -waypointE
                    val newE = waypointN
                    waypointN = newN
                    waypointE = newE
                }
            }
        }
    }

    return abs(distN) + abs(distE)
}

const val day = 12
const val srcdir = "src/main/resources/y2020/day"
const val inputFile = "$srcdir$day/input"
const val testInputFile = "$srcdir$day/testInput"

fun main() {

    println(-2 % 3)

    val testInput = File(testInputFile).useLines { it.toList() }
    val input = File(inputFile).useLines { it.toList() }


    println(task1(testInput))
    println(task1(input))
    println(task2(testInput))
    println(task2(input))
}