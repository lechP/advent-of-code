package y2022.day15

import kotlin.math.max
import kotlin.math.min

internal fun integrate(ranges: List<IntRange>, newRange: IntRange): List<IntRange> {
    val leftSubList = ranges.takeWhile { range -> range.last < newRange.first }
    val rightSubList = ranges.takeLastWhile { range -> range.first > newRange.last }
    val middleRange = middleRange(ranges, leftSubList, rightSubList, newRange)

    val joinLastLeft = leftSubList.isNotEmpty() && leftSubList.last().last + 1 == middleRange.first
    val joinFirstRight = rightSubList.isNotEmpty() && middleRange.last + 1 == rightSubList.first().first

    return when {
        joinLastLeft && joinFirstRight ->
            leftSubList.dropLast(1) +
                    listOf(leftSubList.last().first..rightSubList.first().last) + rightSubList.drop(1)

        joinLastLeft ->
            leftSubList.dropLast(1) + listOf(leftSubList.last().first..middleRange.last) + rightSubList

        joinFirstRight ->
            leftSubList + listOf(middleRange.first..rightSubList.first().last) + rightSubList.drop(1)

        else ->
            leftSubList + listOf(middleRange) + rightSubList
    }
}

private fun middleRange(
    ranges: List<IntRange>,
    leftSublist: List<IntRange>,
    rightSublist: List<IntRange>,
    newRange: IntRange
): IntRange {
    val leftBoundary = if (leftSublist.size < ranges.size) {
        min(newRange.first, ranges[leftSublist.size].first)
    } else {
        newRange.first
    }
    val rightBoundary = if (rightSublist.size < ranges.size) {
        max(newRange.last, ranges[ranges.size - rightSublist.size - 1].last)
    } else {
        newRange.last
    }
    return leftBoundary..rightBoundary
}