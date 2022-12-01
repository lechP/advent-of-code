package y2020.day25

import java.io.File

fun task1(input: List<String>): Int {
    val dist =
        input.filter { it.startsWith("Response") && it.contains("404") }.map { it.split("\"")[3] }.distinct().sorted()
    val x = dist.groupBy { it.contains("refunds") }
    val refunds = x[true]!!.map { url -> url.split("/").let { "${it[3]},${it[5]}" } }

    val o =
        """
            1112878272275
            1137856250836
            1160099249004
            1229807080683
            1254579146882
            1295190019242
            1313810959103
            1332790643025
            1339660797220
            1350010587617
            1415717048161
            1516862556620
            1650205289491
            1721936572820
            1824328621824
            1861577195171
            1875434809360
            1995994490598
        """.trimIndent().split("\n")

    val orders = x[false]!!.map { url -> url.split("/").let { it[3] } }.filter { !o.contains(it) }
    println(refunds.size)
    println(orders.size)
    println("refunds:\n${refunds.joinToString("\n")}")
    println("orders:\n${orders.joinToString("\n")}")



    return -1
}

fun task2(input: List<String>): Int {

    return -1
}

const val day = 25
const val srcdir = "src/main/resources/y2020/day"
const val inputFile = "$srcdir$day/input"
const val testInputFile = "$srcdir$day/testInput"

fun main() {
    val testInput = File(testInputFile).useLines { it.toList() }
    val input = File(inputFile).useLines { it.toList() }

    println(task1(testInput))
//    println(task1(input))
    println(task2(testInput))
    println(task2(input))
}