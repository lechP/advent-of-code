package y2022.day24

import common.Coordinate
import common.printSolutions


fun task1(input: List<String>): Int {
    //up (^), down (v), left (<), or right (>)
    val dirs = setOf('^', 'v', '<', '>')
    val maxRowIdx = input.size - 1
    val maxColIdx = input.first().length - 1

    val blizzards = input.flatMapIndexed { row, line ->
        line.mapIndexedNotNull { col, c ->
            if (c in dirs) Coordinate(row, col) to listOf(c) else null
        }
    }.toMap()
    val start = Coordinate(0, input.first().indexOf('.'))
    val end = Coordinate(maxRowIdx, input.last().indexOf('.'))

//    var nextBlizzards = move(blizzards, maxRowIdx, maxColIdx)
//    var count = 1
//    while (nextBlizzards != blizzards) {
//        nextBlizzards = move(nextBlizzards, maxRowIdx, maxColIdx)
//        count += 1
//    }
    // blizzards have cycles of 12/600 - can be used to optimise

    return bfs(start, end, blizzards, maxRowIdx, maxColIdx)
}

fun Map<Coordinate, List<Char>>.print(
    maxRowIdx: Int,
    maxColIdx: Int
) {
    println("_________")
    for (r in 0..maxRowIdx) {
        for (c in 0..maxColIdx) {
            if (r == 0 || c == 0 || r == maxRowIdx || c == maxColIdx) {
                print('#')
            } else if (containsKey(Coordinate(r, c))) {
                if (this[Coordinate(r, c)]!!.size == 1) {
                    print(this[Coordinate(r, c)]!!.first())
                } else {
                    print(this[Coordinate(r, c)]!!.size)
                }
            } else print('.')
        }
        println()
    }
}

fun bfs(
    start: Coordinate,
    end: Coordinate,
    blizzards: Map<Coordinate, List<Char>>,
    maxRowIdx: Int,
    maxColIdx: Int
): Int {
    val t0 = System.currentTimeMillis()
    val queue = mutableListOf(start to 0)
    val path = mutableMapOf((start to 0) to emptyList<Coordinate>())
    val visited = mutableSetOf(start to 0)
    val blizzardsCache = mutableMapOf(0 to move(blizzards, maxRowIdx, maxColIdx))
    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        val currentPos = current.first
        val timeElapsed = current.second
        if (currentPos == end) {
            println(System.currentTimeMillis() - t0)
            return (path[current]!! + current).size - 1
        }
        if (!blizzardsCache.containsKey(timeElapsed)) {
            blizzardsCache[timeElapsed] =
                move(blizzardsCache[timeElapsed - 1]!!, maxRowIdx, maxColIdx)
        }
        val nextBlizzards = blizzardsCache[path[current]!!.size]!!
        val possibleMoves = possibleMoves(currentPos, start, end, nextBlizzards, maxRowIdx, maxColIdx)
        possibleMoves.forEach { nextPos ->
            val next = nextPos to timeElapsed + 1
            if (next !in visited) {
                queue.add(next)
                visited.add(next)
                path[next] = path[current]!! + currentPos
            }
        }
    }

    return Int.MAX_VALUE
}

fun bfs2(
    start: Coordinate,
    end: Coordinate,
    blizzards: Map<Coordinate, List<Char>>,
    maxRowIdx: Int,
    maxColIdx: Int
): Pair<Int, Map<Coordinate, List<Char>>> {
    val queue = mutableListOf(start to 0)
    val path = mutableMapOf((start to 0) to emptyList<Coordinate>())
    val visited = mutableSetOf(start to 0)
    val blizzardsCache = mutableMapOf(0 to move(blizzards, maxRowIdx, maxColIdx))
    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        val currentPos = current.first
        val timeElapsed = current.second
        if (currentPos == end) {
            return ((path[current]!! + current).size - 1) to move(
                blizzardsCache[timeElapsed - 1]!!,
                maxRowIdx,
                maxColIdx
            )
        }
        if (!blizzardsCache.containsKey(timeElapsed)) {
            blizzardsCache[timeElapsed] =
                move(blizzardsCache[timeElapsed - 1]!!, maxRowIdx, maxColIdx)
        }
        val nextBlizzards = blizzardsCache[path[current]!!.size]!!
        val possibleMoves = possibleMoves(currentPos, start, end, nextBlizzards, maxRowIdx, maxColIdx)
        possibleMoves.forEach { nextPos ->
            val next = nextPos to timeElapsed + 1
            if (next !in visited) {
                queue.add(next)
                visited.add(next)
                path[next] = path[current]!! + currentPos
            }
        }
    }

    return Int.MAX_VALUE to emptyMap()
}

fun possibleMoves(
    current: Coordinate,
    start: Coordinate,
    end: Coordinate,
    blizzards: Map<Coordinate, List<Char>>,
    maxRowIdx: Int,
    maxColIdx: Int
): List<Coordinate> {
    val candidates = current.directNeighbors() + current
    return if (end in candidates) {
        listOf(end)
    } else {
        candidates
            .filter { (it.row in 1 until maxRowIdx && it.col in 1 until maxColIdx) || current in setOf(start, end) }
            .filterNot { blizzards.containsKey(it) }
    }
}

fun move(blizzards: Map<Coordinate, List<Char>>, maxRowIdx: Int, maxColIdx: Int): Map<Coordinate, List<Char>> {
    val result = mutableMapOf<Coordinate, List<Char>>()
    blizzards.forEach { (coordinate, chars) ->
        chars.forEach { dir ->
            val next = move(coordinate, dir, maxRowIdx, maxColIdx)
            result[next] = result.getOrDefault(next, emptyList()) + dir
        }
    }
    return result.toMap()
}

fun move(coordinate: Coordinate, dir: Char, maxRowIdx: Int, maxColIdx: Int): Coordinate =
    when (dir) {
        '^' -> if (coordinate.row == 1) {
            Coordinate(maxRowIdx - 1, coordinate.col)
        } else {
            coordinate.up()
        }

        'v' -> if (coordinate.row == maxRowIdx - 1) {
            Coordinate(1, coordinate.col)
        } else {
            coordinate.down()
        }

        '<' -> if (coordinate.col == 1) {
            Coordinate(coordinate.row, maxColIdx - 1)
        } else {
            coordinate.left()
        }

        '>' -> if (coordinate.col == maxColIdx - 1) {
            Coordinate(coordinate.row, 1)
        } else {
            coordinate.right()
        }

        else -> throw RuntimeException()
    }


fun task2(input: List<String>): Int {
    val t0 = System.currentTimeMillis()
    //up (^), down (v), left (<), or right (>)
    val dirs = setOf('^', 'v', '<', '>')
    val maxRowIdx = input.size - 1
    val maxColIdx = input.first().length - 1

    val blizzards = input.flatMapIndexed { row, line ->
        line.mapIndexedNotNull { col, c ->
            if (c in dirs) Coordinate(row, col) to listOf(c) else null
        }
    }.toMap()
    val start = Coordinate(0, input.first().indexOf('.'))
    val end = Coordinate(maxRowIdx, input.last().indexOf('.'))

    val startToEnd = bfs2(start, end, blizzards, maxRowIdx, maxColIdx)
    val t1 = System.currentTimeMillis()
    println("End reached in ${t1 - t0}. Steps: ${startToEnd.first}")
    val endToStart = bfs2(end, start, startToEnd.second, maxRowIdx, maxColIdx)
    val t2 = System.currentTimeMillis()
    println("Start reached in ${t2 - t0}. Steps: ${endToStart.first}")
    val againToEnd = bfs2(start, end, endToStart.second, maxRowIdx, maxColIdx)
    val t3 = System.currentTimeMillis()
    println("Finished in ${t3 - t0}. Steps: ${againToEnd.first}")

    // it's not yet clear to me why second and third pass should be bumped by 1, but makes some intuitive sense
    return startToEnd.first + endToStart.first + 1 + againToEnd.first + 1
}


fun main() = printSolutions(24, 2022, { input -> task1(input) }, { input -> task2(input) })