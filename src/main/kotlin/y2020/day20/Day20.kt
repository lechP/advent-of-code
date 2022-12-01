package y2020.day20

import java.io.File

data class Tile(
    val id: Long,
    val lines: List<String>
) {
    fun borders() = lines.borders()
    fun inside() = lines.subList(1, lines.lastIndex - 1).map { it.substring(1, it.lastIndex - 1) }
}

fun List<String>.toTiles() = windowed(11, 12).map {
    Tile(
        it.first().substringAfter(" ").substringBefore(":").toLong(),
        it.drop(1)
    )
}

fun task1(input: List<String>): Any {
    val tiles = input.toTiles()

    return tiles.filter { tile ->
        // if only two borders match
        tile.borders().filter { border ->
            tiles.filter { it.id != tile.id }
                .none { it.borders().contains(border) || it.borders().contains(border.reversed()) }
        }.size == 2
    }
        .map { it.id }
        .fold(1.toLong()) { a, b -> a * b }
}

fun task2(input: List<String>): Int {
    val tiles = input.toTiles()
    // find any corner
    val corner = tiles.first { tile ->
        // if only two borders match
        tile.borders().filter { border ->
            tiles.filter { it.id != tile.id }
                .none { it.borders().contains(border) || it.borders().contains(border.reversed()) }
        }.size == 2
    }
    // rotate to make it top-left
    val x = corner.borders()

    return -1
}

fun Tile.flipLeft(): Tile = copy(
    lines = lines.map { it.reversed() }
)

fun Tile.flipDown(): Tile = copy(
    lines = lines.reversed()
)

fun Tile.rotate90(): Tile = copy(
    lines = lines.indices.map { index -> String(lines.map { it[index] }.toCharArray()).reversed() }
)

fun Tile.rotate180(): Tile = flipLeft().flipDown()
fun Tile.rotate270(): Tile = rotate90().rotate180()

fun List<String>.borders() =
    listOf(
        first(), //top
        last(), //down
        map { it[0] }.joinToString(""), //left
        map { it[it.lastIndex] }.joinToString(""), //right
    )

const val day = 20
const val srcdir = "src/main/resources/y2020/day"
const val inputFile = "$srcdir$day/input"
const val testInputFile = "$srcdir$day/testInput"

fun main() {
    val testInput = File(testInputFile).useLines { it.toList() }
    val input = File(inputFile).useLines { it.toList() }



    println(task1(testInput))
    println(task1(input))
//    println(task2(testInput))
//    println(task2(input))
}