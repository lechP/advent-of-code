package y2022.day1

import java.io.File


private fun getCaloriesMap(input: List<String>): Map<Int, Int> {
    var elfId = 0
    val caloriesMap = mutableMapOf<Int, Int>()
    input.forEach { line ->
        if(line.isBlank()) {
            elfId += 1
        } else {
            caloriesMap[elfId] = line.toInt() + caloriesMap.getOrDefault(elfId, 0)
        }
    }
    return caloriesMap
}

fun findMaxCalories(input: List<String>) = getCaloriesMap(input).values.max()

/**
 * sorting has O(n*log(n)) time complexity
 * it could be done faster by iterating three times on map, each next time with maximum element removed
 */
fun findTopThreeMaxCalories(input: List<String>): Int = getCaloriesMap(input).values.sortedDescending().take(3).sum()



fun main() {
//    val input = File("src/main/resources/y2022/day1/testInput").useLines { it.toList() }
    val input = File("src/main/resources/y2022/day1/input").useLines { it.toList() }
    println(findMaxCalories(input))
    println(findTopThreeMaxCalories(input))
}