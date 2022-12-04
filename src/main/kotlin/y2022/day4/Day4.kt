package y2022.day4

import common.printSolutions


fun String.toRange() = split("-").let { it[0].toInt() to it[1].toInt() }

fun rangesOverlap(range1: Pair<Int, Int>, range2: Pair<Int, Int>) =
    (range1.first <= range2.first && range1.second >= range2.second) ||
            (range1.first >= range2.first && range1.second <= range2.second)

fun String.lineOverlaps() = split(",").let {
    rangesOverlap(it[0].toRange(), it[1].toRange())
}

fun task1(input: List<String>) = input.count { it.lineOverlaps() }

fun rangesOverlapAtAll(range1: Pair<Int, Int>, range2: Pair<Int, Int>) =
    range1.first in range2.first..range2.second ||
            range1.second in range2.first..range2.second ||
            range2.first in range1.first..range1.second ||
            range2.second in range1.first..range1.second

fun String.lineOverlapsAtAll() = split(",").let {
    rangesOverlapAtAll(it[0].toRange(), it[1].toRange())
}

fun task2(input: List<String>) = input.count { it.lineOverlapsAtAll() }

fun main() = printSolutions(4, 2022, { input -> task1(input) }, { input -> task2(input) })