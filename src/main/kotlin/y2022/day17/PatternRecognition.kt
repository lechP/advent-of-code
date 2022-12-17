package y2022.day17

import java.io.File

// Visually discovered a pattern in growths of the tower in part1:
// 1, 3, 3, 4, 0, 1, 2, 3, 0, 1, 1, 3, 2, 2, 0, 0, 2, 3, 4, 0, 1, 2, 1, 2, 0, 1, 2, 1, 2, 0, 1, 3, 2, 0, 0

// In part2 pattern is much longer

fun main() {
    val srcdir = "src/main/resources/y2022/day17"
    val inputFile = "$srcdir/input"

    val gasPattern = File(inputFile).useLines { it.toList() }.first()

    // try with 10k rocks to be sure pattern occurs a couple of times
    val peakGrowths = simulateRocks(10000, gasPattern).second

    // manipulate with this number to detect cycle length and when does it start
    val patternCandidateLength = 1221
    val patternCandidate = peakGrowths.takeLast(patternCandidateLength)
    val indexes = peakGrowths.windowed(patternCandidateLength).mapIndexedNotNull { index, sublist ->
        if (sublist == patternCandidate) index else null
    }
    println(indexes)
    val diffs = indexes.mapIndexedNotNull { index, i -> if (index < indexes.size - 1) indexes[index + 1] - i else null }
    println(diffs)

    // taken from the above
    val preambleLength = 54
    val patternLength = 1745

    val rocks = 1000000000000
    val preamble = peakGrowths.take(preambleLength)
    val pattern = peakGrowths.drop(preambleLength).take(patternLength)

    val cycles = (rocks - preamble.size) / pattern.size
    val reminderSize = (rocks - preamble.size) % pattern.size.toLong()

    val result = preamble.sum().toLong() + cycles*pattern.sum() + pattern.take(reminderSize.toInt()).sum()
    println(result)
}