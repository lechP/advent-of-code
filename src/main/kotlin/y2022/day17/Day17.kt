package y2022.day17

import common.Coordinate
import common.printSolutions
import kotlin.math.max


fun task1(input: List<String>): Int {
    val gasPattern = input.first()

    return simulateRocks(2022, gasPattern).first
}

fun simulateRocks(rocksCount: Int, gasPattern: String): Pair<Int, List<Int>> {
    val rocks = listOf("horizLine", "cross", "v", "vertLine", "square")

    var gasIndex = 0
    var rockIndex = 0
    var rocksFallen = 0
    var peak = 0
    val peakGrowths = mutableListOf<Int>()

    val rocksFormation = (1..7).associate {
        Coordinate(0, it) to '-'
    }.toMutableMap()

    // floor r=0 c=1..7

    do {
        rocksFallen += 1
        var currentRock = generateRock(rocks[rockIndex], peak)
        rockIndex = (rockIndex + 1) % rocks.size
        while (true) {
            when (gasPattern[gasIndex]) {
                '>' -> currentRock = currentRock.moveRight(rocksFormation)
                '<' -> currentRock = currentRock.moveLeft(rocksFormation)
            }
            gasIndex = (gasIndex + 1) % gasPattern.length
            if (currentRock.canMoveDown(rocksFormation)) {
                currentRock = currentRock.moveDown()
            } else {
                break
            }
        }
        rocksFormation.putAll(
            currentRock.associateWith { '#' }
        )
        val prevPeak = peak
        peak = max(peak, currentRock.maxOf { it.row })
        peakGrowths.add(peak - prevPeak)

    } while (rocksFallen < rocksCount)

    return peak to peakGrowths
}


fun printState(map: Map<Coordinate, Char>, rows: IntRange, cols: IntRange) {
    println()
    for (r in rows.reversed()) {
        for (c in cols) {
            print(map.getOrDefault(Coordinate(r, c), '.'))
        }
        println()
    }
}


fun generateRock(shape: String, currentPeak: Int): List<Coordinate> =
    when (shape) {
        "horizLine" -> (3..6).map {
            Coordinate(currentPeak + 4, it)
        }

        "cross" -> (3..5).map {
            Coordinate(currentPeak + 5, it)
        } + Coordinate(currentPeak + 4, 4) +
                Coordinate(currentPeak + 6, 4)

        "v" -> (3..5).map {
            Coordinate(currentPeak + 4, it)
        } + Coordinate(currentPeak + 5, 5) +
                Coordinate(currentPeak + 6, 5)

        "vertLine" -> ((currentPeak + 4)..(currentPeak + 7)).map {
            Coordinate(it, 3)
        }

        "square" -> listOf(
            Coordinate(currentPeak + 4, 3),
            Coordinate(currentPeak + 4, 4),
            Coordinate(currentPeak + 5, 3),
            Coordinate(currentPeak + 5, 4),
        )

        else -> throw RuntimeException("incorrect shape: $shape")
    }

fun List<Coordinate>.moveDown() = map {
    Coordinate(row = it.row - 1, col = it.col)
}

fun List<Coordinate>.moveLeft(rocksFormation: Map<Coordinate, Char>) =
    if (any { it.col == 1 } || any { rocksFormation.containsKey(Coordinate(it.row, it.col - 1)) })
        this
    else map { Coordinate(row = it.row, col = it.col - 1) }

fun List<Coordinate>.moveRight(rocksFormation: Map<Coordinate, Char>) =
    if (any { it.col == 7 } || any { rocksFormation.containsKey(Coordinate(it.row, it.col + 1)) })
        this
    else map { Coordinate(row = it.row, col = it.col + 1) }

fun List<Coordinate>.canMoveDown(rocksFormation: Map<Coordinate, Char>): Boolean = none {
    rocksFormation.containsKey(Coordinate(it.row - 1, it.col))
}

fun main() = printSolutions(17, 2022, { input -> task1(input) }, { })