package y2020.day13

import java.io.File

fun task1(input: List<String>): Any {
    val arrival = input.first().toInt()
    val busIds = input[1].split(",").filter { it != "x" }.map { it.toInt() }
    val xd = mutableMapOf<Int, Int>()
    busIds.forEach {
        var departure = 0
        while (departure < arrival) {
            departure += it
        }
        xd[it] = departure
    }
    return xd.minByOrNull { it.value }.let { (it!!.value - arrival) * it.key }

}

fun task2(input: List<String>): Any {
    val expectedSchedule = input[1].split(",").mapIndexed { index, value ->
        value to index
    }.filter { it.first != "x" }.map { it.first.toInt() to it.second }

    var t: Long = expectedSchedule.first().first.toLong()
    var offset = t
    var numsToTake = 2

    while (true) {
        if (matchesExpectedSchedule(t, expectedSchedule.take(numsToTake))) {
            if (numsToTake == expectedSchedule.size) {
                return t
            } else {
                offset *= expectedSchedule[numsToTake - 1].first
                numsToTake++
            }
        }
        t += offset
    }
}

fun matchesExpectedSchedule(t: Long, expectedSchedule: List<Pair<Int, Int>>): Boolean {
    for (i in expectedSchedule.indices) {
        if ((t + expectedSchedule[i].second) % expectedSchedule[i].first != 0.toLong()) return false
    }
    return true
}

const val day = 13
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

/*
67,7,59,61 first occurs at timestamp 754018.
    67*x = t
    7*y = t+1
    59*z = t+2
    61*w = t+3

    431-23
    409-54

    431*n = t + 23
    409*m = t + 54
    431*n - 23 = 409*m - 54
    431*n = 409*m - 31
    409*m = 431*n + 31

    m = (431*n + 31)/409
 */