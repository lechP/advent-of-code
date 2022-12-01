package y2016.day7

import common.printSolutions

fun task1(input: List<String>): Int {
    return input.filter { ip ->
        ip.splitIP().let {
            val outsiders = it.filterIndexed { index, _ -> index % 2 == 0 }
            val insiders = it.filterIndexed { index, _ -> index % 2 == 1 }
            outsiders.any { it.containsAbba() } && insiders.none { it.containsAbba() }
        }
    }.size
}

fun String.splitIP(): List<String> =
    replace("[", ",").replace("]", ",").split(",")


fun String.containsAbba(): Boolean {
    val asChars = toCharArray()
    for (i in 0..asChars.lastIndex - 3) {
        if (asChars[i] == asChars[i + 3] && asChars[i + 1] == asChars[i + 2] && asChars[i] != asChars[i + 1]) return true
    }
    return false
}

fun task2(input: List<String>): Int {
    return input.filter { ip ->
        ip.splitIP().let {
            val outsiders = it.filterIndexed { index, _ -> index % 2 == 0 }
            val insiders = it.filterIndexed { index, _ -> index % 2 == 1 }
            val allABAs = outsiders.flatMap { outsider -> outsider.getABAs()}
            insiders.any { insider ->
                insider.getABAs().map { aba -> Pair(aba.second, aba.first) }
                    .any { insiderABA -> allABAs.contains(insiderABA) }
            }
        }
    }.size
}

fun String.getABAs(): List<Pair<Char, Char>> {
    val asChars = toCharArray()
    var ABAs = mutableListOf<Pair<Char, Char>>()
    for (i in 0..asChars.lastIndex - 2) {
        if (asChars[i] == asChars[i + 2] && asChars[i] != asChars[i + 1]) ABAs.add(asChars[i] to asChars[i + 1])
    }
    return ABAs
}

fun main() = printSolutions(7, 2016, { input -> task1(input) }, { input -> task2(input) })