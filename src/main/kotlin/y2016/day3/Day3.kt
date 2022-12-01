package y2016.day3

import common.printSolutions

fun task1(input: List<String>): Int {
    val triangleCandidates = input.map { it.replace("    ", " ").replace("   ", " ").replace("  ", " ").trim().split(" ").map { chunk -> chunk.toInt()}.sorted() }
    return triangleCandidates.filter { it[0] + it[1] > it[2] }.size
}

fun task2(input: List<String>): Int {
    val lengths = input.map { it.replace("    ", " ").replace("   ", " ").replace("  ", " ").trim().split(" ").map { chunk -> chunk.toInt()} }
    var triangles = 0
    for(i in 0 .. lengths.lastIndex step 3) {
        for(j in 0..2) {
            if(listOf(lengths[i][j], lengths[i+1][j], lengths[i+2][j]).isTriangle()) triangles++
        }
    }
    return triangles
}

fun List<Int>.isTriangle(): Boolean = sorted().let { it[0] + it[1] > it[2]}

fun main() = printSolutions(3, 2016, { input -> task1(input) }, { input -> task2(input) })