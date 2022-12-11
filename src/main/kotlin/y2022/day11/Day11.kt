package y2022.day11

import common.printSolutions


fun task1(input: List<String>): Int {
    val monkeys = input.chunked(7).map { it.toMonkey() }
    val monkeyDecisions = monkeys.map { 0 }.toMutableList()
    val rounds = 20
    for (i in 1..rounds) {
        monkeys.forEachIndexed { index, monkey ->
            monkey.items.forEach { item ->
                val worryLevel = monkey.operation(item) / 3
                val nextMonkey = if (worryLevel % monkey.test == 0L) {
                    monkey.trueMonkey
                } else {
                    monkey.falseMonkey
                }
                monkeys[nextMonkey].items.add(worryLevel)
            }
            monkeyDecisions[index] = monkeyDecisions[index] + monkey.items.size
            monkey.items.removeAll(monkey.items)
        }
    }

    val mostActiveMonkeys = monkeyDecisions.sortedDescending().take(2)

    return mostActiveMonkeys[0] * mostActiveMonkeys[1]
}

fun List<String>.toMonkey() =
    Monkey(
        items = this[1].split(": ")[1].split(", ").map { it.toLong() }.toMutableList(),
        operation = this[2].split(" ").takeLast(2).toOperation(),
        test = this[3].split(" ").last().toInt(),
        trueMonkey = this[4].split(" ").last().toInt(),
        falseMonkey = this[5].split(" ").last().toInt(),
    )

fun List<String>.toOperation(): (Long) -> (Long) =
    when (this[0]) {
        "*" -> if (this[1] == "old") { a -> a * a } else { a -> a * this[1].toLong() }
        "+" -> { a -> a + this[1].toLong() }
        else -> throw RuntimeException("unexpected token: ${this[0]}")
    }


fun task2(input: List<String>): Long {
    val monkeys = input.chunked(7).map { it.toMonkey() }
    val monkeyDecisions = monkeys.map { 0L }.toMutableList()
    val multTest = monkeys.map { it.test }.reduce { a, b -> a * b }
    val rounds = 10000
    for (i in 1..rounds) {
        monkeys.forEachIndexed { index, monkey ->
            monkey.items.forEach { item ->
                val worryLevel = monkey.operation(item) % multTest // chinese rest theorem
                val nextMonkey = if (worryLevel % monkey.test == 0L) {
                    monkey.trueMonkey
                } else {
                    monkey.falseMonkey
                }
                monkeys[nextMonkey].items.add(worryLevel)
            }
            monkeyDecisions[index] = monkeyDecisions[index] + monkey.items.size
            monkey.items.removeAll(monkey.items)
        }
        if (i == 1) println(monkeyDecisions)
        if (i == 20) println(monkeyDecisions)
    }

    val mostActiveMonkeys = monkeyDecisions.sortedDescending().take(2)

    return mostActiveMonkeys[0] * mostActiveMonkeys[1]
}

fun main() = printSolutions(11, 2022, { input -> task1(input) }, { input -> task2(input) })

data class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> (Long),
    val test: Int,
    val trueMonkey: Int,
    val falseMonkey: Int,
)