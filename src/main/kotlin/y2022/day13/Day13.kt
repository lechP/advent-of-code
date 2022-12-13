package y2022.day13

import common.printSolutions
import java.util.*
import kotlin.math.min


fun task1(input: List<String>): Int {
    val pairs = input.chunked(3)
    val rightOrderedPairs = pairs.mapIndexed { index, pair ->
        val compare = compare(pair[0].parseList(), pair[1].parseList())
        if (compare == 1) index + 1 else 0
    }
    return rightOrderedPairs.sum()
}


fun task2(input: List<String>): Int {
    val dividerPackets = listOf("[[2]]", "[[6]]")
    val packets = input.filter { it.isNotBlank() } + dividerPackets
    val sortedPackets = packets.sortedWith { s1, s2 -> -compare(s1.parseList(), s2.parseList()) }

    return (sortedPackets.indexOf(dividerPackets[0]) + 1) * (sortedPackets.indexOf(dividerPackets[1]) + 1)
}

fun compare(left: List<String>, right: List<String>): Int {
    for (i in 0 until min(left.size, right.size)) {
        if (left[i].isInt() && right[i].isInt()) {
            val leftInt = left[i].toInt()
            val rightInt = right[i].toInt()
            if (leftInt < rightInt) return 1
            if (leftInt > rightInt) return -1
        } else {
            val subLeft = if (left[i].isInt()) listOf(left[i]) else left[i].parseList()
            val subRight = if (right[i].isInt()) listOf(right[i]) else right[i].parseList()
            val subResult = compare(subLeft, subRight)
            if (subResult != 0) return subResult
        }
    }
    if (left.size < right.size) return 1
    if (left.size > right.size) return -1
    //-1 left, 0 equal, 1 right ... is bigger
    return 0
}

fun String.isInt() = toIntOrNull() != null

fun String.parseList(): List<String> =
    if (isEmpty()) emptyList() else
        if (first() == '[' && last() == ']') {
            val trimmed = substring(1, length - 1)
            val stack = Stack<Char>()
            var tokenStart = 0
            val result = mutableListOf<String>()
            trimmed.forEachIndexed { index, c ->
                when (c) {
                    '[' -> stack.push(c)
                    ']' -> stack.pop()
                    ',' -> if (stack.empty()) {
                        result.add(trimmed.substring(tokenStart, index))
                        tokenStart = index + 1
                    }
                }
            }
            result.add(trimmed.substring(tokenStart, trimmed.length))
            result.toList()
        } else {
            throw RuntimeException("Incorrect array boundaries: $this")
        }


fun main() = printSolutions(13, 2022, { input -> task1(input) }, { input -> task2(input) })
