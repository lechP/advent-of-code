package y2020.day9

import java.io.File

fun task1(input: List<String>): Long {
    val nums = input.map { it.toLong() }
    val plen = 25
    var matches = false
    for (i in plen..nums.lastIndex) {
        val preamble = nums.subList(i - plen, i)
        for (j in 0 until plen) {
            if (preamble.minus(preamble[j]).contains(nums[i] - preamble[j])) matches = true
        }
        if (!matches) return nums[i]
        matches = false
    }
    return -1
}

fun task2(input: List<String>): Long {
    val x: Long = 542529149
    val nums = input.map { it.toLong() }
    for (i in 2..20) {
        for (j in 0..nums.size - i) {
            nums.subList(j, j + i).let {
                if (it.sum() == x) return it.min()!! + it.max()!!
            }
        }
    }
    return -1
}

fun main() {
    val testInput = File("src/main/resources/y2020/day9/testInput").useLines { it.toList() }
    val input = File("src/main/resources/y2020/day9/input").useLines { it.toList() }


    println(task1(testInput))
    println(task1(input))
    println(task2(testInput))
    println(task2(input))
}