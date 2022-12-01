package y2020.day7

import java.io.File

data class Content(
    val quantity: Int,
    val color: String
)

data class Rule(
    val container: String,
    val contents: Set<Content>
)

fun String.parseBagRule(): Rule {
    val a = split(" contain ")
    val container = a[0].removeSuffix(" bags")
    if (a[1] != "no other bags.") {
        val b = a[1].removeSuffix(".").split(", ")
        val contents = b.map {
            Content(
                quantity = it.substringBefore(" ").toInt(),
                color = it.substringAfter(" ").removeSuffix(" bags").removeSuffix(" bag")
            )
        }.toSet()
        return Rule(container, contents)
    } else {
        return Rule(container, emptySet())
    }
}


fun task1(input: List<String>): Int {
    val rules = input.map { it.parseBagRule() }.toSet()
    return countContainers(rules, "shiny gold").size
}

fun countContainers(rules: Set<Rule>, color: String): Set<String> {
    val complyingRules = rules.filter { it.contents.any { it.color == color } }
    return complyingRules.map { it.container }
        .union(
            complyingRules.map { countContainers(rules, it.container) }.flatten().toSet()
        )
}

fun task2(input: List<String>): Int {
    val rules = input.map { it.parseBagRule() }.toSet()
    return countContains(rules, "shiny gold")
}

fun countContains(rules: Set<Rule>, color: String): Int {
    val complyingRule = rules.first { it.container == color }
    return complyingRule.contents.map { it.quantity }.sum() +
            complyingRule.contents.map { it.quantity * countContains(rules, it.color) }.sum()
}


fun main() {
    val testInput = File("src/main/resources/y2020/day7/testInput").useLines { it.toList() }
    val testInput2 = File("src/main/resources/y2020/day7/testInput2").useLines { it.toList() }
    val input = File("src/main/resources/y2020/day7/input").useLines { it.toList() }


    println(task1(testInput))
    println(task1(input))
    println(task2(testInput))
    println(task2(testInput2))
    println(task2(input))
}