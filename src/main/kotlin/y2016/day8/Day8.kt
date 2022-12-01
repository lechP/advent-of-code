package y2016.day8

import common.printSolutions
import java.lang.RuntimeException

interface Operation

data class TurnOn(
    val x: Int,
    val y: Int
) : Operation

data class Rotate(
    val dim: Dim,
    val id: Int,
    val offset: Int
) : Operation

enum class Dim {
    R, C
}

fun String.toOperation() =
    when {
        startsWith("rect") -> substringAfter("rect ").split("x").let { TurnOn(it.first().toInt(), it[1].toInt()) }
        startsWith("rotate") -> split(" ").let {
            Rotate(
                dim = if (it[1] == "column") Dim.C else Dim.R,
                id = it[2].split("=")[1].toInt(),
                offset = it[4].toInt()
            )
        }
        else -> throw RuntimeException("unrecognized: $this")
    }


fun task1(input: List<String>): Int {
    val columns = 50 //x
    val rows = 6 //y

    val screen: MutableList<MutableList<Int>> = MutableList(rows) { MutableList(columns) { 0 } }

    input.map { it.toOperation() }.forEach { op ->
        when (op) {
            is TurnOn -> {
                for (i in 0 until op.y) {
                    for (j in 0 until op.x) {
                        screen[i][j] = 1
                    }
                }
            }
            is Rotate -> {
                if (op.dim == Dim.C) {
                    val copy = MutableList(rows) { 0 }
                    for (i in 0 until rows) {
                        copy[(i + op.offset) % rows] = screen[i][op.id]
                    }
                    for (i in 0 until rows) {
                        screen[i][op.id] = copy[i]
                    }
                }
                if (op.dim == Dim.R) {
                    val copy = MutableList(columns) { 0 }
                    for (i in 0 until columns) {
                        copy[(i + op.offset) % columns] = screen[op.id][i]
                    }
                    for (i in 0 until columns) {
                        screen[op.id][i] = copy[i]
                    }
                }

            }
        }
    }

    //task2
    println(screen.joinToString("\n") { it.joinToString("") { num -> if (num == 0) "." else "#" } })

    return screen.map { it.sum() }.sum()
}

fun task2(input: List<String>): Int {

    return -1
}

fun main() = printSolutions(8, 2016, { input -> task1(input) }, { input -> task2(input) })