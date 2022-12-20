package y2022.day20

import common.printSolutions


fun task1(input: List<String>): Long = decrypt(input)

// inspired by https://github.com/Jrmyy
private fun decrypt(input: List<String>, decryptionKey: Int = 1, mixTimes: Int = 1): Long {
    val numbers = input.mapIndexed { index, s -> index to s.toLong() * decryptionKey }
    val size = numbers.size
    val copy = numbers.toMutableList()
    repeat(mixTimes) {
        numbers.forEach { pair ->
            val idx = copy.indexOf(pair)
            copy.removeAt(idx)
            copy.add((idx + pair.second).mod(copy.size), pair)
        }
    }
    val rearrangedNumbers = copy.map { it.second }
    val indexOf0 = rearrangedNumbers.indexOf(0)
    return rearrangedNumbers[(indexOf0 + 1000) % size] + rearrangedNumbers[(indexOf0 + 2000) % size] + rearrangedNumbers[(indexOf0 + 3000) % size]
}

fun task2(input: List<String>) = decrypt(input, 811589153, 10)


fun main() = printSolutions(20, 2022, { input -> task1(input) }, { input -> task2(input) })