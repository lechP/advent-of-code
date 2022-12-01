package y2020.day19

import java.io.File

fun task1(input: List<String>): Int {
    val rules = input.takeWhile { it != "" }
    val messages = input.drop(rules.size + 1)

    val parsedRules: Map<Int, List<List<String>>> = rules.map {
        it.split(": ").let {
            it[0].toInt() to it[1].parseReminder()
        }
    }.toMap()

    var rule0 = parsedRules[0]!!
    val expected = listOf("a", "b")
    println(rule0)
    while (rule0.flatten().any { !expected.contains(it) }) {
        rule0 = rule0.map { it.getRules(parsedRules) }.fold(emptyList()) { first, second -> first + second }
        rule0 = rule0.distinct()
        println(rule0)
        //println(rule0.flatten().count { !expected.contains(it) })
    }
    val rule = rule0.map { it.joinToString("") }

    return messages.count { rule.contains(it) }
}

fun List<String>.getRules(rules: Map<Int, List<List<String>>>): List<List<String>> =
    this.map {
        if (listOf("a", "b").contains(it)) {
            listOf(listOf(it))
        } else {
            rules[it.toInt()]!!
        }
    }.fold(emptyList()) { first, second -> cartesianJoin(first, second) }

fun String.parseReminder(): List<List<String>> =
    replace("\"", "").split(" | ").map { it.split(" ") }

//[(1,2), (3,4)], [(11,12),(13,14)] -> [(1,2,11,12), (1,2,13,14), (3,4,11,12), (3,4,13,14)]
fun cartesianJoin(first: List<List<String>>, second: List<List<String>>): List<List<String>> =
    if (first.isEmpty()) second else first.flatMap { f -> second.map { s -> f + s } }


fun task2(input: List<String>): Int {

    return -1
}

const val day = 19
const val srcdir = "src/main/resources/y2020/day"
const val inputFile = "$srcdir$day/input"
const val testInputFile = "$srcdir$day/testInput"

fun main() {
    val testInput = File(testInputFile).useLines { it.toList() }
    val input = File(inputFile).useLines { it.toList() }

//    println(
//        cartesianJoin(
//            first = listOf(
//                listOf("1", "2"),
////                listOf("3", "4"),
////                listOf("5", "6"),
//            ),
//            second = listOf(
//                listOf("11", "12"),
//                listOf("13", "14"),
//            ),
//        )
//    )

    println(task1(testInput))
//    println(task1(input))
    println(task2(testInput))
    println(task2(input))
}