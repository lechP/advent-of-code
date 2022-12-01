package y2020.day18

import java.io.File
import java.lang.RuntimeException
import java.util.*

fun task1(input: List<String>): Any {
    return input.map { it.evaluate1a() }.sum()
}

enum class Op {
    ADD, MULTIPLY
}

fun String.evaluate(): Long {
    var index = 0
    var result: Long
    var operation: Op = Op.ADD

    if (this[index].isDigit()) {
        result = this[index].toString().toLong()
        index++
    } else { // (
        result = subExpression(1).evaluate()
        index += subExpression(1).length + 2
    }

    while (index < length) {
        when {
            this[index].isDigit() -> {
                when (operation) {
                    Op.ADD -> result += this[index].toString().toLong()
                    Op.MULTIPLY -> result *= this[index].toString().toLong()
                }
                index++
            }
            this[index] == '+' -> {
                operation = Op.ADD
                index++
            }
            this[index] == '*' -> {
                operation = Op.MULTIPLY
                index++
            }
            this[index] == ' ' -> index++
            this[index] == '(' -> {
                when (operation) {
                    Op.ADD -> result += subExpression(index + 1).evaluate()
                    Op.MULTIPLY -> result *= subExpression(index + 1).evaluate()
                }
                index += subExpression(index + 1).length + 2
            }
        }
    }

    return result
}

fun String.subExpression(startIndex: Int): String { // start index right after (
    var left = 1
    var right = 0
    var index = startIndex
    while (right < left) {
        if (this[index] == '(') left++
        if (this[index] == ')') right++
        index++
    }
    return substring(startIndex, index - 1)
}

fun String.evaluate1a(): Long {
    var result = this
    while (result.contains("(")) {
        result = result.replace(Regex("\\([^()]+\\)")) {
            calculateSimple1a(it.value.substring(1, it.value.lastIndex)).toString()
        }
    }
    return calculateSimple1a(result)
}

fun calculateSimple1a(operation: String): Long {  // a * b + c (no parentheses)
    val input = operation.split(" ")
    val initial = input.first().toLong()
    return input.drop(1).windowed(2, 2).fold(initial) { acc, pair ->
        val(op: String, num) = pair
        when(op) {
            "*" -> acc * num.toLong()
            else -> acc + num.toLong()
        }
    }
}

fun String.evaluate2(): Long {
    val values: Stack<Long> = Stack()
    val ops: Stack<Char> = Stack()
    toCharArray().forEach { c ->
        when {
            c.isDigit() -> values.push(c.toString().toLong())
            c == '(' -> ops.push(c)
            c == ')' -> {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()))
                ops.pop()
            }
            c == '+' || c == '*' -> {
                while (!ops.empty() && hasPrecendence(c, ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()))
                ops.push(c)
            }
        }
    }
    while (!ops.empty())
        values.push(applyOp(ops.pop(), values.pop(), values.pop()))

    return values.pop()
}

fun applyOp(op: Char, a: Long, b: Long): Long =
    when (op) {
        '+' -> a + b
        '*' -> a * b
        else -> throw RuntimeException("Unrecognized op: $op")
    }

fun hasPrecendence(op1: Char, op2: Char): Boolean {
    if (op2 == '(' || op2 == ')') return false
    return op1 == '*' && op2 == '+'
}

fun task2(input: List<String>): Any {
    return input.map { it.evaluate3() }.sum()
}

fun String.evaluate3(): Long {
    var result = this
    while (result.contains("(")) {
        result = result.replace(Regex("\\([^()]+\\)")) {
            calculateSimple(it.value.substring(1, it.value.lastIndex)).toString()
        }
    }
    return calculateSimple(result)
}

fun calculateSimple(operation: String): Long {  // a * b + c (no parentheses)
    var result = operation.split(" ")
    while (result.contains("+")) { // resolve plus
        val plusIdx = result.indexOf("+")
        val operationResult = result[plusIdx - 1].toLong() + result[plusIdx + 1].toLong()
        result = result.take(plusIdx - 1) + listOf(operationResult.toString()) + result.drop(plusIdx + 2)
    }
    // multiply the rest
    return result.filter { it != "*" }.map { it.toLong() }.fold(1.toLong(), { a, b -> a * b })
}

const val day = 18
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