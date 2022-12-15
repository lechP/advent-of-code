package y2022.day15

// listOf(ranges...) - must be sorted
// dodaje nowy range
// jesli left boundary jest within range to spoko
// jesli nie, to wsadzamy nowy range i kleimy az do osiagniecia right boundary
internal fun integrate(ranges: List<IntRange>, newRange: IntRange): List<IntRange> {
    if (ranges.isEmpty()) {
        return listOf(newRange)
    } else {
        val min = ranges.first().first
        val max = ranges.last().last
        val firstLeftAfterNewLeft = ranges.indexOfFirst { it.first > newRange.first }
        val firstLeftAfterNewRight = ranges.indexOfFirst { it.first > newRange.last }
        val firstRightAfterNewLeft = ranges.indexOfFirst { it.last > newRange.first }
        val firstRightAfterNewRight = ranges.indexOfFirst { it.last > newRange.last }

        val lastLeftBeforeNewLeft = ranges.indexOfLast { it.first < newRange.first }
        val lastLeftBeforeNewRight = ranges.indexOfLast { it.first < newRange.last }
        val lastRightBeforeNewLeft = ranges.indexOfLast { it.last < newRange.first }
        val lastRightBeforeNewRight = ranges.indexOfLast { it.last < newRange.last }

        val indexOfContainingNewLeft = ranges.indexOfFirst { newRange.first in it }
        val indexOfContainingNewRight = ranges.indexOfFirst { newRange.last in it }

        return when {
            // new is before all
            newRange.last < min -> if (newRange.last + 1 == min) {
                listOf(newRange.first..ranges.first().last) + ranges.drop(1)
            } else {
                listOf(newRange) + ranges
            }
            // new is after all
            max < newRange.first -> if (max + 1 == newRange.first) {
                ranges.dropLast(1) + listOf(ranges.last().first..newRange.last)
            } else {
                ranges + listOf(newRange)
            }
            // both ends overlapping
            indexOfContainingNewLeft > -1 && indexOfContainingNewRight > -1 ->
                ranges.take(indexOfContainingNewLeft) + listOf(
                    ranges[indexOfContainingNewLeft].first..ranges[indexOfContainingNewRight].last
                ) + ranges.drop(indexOfContainingNewRight + 1)
            // covers no overlapping case: middle
            lastLeftBeforeNewLeft + 1 == firstRightAfterNewRight ->
                ranges.take(lastLeftBeforeNewLeft + 1) + listOf(newRange) + ranges.drop(lastLeftBeforeNewLeft + 1)

            else -> emptyList()
        }

    }
}