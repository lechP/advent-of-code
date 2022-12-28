package y2022.day21

import common.printSolutions


fun task1(input: List<String>): Long {
    val monkeyMap = input.associate { line -> line.split(": ").let { it[0] to it[1] } }
    return findResult("root", monkeyMap)
}

private fun findResult(monkey: String, map: Map<String, String>): Long {
    val operation = map[monkey]!!
    return if (operation.isLong()) {
        operation.toLong()
    } else {
        val tokens = operation.split(' ')
        when (val operator = tokens[1]) {
            "+" -> findResult(tokens[0], map) + findResult(tokens[2], map)
            "-" -> findResult(tokens[0], map) - findResult(tokens[2], map)
            "*" -> findResult(tokens[0], map) * findResult(tokens[2], map)
            "/" -> findResult(tokens[0], map) / findResult(tokens[2], map)
            else -> throw RuntimeException("Unsupported operator: $operator")
        }
    }
}

private fun String.isLong() = toLongOrNull() != null

fun task2(input: List<String>): Long {
    val monkeyMap = input.associate { line -> line.split(": ").let { it[0] to it[1] } }

    var lo = 0L
    var hi = Int.MAX_VALUE.toLong()
    // increase hi until it gets on the other side of zero
    while (sidesDiff(monkeyMap, lo).sign() == sidesDiff(monkeyMap, hi).sign()) hi *= 2
    // find candidate by binary search
    var mid = (hi + lo) / 2
    while (sidesDiff(monkeyMap, mid) != 0L) {
        if (sidesDiff(monkeyMap, lo).sign() == sidesDiff(monkeyMap, mid).sign()) {
            lo = mid
        } else {
            hi = mid
        }
        mid = (hi + lo) / 2
    }

    // it turns out that couple of numbers gives the same result:
    // 0,1: ..8355 2-8 ..8025 9-15 ..7690 16- ..7385 // 330 , 335 , 305
    // so I find minimal value which still gives equality
    while (sidesDiff(monkeyMap, mid - 1) == 0L) mid -= 1

    return mid
}

fun sidesDiff(map: Map<String, String>, candidate: Long): Long {
    val replacedMap = replaceHumanValue(map, candidate)
    val sides = replacedMap["root"]!!.split(" + ")
    return findResult(sides[0], replacedMap) - findResult(sides[1], replacedMap)
}

fun Long.sign() = when {
    this < 0 -> -1
    this > 0 -> 1
    else -> 0
}

private fun replaceHumanValue(map: Map<String, String>, humanValue: Long) =
    map.mapValues { (key, value) -> if (key == "humn") humanValue.toString() else value }


fun main() = printSolutions(21, 2022, { input -> task1(input) }, { input -> task2(input) })