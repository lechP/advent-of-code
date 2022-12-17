package y2022.day17

import java.io.File

//{
//    val rocks = 1000000000000
//    val preamble = listOf(1, 3, 2, 1, 2, 1, 3, 2, 2, 0, 1, 3, 2, 0, 2)
//    val pattern =
//        listOf(1, 3, 3, 4, 0, 1, 2, 3, 0, 1, 1, 3, 2, 2, 0, 0, 2, 3, 4, 0, 1, 2, 1, 2, 0, 1, 2, 1, 2, 0, 1, 3, 2, 0, 0)
//    println(preamble.size)
//    println(pattern.size)
//    val cycles = (rocks - preamble.size) / pattern.size
//    val reminderSize = (rocks - preamble.size) % pattern.size.toLong()
//
//    val result = preamble.sum().toLong() + cycles*pattern.sum() + pattern.take(reminderSize.toInt()).sum()
//    println(result)
//}

// pattern in 1:
// 1, 3, 3, 4, 0, 1, 2, 3, 0, 1, 1, 3, 2, 2, 0, 0, 2, 3, 4, 0, 1, 2, 1, 2, 0, 1, 2, 1, 2, 0, 1, 3, 2, 0, 0

// pattern in 2:

fun main() {
    val srcdir = "src/main/resources/y2022/day17"
    val inputFile = "$srcdir/input"

    val gasPattern = File(inputFile).useLines { it.toList() }.first()

    val peakGrowths = simulateRocks(10000, gasPattern).second

    val patternLength = 1221
    val patternCandidate = peakGrowths.takeLast(patternLength)
    val indexes = peakGrowths.windowed(patternLength).mapIndexedNotNull { index, sublist ->
        if (sublist == patternCandidate) index else null
    }
    println(indexes)
    val diffs = indexes.mapIndexedNotNull { index, i -> if (index < indexes.size - 1) indexes[index + 1] - i else null }
    println(diffs)


    val rocks = 1000000000000
    val preamble = peakGrowths.take(54)
    val pattern = peakGrowths.drop(54).take(1745)

    val cycles = (rocks - preamble.size) / pattern.size
    val reminderSize = (rocks - preamble.size) % pattern.size.toLong()

    val result = preamble.sum().toLong() + cycles*pattern.sum() + pattern.take(reminderSize.toInt()).sum()
    println(result)
}