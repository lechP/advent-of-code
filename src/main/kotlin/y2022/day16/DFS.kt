package y2022.day16

// depth first search basic implementation
val graph = mapOf(
    0 to setOf(1, 2, 3),
    1 to setOf(0, 2),
    2 to setOf(0, 1, 4),
    3 to setOf(0),
    4 to setOf(2),
)

val graph2 = mapOf(
    'A' to setOf('B', 'C', 'E'),
    'B' to setOf('A', 'D', 'F'),
    'C' to setOf('A', 'G'),
    'D' to setOf('B'),
    'E' to setOf('A', 'F'),
    'F' to setOf('B', 'E'),
    'G' to setOf('C'),
)

fun <T> dfs(graph: Map<T, Set<T>>, start: T, visited: Set<T>): Set<T> {
    var updatedVisited = visited + start
    val neighbors = graph[start]!!
    for (vertex in neighbors) {
        if (vertex !in updatedVisited) {
            updatedVisited = dfs(graph, vertex, updatedVisited)
        }
    }
    return updatedVisited
}


fun main() {
    println(dfs(graph, 0, emptySet()))
    println(dfs(graph2, 'A', emptySet()))
}