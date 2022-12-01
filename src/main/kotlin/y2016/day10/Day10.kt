package y2016.day10

import common.printSolutions
import java.lang.Integer.max
import java.lang.RuntimeException

data class InputRule(
    val value: Int,
    val botId: Int
)

data class PassingRule(
    val botId: Int,
    val low: Receiver,
    val high: Receiver
)

data class Receiver(
    val id: Int,
    val type: Type
)

enum class Type { OUTPUT, BOT }

fun String.toInputRule() =
    split(" ").let { InputRule(it[1].toInt(), it[5].toInt()) }


fun String.toPassingRule() =
    split(" ").let {
        PassingRule(
            botId = it[1].toInt(),
            low = Receiver(it[6].toInt(), it[5].toType()),
            high = Receiver(it[11].toInt(), it[10].toType())
        )
    }


fun String.toType() = when (this) {
    "bot" -> Type.BOT
    "output" -> Type.OUTPUT
    else -> throw RuntimeException("unrecognized: $this")
}


fun task1(input: List<String>): Int {
    val target = setOf(input[0].toInt(), input[1].toInt())
    val inputRules = input.filter { it.startsWith("value") }.map { it.toInputRule() }.sortedBy { it.botId }
    val passingRules = input.filter { it.startsWith("bot") }.map { it.toPassingRule() }.sortedBy { it.botId }
    val botsCount = max(inputRules.map { it.botId }.maxOrNull()!!, passingRules.map { it.botId }.maxOrNull()!!) + 1
    var grid = List<MutableSet<Int>>(botsCount) { mutableSetOf() }
    var copyGrid = List<MutableSet<Int>>(botsCount) { mutableSetOf() }

    // init
    inputRules.forEach { rule ->
        grid[rule.botId].add(rule.value)
    }

    // apply passing rules until target decision is made
    while (true) {
        println(grid.mapIndexed { index, mutableSet -> "$index - ${mutableSet.toList()}" })
        grid.forEachIndexed { index, set -> copyGrid[index].addAll(set) }
        passingRules.forEach { rule ->
            val chips = grid[rule.botId]
            if (chips.size == 2) {
                if (chips == target) return rule.botId
                copyGrid[rule.botId].clear()
                if (rule.low.type == Type.BOT) copyGrid[rule.low.id].add(chips.minOrNull()!!)
                if (rule.high.type == Type.BOT) copyGrid[rule.high.id].add(chips.maxOrNull()!!)

            }
        }
        println(copyGrid.mapIndexed { index, mutableSet -> "$index - ${mutableSet.toList()}" })
        grid = copyGrid
        copyGrid = List(botsCount) { mutableSetOf() }
    }

}

fun task2(input: List<String>): Int {
    val target = setOf(input[0].toInt(), input[1].toInt())
    val inputRules = input.filter { it.startsWith("value") }.map { it.toInputRule() }.sortedBy { it.botId }
    val passingRules = input.filter { it.startsWith("bot") }.map { it.toPassingRule() }.sortedBy { it.botId }
    val botsCount = max(inputRules.map { it.botId }.maxOrNull()!!, passingRules.map { it.botId }.maxOrNull()!!) + 1
    var grid = List<MutableSet<Int>>(botsCount) { mutableSetOf() }
    var copyGrid = List<MutableSet<Int>>(botsCount) { mutableSetOf() }
    val outputs = MutableList(3) { 0 }

    // init
    inputRules.forEach { rule ->
        grid[rule.botId].add(rule.value)
    }

    // apply passing rules until outputs are filled
    while (true) {
        println(grid.mapIndexed { index, mutableSet -> "$index - ${mutableSet.toList()}" })
        grid.forEachIndexed { index, set -> copyGrid[index].addAll(set) }
        passingRules.forEach { rule ->
            val chips = grid[rule.botId]
            if (chips.size == 2) {
                copyGrid[rule.botId].clear()
                if (rule.low.type == Type.BOT) copyGrid[rule.low.id].add(chips.minOrNull()!!)
                if (rule.low.type == Type.OUTPUT && rule.low.id <= 2) outputs[rule.low.id] = chips.minOrNull()!!
                if (rule.high.type == Type.BOT) copyGrid[rule.high.id].add(chips.maxOrNull()!!)
                if (rule.high.type == Type.OUTPUT && rule.low.id <= 2) outputs[rule.high.id] = chips.maxOrNull()!!
            }
            val outputValue = outputs[0] * outputs[1] * outputs[2]
            if (outputValue > 0) return outputValue;
        }
        grid = copyGrid
        copyGrid = List(botsCount) { mutableSetOf() }
    }
}

fun main() = printSolutions(10, 2016, { input -> task1(input) }, { input -> task2(input) })