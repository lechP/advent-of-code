package y2016.day1

import java.io.File
import kotlin.math.abs

data class Move(
    val direction: Char,
    val distance: Int
)

enum class Direction {
    N, W, S, E
}

fun task1(input: List<String>): Int {
    var horizontal = 0
    var vertical = 0
    var direction = Direction.N
    val path = input.first().split(", ").map { Move(it.first(), it.substring(1).toInt()) }

    path.forEach {
        when (direction) {
            Direction.N ->
                if (it.direction == 'L') {
                    horizontal -= it.distance
                    direction = Direction.W
                } else {
                    horizontal += it.distance
                    direction = Direction.E
                }
            Direction.W ->
                if (it.direction == 'L') {
                    vertical -= it.distance
                    direction = Direction.S
                } else {
                    vertical += it.distance
                    direction = Direction.N
                }
            Direction.S ->
                if (it.direction == 'L') {
                    horizontal += it.distance
                    direction = Direction.E
                } else {
                    horizontal -= it.distance
                    direction = Direction.W
                }
            Direction.E ->
                if (it.direction == 'L') {
                    vertical += it.distance
                    direction = Direction.N
                } else {
                    vertical -= it.distance
                    direction = Direction.S
                }
        }
    }

    return abs(vertical) + abs(horizontal)
}

fun task2(input: List<String>): Int {
    var horizontal = 0
    var vertical = 0
    var direction = Direction.N
    val locations = mutableSetOf<Pair<Int, Int>>()
    val path = input.first().split(", ").map { Move(it.first(), it.substring(1).toInt()) }

    path.forEach {
        when (direction) {
            Direction.N ->
                if (it.direction == 'L') {
                    val vector = (horizontal - 1 downTo horizontal - it.distance).map { Pair(it, vertical) }.toSet()
                    verifyIntersection(vector, locations)
                    horizontal -= it.distance
                    direction = Direction.W
                } else {
                    val vector = (horizontal + 1..horizontal + it.distance).map { Pair(it, vertical) }.toSet()
                    verifyIntersection(vector, locations)
                    horizontal += it.distance
                    direction = Direction.E
                }
            Direction.W ->
                if (it.direction == 'L') {
                    val vector = (vertical - 1 downTo vertical - it.distance).map { Pair(horizontal, it) }.toSet()
                    verifyIntersection(vector, locations)
                    vertical -= it.distance
                    direction = Direction.S
                } else {
                    val vector = (vertical + 1..vertical + it.distance).map { Pair(horizontal, it) }.toSet()
                    verifyIntersection(vector, locations)
                    vertical += it.distance
                    direction = Direction.N
                }
            Direction.S ->
                if (it.direction == 'L') {
                    val vector = (horizontal + 1..horizontal + it.distance).map { Pair(it, vertical) }.toSet()
                    verifyIntersection(vector, locations)
                    horizontal += it.distance
                    direction = Direction.E
                } else {
                    val vector = (horizontal - 1 downTo horizontal - it.distance).map { Pair(it, vertical) }.toSet()
                    verifyIntersection(vector, locations)
                    horizontal -= it.distance
                    direction = Direction.W
                }
            Direction.E ->
                if (it.direction == 'L') {
                    val vector = (vertical + 1..vertical + it.distance).map { Pair(horizontal, it) }.toSet()
                    verifyIntersection(vector, locations)
                    vertical += it.distance
                    direction = Direction.N
                } else {
                    val vector = (vertical - 1 downTo vertical - it.distance).map { Pair(horizontal, it) }.toSet()
                    verifyIntersection(vector, locations)
                    vertical -= it.distance
                    direction = Direction.S
                }
        }
    }
    return abs(vertical) + abs(horizontal)
}

fun verifyIntersection(vector: Set<Pair<Int, Int>>, locations: MutableSet<Pair<Int, Int>>) {
    val intersection = locations.intersect(vector)
    if (intersection.isNotEmpty()) {
        println(intersection.first().let { location -> abs(location.first) + abs(location.second) })
    } else {
        locations.addAll(vector)
    }
}

fun main() {
    val testInput = File("src/main/resources/y2016/day1/testInput").useLines { it.toList() }
    val testInput2 = File("src/main/resources/y2016/day1/testInput2").useLines { it.toList() }
    val input = File("src/main/resources/y2016/day1/input").useLines { it.toList() }

    println(task1(testInput))
    println(task1(input))
    println(task2(testInput2))
    println(task2(input))
}