package y2016.day11

import common.printSolutions

fun task1and2(input: List<String>): Int {
    val start = Facility(
        elevatorPosition = 0,
        floors = input.map { line ->
            line.split(" ").let { tokens ->
                Floor(
                    generators = tokens.filter { it.contains("generator") }.map { it.split("-")[0] }.toSet(),
                    microchips = tokens.filter { it.contains("microchip") }.map { it.split("-")[0] }.toSet()
                )
            }
        }
    )
    val end = Facility(
        elevatorPosition = start.floors.size - 1,
        floors = start.floors.dropLast(1).map { Floor() } + Floor(
            generators = start.floors.flatMap { it.generators }.toSet(),
            microchips = start.floors.flatMap { it.microchips }.toSet()
        )
    )

    return bfs(start, end).size - 1
}

fun bfs(start: Facility, end: Facility): List<Facility> {
    val queue = mutableListOf(start)
    val visited = mutableSetOf(start)
    val path = mutableMapOf(start to emptyList<Facility>())

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        if (current == end) return path[current]!! + current
        current.possibleMoves().forEach { next ->
            // TODO for task 2 there are too many paths when checking exact equality of visited nodes
            //   when checking visited we should check EQUIVALENCE instead of EQUALITY
            if (next !in visited) {
                visited.add(next)
                queue.add(next)
                path[next] = path[current]!! + current
            }
        }
    }

    throw RuntimeException("end unreachable")
}

data class Facility(
    val floors: List<Floor>,
    val elevatorPosition: Int
) {
    fun possibleMoves(): List<Facility> {
        val floor = floors[elevatorPosition]
        val microchips = floor.microchips
        val generators = floor.generators
        val singles: Set<Transport> =
            microchips.map { Transport(microchips = setOf(it)) }.toSet() +
                    generators.map { Transport(generators = setOf(it)) }.toSet()
        val pairs: Set<Transport> = cartesianProduct(generators, microchips) +
                microchips.twoElementSubsets().map { Transport(microchips = it) } +
                generators.twoElementSubsets().map { Transport(generators = it) }
        val possibleTransports = singles + pairs
        val movesUp = if (elevatorPosition < 3) {
            possibleTransports.map { transport ->
                copy(
                    elevatorPosition = elevatorPosition + 1,
                    floors = floors.mapIndexed { index, floor ->
                        when (index) {
                            elevatorPosition -> floor.copy(
                                generators = floor.generators - transport.generators,
                                microchips = floor.microchips - transport.microchips
                            )

                            elevatorPosition + 1 -> floor.copy(
                                generators = floor.generators + transport.generators,
                                microchips = floor.microchips + transport.microchips
                            )

                            else -> floor
                        }
                    }
                )
            }.filter { it.isLegal() }
        } else emptyList()

        val movesDown = if (elevatorPosition > 0) {
            possibleTransports.map { transport ->
                copy(
                    elevatorPosition = elevatorPosition - 1,
                    floors = floors.mapIndexed { index, floor ->
                        when (index) {
                            elevatorPosition -> floor.copy(
                                generators = floor.generators - transport.generators,
                                microchips = floor.microchips - transport.microchips
                            )

                            elevatorPosition - 1 -> floor.copy(
                                generators = floor.generators + transport.generators,
                                microchips = floor.microchips + transport.microchips
                            )

                            else -> floor
                        }
                    }
                )
            }.filter { it.isLegal() }
        } else emptyList()
        return movesUp + movesDown
    }

    private fun isLegal(): Boolean = floors.all { floor ->
        floor.generators.isEmpty() || floor.microchips.all { chip -> chip in floor.generators }
    }

    private fun Set<String>.twoElementSubsets(): Set<Set<String>> = if (size < 2) emptySet() else {
        val result = mutableSetOf<Set<String>>()
        for (item1 in this)
            for (item2 in this)
                if (item1 != item2) result.add(setOf(item1, item2))
        result
    }

    private fun cartesianProduct(microchips: Set<String>, generators: Set<String>): Set<Transport> =
        microchips.flatMap { m ->
            generators.map { g ->
                Transport(setOf(g), setOf(m))
            }
        }.toSet()

}

data class Floor(
    val generators: Set<String> = emptySet(),
    val microchips: Set<String> = emptySet()
)

typealias Transport = Floor


fun main() = printSolutions(11, 2016, { input -> task1and2(input) }, { })