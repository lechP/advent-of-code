package y2020.day14

import java.io.File
import java.lang.RuntimeException

interface Instruction

data class Mask(
    val value: String
) : Instruction

data class Mem(
    val address: Int,
    val value: Long
) : Instruction

fun List<String>.parseInstructions() = this.map {
    when {
        it.startsWith("mask") -> Mask(it.split(" ")[2])
        it.startsWith("mem") -> Mem(it.substringAfter("[").substringBefore("]").toInt(), it.split(" ")[2].toLong())
        else -> throw RuntimeException("unrecognized: $it")
    }
}

fun task1(input: List<String>): Any {
    val instructions = input.parseInstructions()
    var currentMask = ""
    val space = mutableMapOf<Int, Long>()

    instructions.forEach {
        when (it) {
            is Mask -> currentMask = it.value
            is Mem -> space[it.address] = it.value.toString(2).applyMask(currentMask).toLong(2)
        }
    }

    return space.values.sum()
}

fun String.applyMask(mask: String): String {
    val chars = CharArray(mask.length)
    val offset = mask.lastIndex - this.lastIndex
    val x = "0".repeat(offset) + this
    for (i in mask.lastIndex downTo 0) {
        if (mask[i] == 'X') {
            chars[i] = x[i]
        } else {
            chars[i] = mask[i]
        }
    }
    return String(chars)
}

fun String.applyMaskToMem(mask: String): List<Long> {
    val chars = CharArray(mask.length)
    val offset = mask.lastIndex - this.lastIndex
    val x = "0".repeat(offset) + this
    for (i in mask.lastIndex downTo 0) {
        when (mask[i]) {
            'X' -> chars[i] = 'X'
            '1' -> chars[i] = '1'
            '0' -> chars[i] = x[i]
        }
    }
    return String(chars).evaluateFloats().map { it.toLong(2) }
}

fun String.evaluateFloats(): List<String> {
    return if (this.contains('X')) {
        this.replaceFirst('X', '0').evaluateFloats() + this.replaceFirst('X', '1').evaluateFloats()
    } else {
        listOf(this)
    }
}

fun task2(input: List<String>): Any {
    val instructions = input.parseInstructions()
    var currentMask = ""
    val space = mutableMapOf<Long, Long>()

    instructions.forEach {
        when (it) {
            is Mask -> currentMask = it.value
            is Mem -> {
                val addresses = it.address.toString(2).applyMaskToMem(currentMask)
                addresses.forEach { address ->
                    space[address] = it.value
                }
            }
        }
    }

    return space.values.sum()
}

const val day = 14
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