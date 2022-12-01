package y2020.day15

import java.io.File

fun task1(input: List<String>): Any {
    val map =
        input.first().split(",").mapIndexed { index, s -> s.toInt() to index }.toMap().toMutableMap()
    var turn = map.size
    var num = 0
    var newNum: Int
    while (turn < 2019) { // counting for the next turn
        newNum = if (map.containsKey(num)) {
            turn - map[num]!!
        } else {
            0
        }
        map[num] = turn
        num = newNum
        turn++
    }
    return num
}

fun task2(input: List<String>): Any {

    return -1
}

const val day = 15
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