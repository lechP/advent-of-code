package y2022.day16

import common.printSolutions

fun task1(input: List<String>): Int {
    val valves = input.map { it.toValve() }
    val map = valves.associateBy { it.id }
    val paths = precomputeShortestPaths(map)
    return findMaxPressure("AA", 0, map, paths, emptySet())
}

fun precomputeShortestPaths(valvesMap: Map<String, Valve>): Map<Pair<String, String>, Int> {
    val ids = valvesMap.keys
    val paths = mutableMapOf<Pair<String, String>, Int>()
    for (start in ids) {
        for (end in ids) {
            if (start != end) {
                val length = when {
                    paths[start to end] != null -> paths[start to end]!!
                    paths[end to start] != null -> paths[end to start]!!
                    else -> bfs(valvesMap[start]!!, valvesMap[end]!!, valvesMap)
                }
                if (length > -1) {
                    paths[start to end] = length
                    paths[end to start] = length
                }
            }
        }
    }
    return paths.toMap()
}

fun findMaxPressure(
    current: String,
    usedTime: Int,
    valvesMap: Map<String, Valve>,
    paths: Map<Pair<String, String>, Int>,
    openedValves: Set<String>,
): Int {
    val totalTime = 30
    val timeToOpenValve = 1

    val possibleValves = valvesMap
        .filter { paths.containsKey(current to it.key) }
        .filter { it.value.pressure > 0 }
        .filter { it.key !in openedValves }

    return possibleValves.maxOfOrNull { (id, valve) ->
        val updatedUsedTime = usedTime + paths[current to id]!! + timeToOpenValve
        if (updatedUsedTime > totalTime) {
            0
        } else {
            val gainedPressure = (totalTime - updatedUsedTime) * valve.pressure
            gainedPressure + findMaxPressure(
                current = id,
                usedTime = updatedUsedTime,
                valvesMap = valvesMap,
                paths = paths,
                openedValves = openedValves + id
            )
        }
    } ?: 0
}

fun findMaxPressureV2(
    currentHuman: String,
    currentElephant: String,
    usedTimeHuman: Int,
    usedTimeElephant: Int,
    valvesMap: Map<String, Valve>,
    paths: Map<Pair<String, String>, Int>,
    openedValves: Set<String>,
): Int {
    val totalTime = 26
    val timeToOpenValve = 1

    val possibleHumanValves = valvesMap
        .filter { paths.containsKey(currentHuman to it.key) }
        .filter { it.value.pressure > 0 }
        .filter { it.key !in openedValves }

    val possibleElephantValves = valvesMap
        .filter { paths.containsKey(currentElephant to it.key) }
        .filter { it.value.pressure > 0 }
        .filter { it.key !in openedValves }

    return possibleHumanValves.maxOfOrNull { (idH, valveH) ->
        val updatedUsedTimeHuman = usedTimeHuman + paths[currentHuman to idH]!! + timeToOpenValve
        if (updatedUsedTimeHuman > totalTime) {
            0
        } else {
            val gainedPressure = (totalTime - updatedUsedTimeHuman) * valveH.pressure
            gainedPressure +
                    ((possibleElephantValves - idH).maxOfOrNull { (idE, valveE) ->
                        val updatedUsedElephantTime =
                            usedTimeElephant + paths[currentElephant to idE]!! + timeToOpenValve
                        if (updatedUsedElephantTime > totalTime) {
                            0
                        } else {
                            val gainedPressureE = (totalTime - updatedUsedElephantTime) * valveE.pressure
                            gainedPressureE + findMaxPressureV2(
                                currentHuman = idH,
                                currentElephant = idE,
                                usedTimeHuman = updatedUsedTimeHuman,
                                usedTimeElephant = updatedUsedElephantTime,
                                valvesMap = valvesMap,
                                paths = paths,
                                openedValves = openedValves + idH + idE
                            )
                        }
                    } ?: 0)
        }
    } ?: 0
}

//Valve MF has flow rate=0; tunnels lead to valves QO, GQ
fun String.toValve() = Valve(
    id = substring(6, 8),
    pressure = substring(indexOf('=') + 1, indexOf(';')).toInt(),
    neighbors = split(", ").map { it.takeLast(2) }
)

fun bfs(start: Valve, end: Valve, all: Map<String, Valve>): Int {

    val queue = mutableListOf(start)
    val visited = mutableSetOf(start)
    val path = mutableMapOf(start to emptyList<Valve>())

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        if (current == end) return (path[current]!! + current).size - 1
        current.neighbors.forEach { id ->
            val next = all[id]!!
            if (next !in visited) {
                visited.add(next)
                queue.add(next)
                path[next] = path[current]!! + current
            }
        }
    }
    return -1
}

data class Valve(
    val id: String,
    val pressure: Int,
    val neighbors: List<String>,
)


fun task2(input: List<String>): Int {
    val valves = input.map { it.toValve() }
    val map = valves.associateBy { it.id }
    val paths = precomputeShortestPaths(map)
    return findMaxPressureV2("AA", "AA", 0, 0, map, paths, emptySet())
}


fun main() = printSolutions(16, 2022, { input -> task1(input) }, { input -> task2(input) })
//2584 2588 2592 2600