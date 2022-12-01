package y2016.day6

import common.printSolutions

fun task1(input: List<String>): Int {
    var password = mutableListOf<Char>()
    for (i in 0..input.first().lastIndex) {
        password.add(
            input.map { it[i] }.groupBy {it}.map { it.key to it.value.size }.sortedWith(compareBy { -it.second }).first().first)

    }
    print(password)
    return -1
}

fun task2(input: List<String>): Int {
    var password = mutableListOf<Char>()
    for (i in 0..input.first().lastIndex) {
        password.add(
            input.map { it[i] }.groupBy {it}.map { it.key to it.value.size }.sortedWith(compareBy { it.second }).first().first)

    }
    print(password)
    return -1
}

fun main() = printSolutions(6, 2016, { input -> task1(input) }, { input -> task2(input) })