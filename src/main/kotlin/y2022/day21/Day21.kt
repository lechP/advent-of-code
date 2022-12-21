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

fun task2(input: List<String>) {
    val monkeyMap = input.associate { line -> line.split(": ").let { it[0] to it[1] } }
    // very primitive manual binary search
    // my number decreases final result
    // first candidate: 3412650897410 - too high
    // but it turns out that couple of numbers gives the same result:
    // 0,1: ..8355 2-8 ..8025 9-15 ..7690 16- ..7385 // 330 , 335 , 305
    // so I find min which still gives equality
    val map2 = replaceHumanValue(monkeyMap, 3412650897405)
    val sides = monkeyMap["root"]!!.split(" + ")
    println(findResult(sides[0], map2))
    println(findResult(sides[1], map2))
    println(findResult(sides[0], map2)==findResult(sides[1], map2))
    println(findResult(sides[0], map2)-findResult(sides[1], map2))
}

private fun replaceHumanValue(map: Map<String, String>, humanValue: Long) =
    map.mapValues { (key, value) -> if(key=="humn") humanValue.toString() else value }


fun main() = printSolutions(21, 2022, {  }, { input -> task2(input) })