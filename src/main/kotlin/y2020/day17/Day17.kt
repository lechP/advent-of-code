package y2020.day17

import java.io.File

data class Dim(
    val x: Int,
    val y: Int,
    val z: Int
)

data class Dim4(
    val x: Int,
    val y: Int,
    val z: Int,
    val w: Int
)

fun task1(input: List<String>): Any {
    var grid: MutableMap<Dim, Boolean> = input.flatMapIndexed { y, line ->
        line.mapIndexed { x, char ->
            Pair(Dim(x, y, 0), char == '#')
        }
    }.toMap().toMutableMap()

    for (i in 1..6) {
        val minX = grid.keys.map { it.x }.minOrNull()!! - 1
        val maxX = grid.keys.map { it.x }.maxOrNull()!! + 1
        val minY = grid.keys.map { it.y }.minOrNull()!! - 1
        val maxY = grid.keys.map { it.y }.maxOrNull()!! + 1
        val minZ = grid.keys.map { it.z }.minOrNull()!! - 1
        val maxZ = grid.keys.map { it.z }.maxOrNull()!! + 1
        val copy = mutableMapOf<Dim, Boolean>()

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                for (z in minZ..maxZ) {
                    copy[Dim(x, y, z)] = getState(grid, x, y, z)
                }
            }
        }

        grid = copy
    }

    return grid.values.count { it }
}

fun getState(grid: Map<Dim, Boolean>, x: Int, y: Int, z: Int): Boolean {
    val neighbours = (x - 1..x + 1).flatMap { dx ->
        (y - 1..y + 1).flatMap { dy ->
            (z - 1..z + 1).map { dz ->
                Dim(dx, dy, dz)
            }
        }
    }.filter { it != Dim(x, y, z) }

    val activeNeighbours = neighbours.map {
        grid.getOrDefault(it, false)
    }.count { it }

    return if (grid.getOrDefault(Dim(x, y, z), false)) {
        activeNeighbours in 2..3
    } else {
        activeNeighbours == 3
    }
}

fun task2(input: List<String>): Any {
    var grid: MutableMap<Dim4, Boolean> = input.flatMapIndexed { y, line ->
        line.mapIndexed { x, char ->
            Pair(Dim4(x, y, 0, 0), char == '#')
        }
    }.toMap().toMutableMap()

    for (i in 1..6) {
        val minX = grid.keys.map { it.x }.minOrNull()!! - 1
        val maxX = grid.keys.map { it.x }.maxOrNull()!! + 1
        val minY = grid.keys.map { it.y }.minOrNull()!! - 1
        val maxY = grid.keys.map { it.y }.maxOrNull()!! + 1
        val minZ = grid.keys.map { it.z }.minOrNull()!! - 1
        val maxZ = grid.keys.map { it.z }.maxOrNull()!! + 1
        val minW = grid.keys.map { it.w }.minOrNull()!! - 1
        val maxW = grid.keys.map { it.w }.maxOrNull()!! + 1
        val copy = mutableMapOf<Dim4, Boolean>()

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                for (z in minZ..maxZ) {
                    for (w in minW..maxW) {
                        copy[Dim4(x, y, z, w)] = getState4(grid, x, y, z, w)
                    }
                }
            }
        }

        grid = copy
    }

    return grid.values.count { it }
}

fun getState4(grid: Map<Dim4, Boolean>, x: Int, y: Int, z: Int, w: Int): Boolean {
    val neighbours = (x - 1..x + 1).flatMap { dx ->
        (y - 1..y + 1).flatMap { dy ->
            (z - 1..z + 1).flatMap { dz ->
                (w - 1..w + 1).map { dw ->
                    Dim4(dx, dy, dz, dw)
                }
            }
        }
    }.filter { it != Dim4(x, y, z, w) }

    val activeNeighbours = neighbours.map {
        grid.getOrDefault(it, false)
    }.count { it }

    return if (grid.getOrDefault(Dim4(x, y, z, w), false)) {
        activeNeighbours in 2..3
    } else {
        activeNeighbours == 3
    }
}

const val day = 17
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