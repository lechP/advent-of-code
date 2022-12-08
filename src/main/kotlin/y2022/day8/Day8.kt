package y2022.day8

import common.printSolutions
import java.lang.Integer.min


fun task1(input: List<String>): Int {
    val trees = input.map { line -> line.toCharArray().map { it.digitToInt() } }
    val rows = trees.size
    val columns = trees.first().size
    val treesTransposed = trees.transpose()
    val edgeTrees = (rows + columns - 2) * 2
    var insideVisibleTrees = 0
    for (row in 1..rows - 2)
        for (col in 1..columns - 2) {
            val treesToEdge = listOf(
                trees[row].take(col),
                trees[row].drop(col + 1),
                treesTransposed[col].take(row),
                treesTransposed[col].drop(row + 1),
            )
            if (trees[row][col].isVisible(treesToEdge)) {
                insideVisibleTrees += 1
            }
        }

    return edgeTrees + insideVisibleTrees
}

fun Int.isVisible(treesToEdge: List<List<Int>>): Boolean =
    treesToEdge.sortedBy { it.size }.any { treesLine -> treesLine.all { it < this } }

fun List<List<Int>>.transpose(): List<List<Int>> {
    val columns = first().size
    val result = mutableListOf<List<Int>>()
    for (i in 0 until columns) {
        result.add(map { it[i] })
    }
    return result.toList()
}

fun Int.scenicScore(treesToEdge: List<List<Int>>): Int =
    treesToEdge.map { treesLine -> min(treesLine.size, treesLine.takeWhile { it < this }.size + 1) }
        .reduce { acc, i -> i * acc }

fun task2(input: List<String>): Int {
    val trees = input.map { line -> line.toCharArray().map { it.digitToInt() } }
    val rows = trees.size
    val columns = trees.first().size
    val treesTransposed = trees.transpose()
    var maxScenicScore = 0
    for (row in 1..rows - 2)
        for (col in 1..columns - 2) {
            val treesToEdge = listOf(
                trees[row].take(col).reversed(),
                trees[row].drop(col + 1),
                treesTransposed[col].take(row).reversed(),
                treesTransposed[col].drop(row + 1),
            )
            val scenicScore = trees[row][col].scenicScore(treesToEdge)
            if (scenicScore > maxScenicScore) {
                maxScenicScore = scenicScore
            }
        }

    return maxScenicScore
}


fun main() = printSolutions(8, 2022, { input -> task1(input) }, { input -> task2(input) })