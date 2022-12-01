package y2016.day2

import java.io.File
import kotlin.math.max
import kotlin.math.min

fun task1(input: List<String>): List<Int> {
    val keypad = listOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9))
    var position = 1 to 1
    var password = mutableListOf<Int>()

    input.forEach { line ->
        line.toCharArray().forEach {
            when (it) {
                'R' -> position = position.first to min(position.second + 1, 2)
                'L' -> position = position.first to max(position.second - 1, 0)
                'U' -> position = max(position.first - 1, 0) to position.second
                'D' -> position = min(position.first + 1, 2) to position.second
            }
        }
        password.add(keypad[position.first][position.second])
    }


    return password
}

fun task2(input: List<String>): List<Char> {
    val keypad = listOf(
        listOf('x', 'x', '1'),
        listOf('x', '2', '3', '4'),
        listOf('5', '6', '7', '8', '9'),
        listOf('x', 'A', 'B', 'C'),
        listOf('x', 'x', 'D')
    )
    val legalCoordinates =
        setOf(0 to 2, 1 to 1, 1 to 2, 1 to 3, 2 to 0, 2 to 1, 2 to 2, 2 to 3, 2 to 4, 3 to 1, 3 to 2, 3 to 3, 4 to 2)

    var position = 2 to 0
    var password = mutableListOf<Char>()

    input.forEach { line ->
        line.toCharArray().forEach {
            when (it) {
                'R' -> position =
                    if (legalCoordinates.contains(position.first to position.second + 1)) position.first to position.second + 1 else position
                'L' -> position =
                    if (legalCoordinates.contains(position.first to position.second - 1)) position.first to position.second - 1 else position
                'U' -> position =
                    if (legalCoordinates.contains(position.first - 1 to position.second)) position.first - 1 to position.second else position
                'D' -> position =
                    if (legalCoordinates.contains(position.first + 1 to position.second)) position.first + 1 to position.second else position
            }
        }
        password.add(keypad[position.first][position.second])
    }

    return password
}

fun main() {
    val testInput = File("src/main/resources/y2016/day2/testInput").useLines { it.toList() }
    val input = File("src/main/resources/y2016/day2/input").useLines { it.toList() }

    println(task1(testInput))
    println(task1(input))
    println(task2(testInput))
    println(task2(input))
}