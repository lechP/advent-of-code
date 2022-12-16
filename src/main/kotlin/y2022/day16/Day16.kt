package y2022.day16

import common.printSolutions


fun task1(input: List<String>): Int {
    val valves = input.map { it.toValve() }
    var current = valves.first()
    var timeElapsed = 0
    var pressureReleased = 0
    val opened = mutableListOf<String>()
    val ids = valves.map { it.id }
    val map = valves.associateBy { it.id }
    val paths = mutableMapOf<Pair<String, String>, Int>()

    // wybiore sobie co mi sie najbardziej opłaca w kolejnym ruchu
    while (timeElapsed < 30) {
        val timeRemaining = 30 - timeElapsed
        val otherIds = ids - current.id
        for (otherId in otherIds) {
            if (paths[current.id to otherId] == null) {
                paths[current.id to otherId] = bfs(current, map[otherId]!!, map)
            }
        }
        val distances = otherIds.associateWith { otherId ->
            if (paths[current.id to otherId] == null) {
                paths[current.id to otherId] = bfs(current, map[otherId]!!, map)
            }
            paths[current.id to otherId]!!
        }
        val possibleGains = distances
            .filterKeys { it !in opened }
            .filterValues { it < timeRemaining }
            .map { (id, distance) ->
                id to (timeRemaining - 1 - distance) * map[id]!!.pressure
            }
        // choosing local maximum isn't optimal for the whole path
        val maxGain = possibleGains.maxByOrNull { it.second }
        println("max gain: $maxGain")
        if (maxGain != null) {
            opened.add(maxGain.first)
            timeElapsed += distances[maxGain.first]!! + 1
            println("time elapsed: $timeElapsed")
            pressureReleased += maxGain.second
            current = map[maxGain.first]!!
        } else {
            timeElapsed = 30
        }

    }
    return pressureReleased
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
    return -2
}


fun main() = printSolutions(16, 2022, { input -> task1(input) }, { input -> task2(input) })