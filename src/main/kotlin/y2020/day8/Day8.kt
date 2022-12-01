package y2020.day8

import java.io.File

data class Operation(
    val name: String,
    val value: Int
)

fun String.toOperation() =
    split(" ").let {
        Operation(it[0], it[1].substringAfter('+').toInt())
    }

fun task1(input: List<String>): Int {
    var accumulator = 0
    var current = 0
    var visited = mutableSetOf<Int>()
    val operations = input.map { it.toOperation() }

    while (!visited.contains(current)) {
        visited.add(current)
        when (operations[current].name) {
            "acc" -> {
                accumulator += operations[current].value
                current++
            }
            "nop" -> {
                current++
            }
            "jmp" -> {
                current += operations[current].value
            }
        }
    }

    return accumulator
}

fun task2(input: List<String>): Int {
    val operations = input.map { it.toOperation() }
    var candidatesIndexes = mutableListOf<Int>()
    operations.forEachIndexed { index, operation ->
        if(operation.name == "nop" || operation.name == "jmp") candidatesIndexes.add(index)
    }
    for(index in candidatesIndexes) {
        var visited = mutableSetOf<Int>()
        var current = 0
        var accumulator = 0
        val modifiedOperations = operations.mapIndexed { idx, operation -> if(idx == index) operation.flip() else operation }

        while (!visited.contains(current)) {
            if(current == modifiedOperations.size) return accumulator
            visited.add(current)
            when (modifiedOperations[current].name) {
                "acc" -> {
                    accumulator += modifiedOperations[current].value
                    current++
                }
                "nop" -> {
                    current++
                }
                "jmp" -> {
                    current += modifiedOperations[current].value
                }
            }
        }
    }
    return -1
}

fun Operation.flip() =
    when(name) {
        "nop" -> Operation("jmp", value)
        "jmp" -> Operation("nop", value)
        "acc" -> this
        else -> this
    }


fun main() {
    val testInput = File("src/main/resources/y2020/day8/testInput").useLines { it.toList() }
    val input = File("src/main/resources/y2020/day8/input").useLines { it.toList() }


    println(task1(testInput))
    println(task1(input))
    println(task2(testInput))
    println(task2(input))
}