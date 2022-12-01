package y2020.day21

import y2020.day16.Rule
import java.io.File

data class Food(
    val ing: List<String>,
    val alg: List<String>
)

fun String.toFood() =
    Food(
        ing = substringBefore(" (contains ").split(" "),
        alg = if (contains(" (contains "))
            substringAfter(" (contains ").substringBefore(")").split(" ").map {
                it.replace(",", "")
            }
        else
            emptyList()
    )

fun task1(input: List<String>): Int {
    val foods = input.map { it.toFood() }
    val algs = foods.flatMap { it.alg }.toSet()
    val ings = foods.flatMap { it.ing }.toSet()
    var candidates = algs.map {
        it to foods
            .filter { food -> food.alg.contains(it) }
            .map { food -> food.ing.toSet() }
            .fold(ings) { a, b -> a.intersect(b) }
    }
        .toMap()

    while (candidates.values.any { it.size > 1 }) {
        val copy = mutableMapOf<String, Set<String>>()
        val singles = candidates.filter { it.value.size == 1 }.values.flatten()
        candidates.forEach { (key, value) ->
            if (value.size == 1) {
                copy[key] = value
            } else {
                copy[key] = value.filter { !singles.contains(it) }.toSet()
            }
        }
        candidates = copy
    }
    val noalgFood = ings.filter { !candidates.values.flatten().contains(it) }
    val dangerousFood = candidates.entries.sortedBy { it.key }.map { it.value }.flatten().joinToString(",")
    println(dangerousFood)
    return foods.flatMap { it.ing }.count { noalgFood.contains(it) }
}

fun task2(input: List<String>): Int {

    return -1
}

const val day = 21
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