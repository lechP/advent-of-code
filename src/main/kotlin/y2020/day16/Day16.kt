package y2020.day16

import java.io.File

data class Rule(
    val name: String,
    val first: IntRange,
    val second: IntRange
)

fun task1(input: List<String>): Int {
    val ranges = input.takeWhile { row -> row.isNotEmpty() }.map { it.substringAfter(": ").split(" or ") }

    val rules = ranges.map { it[0].toRangeSet() to it[1].toRangeSet() }

    val tickets = input.drop(ranges.size + 5).map { it.split(",").map { it.toInt() } }

    return tickets.fold(0, { rate, ticket ->
        rate + ticket.filter { num -> rules.none { rule -> rule.first.contains(num) || rule.second.contains(num) } }
            .sum()
    })
}

fun String.toRangeSet(): IntRange =
    split("-").let {
        IntRange(it[0].toInt(), it[1].toInt())
    }


fun task2(input: List<String>): Any {
    val rules = input.takeWhile { row -> row.isNotEmpty() }.map {
        Rule(
            name = it.substringBefore(":"),
            first = it.substringAfter(": ").split(" or ")[0].toRangeSet(),
            second = it.substringAfter(": ").split(" or ")[1].toRangeSet()
        )
    }
    val tick = input[rules.size + 2].split(",").map { it.toInt() }
    val tickets = input.drop(rules.size + 5).map { it.split(",").map { it.toInt() } }

    val validTickets = tickets.filter { nums ->
        nums.all { n -> rules.any { it.first.contains(n) || it.second.contains(n) } }
    } + listOf(tick)

    var candidates = mutableMapOf<Int, Set<Rule>>()

    for (i in tick.indices) {
        candidates[i] = validTickets.map { t ->
            rules.filter { r -> t[i] in r.first || t[i] in r.second }.toSet()
        }.reduce { acc, set -> acc.intersect(set) }
    }

    println(candidates.filterValues { it.size == 20 }.size)

    while (candidates.values.any { it.size > 1 }) {
        val copy = mutableMapOf<Int, Set<Rule>>()
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


    println(candidates)

    return candidates.values.mapIndexed { index, set -> set.first().name.startsWith("departure") to tick[index].toLong() }
        .filter { it.first }
        .fold(1.toLong(), { acc, pair -> acc * pair.second })
}

const val day = 16
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