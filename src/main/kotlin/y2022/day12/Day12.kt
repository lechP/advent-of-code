package y2022.day12

import common.printSolutions

data class Coord(
    val row: Int,
    val col: Int
)

fun task1(input: List<String>): Int {
    var start = Coord(-1, -1)
    var end = Coord(-1, -1)
    for (r in input.indices)
        for (c in input[0].indices) {
            if (input[r][c] == 'S') start = Coord(r, c)
            if (input[r][c] == 'E') end = Coord(r, c)
        }
    val terrain = input.map { it.replace('S', 'a').replace('E', 'z') }
    return bfs(start, end, terrain)
}

fun bfs(start: Coord, end: Coord, terrain: List<String>): Int {
    val directions = listOf(Coord(1, 0), Coord(-1, 0), Coord(0, 1), Coord(0, -1))

    val queue = mutableListOf(start)
    val visited = mutableSetOf(start)
    val path = mutableMapOf(start to emptyList<Coord>())

    val maxRow = terrain.size - 1
    val maxCol = terrain[0].length - 1

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        if (current == end) return (path[current]!! + current).size - 1
        directions.forEach { dir ->
            val next = Coord(current.row + dir.row, current.col + dir.col)
            if (next.row in 0..maxRow &&
                next.col in 0..maxCol &&
                next !in visited &&
                terrain[next.row][next.col] <= terrain[current.row][current.col] + 1
            ) {
                visited.add(next)
                queue.add(next)
                path[next] = path[current]!! + current
            }
        }
    }
    return Int.MAX_VALUE
}


fun task2(input: List<String>): Int {
    var end = Coord(-1, -1)
    for (r in input.indices)
        for (c in input[0].indices) {
            if (input[r][c] == 'E') end = Coord(r, c)
        }
    val terrain = input.map { it.replace('S', 'a').replace('E', 'z') }
    val starts = input.flatMapIndexed { rowIndex: Int, line: String ->
        line.mapIndexedNotNull { colIndex, char ->
            if (char == 'a') Coord(rowIndex, colIndex) else null
        }
    }
    return starts.minOf { bfs(it, end, terrain) }
}


fun main() = printSolutions(12, 2022, { input -> task1(input) }, { input -> task2(input) })