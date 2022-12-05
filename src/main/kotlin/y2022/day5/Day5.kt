package y2022.day5

import common.printSolutions
import java.util.*


fun task1(input: List<String>): String {
    val stacks = input.subList(0, input.indexOf("")).reversed().toStacks()
    val instructions = input.subList(input.indexOf("") + 1, input.size).map { it.toInstruction() }
    val stacksAfter = applyInstructions(stacks, instructions)
    return stacksAfter.values.map { it.peek() }.joinToString("")
}

fun List<String>.toStacks(): Map<Int, Stack<Char>> {
    val stacksAmount = this[0].split("   ").last().trim().toInt()
    val mapOfStacks = mutableMapOf<Int, Stack<Char>>()
    for (i in 0 until stacksAmount) {
        mapOfStacks[i] = Stack<Char>()
    }
    drop(1).forEach { line ->
        for (i in 0 until stacksAmount) {
            val index = 1 + 4 * i
            if (line.length > index && line[index] != ' ') {
                mapOfStacks[i]!!.push(line[index])
            }
        }
    }
    return mapOfStacks
}

fun String.toInstruction() = split(" ").let {
    Instruction(it[1].toInt(), it[3].toInt(), it[5].toInt())
}

fun applyInstructions(stacks: Map<Int, Stack<Char>>, instructions: List<Instruction>): Map<Int, Stack<Char>> {
    instructions.forEach {
        for (i in 1..it.amount) {
            val el = stacks[it.from - 1]!!.pop()
            stacks[it.to - 1]!!.push(el)
        }
    }
    return stacks
}

fun task2(input: List<String>): String {
    val stacks = input.subList(0, input.indexOf("")).reversed().toStacks()
    val instructions = input.subList(input.indexOf("") + 1, input.size).map { it.toInstruction() }
    val stacksAfter = applyInstructions2(stacks, instructions)
    return stacksAfter.values.map { it.peek() }.joinToString("")
}

fun applyInstructions2(stacks: Map<Int, Stack<Char>>, instructions: List<Instruction>): Map<Int, Stack<Char>> {
    instructions.forEach {
        val tempStack = Stack<Char>()
        for (i in 1..it.amount) {
            tempStack.push(stacks[it.from - 1]!!.pop())
        }
        for (i in 1..it.amount) {
            stacks[it.to - 1]!!.push(tempStack.pop())
        }
    }
    return stacks
}


fun main() = printSolutions(5, 2022, { input -> task1(input) }, { input -> task2(input) })

data class Instruction(
    val amount: Int,
    val from: Int,
    val to: Int,
)