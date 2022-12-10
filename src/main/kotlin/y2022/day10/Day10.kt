package y2022.day10

import common.printSolutions
import kotlin.math.abs


fun task1(input: List<String>): Int {
    val targetCycles = (0..5).map { it * 40 + 20 }
    val register = createRegister(input)
    return targetCycles.sumOf { it * register[it].during }
}

fun createRegister(input: List<String>): List<RegisterValue> {
    val register = mutableListOf(RegisterValue(1, 1)) // initial val
    input.forEach { line ->
        if (line == "noop") {
            register.add(RegisterValue(register.last().after, register.last().after))
        }
        if (line.startsWith("addx")) {
            register.add(RegisterValue(register.last().after, register.last().after))
            register.add(RegisterValue(register.last().after, register.last().after + line.split(" ")[1].toInt()))
        }
    }
    return register.toList()
}


fun task2(input: List<String>) {
    val register = createRegister(input)
    val screen = StringBuilder("")
    for (i in 1..240) {
        if (abs(register[i].during - (i % 40)+1) <= 1)
            screen.append('#')
        else
            screen.append('.')
        if (i % 40 == 0) screen.append("\n")
    }
    println(screen)
}


fun main() = printSolutions(10, 2022, { input -> task1(input) }, { input -> task2(input) })

data class RegisterValue(
    val during: Int,
    val after: Int,
)