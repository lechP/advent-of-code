package y2022.day3

import common.printSolutions

fun priority(char: Char) = char.code - if (char == char.uppercaseChar()) 38 else 96

fun String.toCompartments() = toCharArray().toList().chunked(this.length/2).map {it.toSet()}
fun List<Set<Char>>.commonElement() = this[1].intersect(this[0])

fun Set<Char>.toPriority() = if(size ==1 ) priority(this.first()) else throw RuntimeException("Size of set is $size, should be 1")

fun task1(input: List<String>) =
    input.sumOf { it.toCompartments().commonElement().toPriority() }

fun String.toRucksack() = toCharArray().toSet()

fun List<Set<Char>>.commonElementOf3() = this[0].intersect(this[1]).intersect(this[2])

fun task2(input: List<String>) = input.chunked(3).sumOf {
    it.map {line -> line.toRucksack()}.commonElementOf3().toPriority()
}

fun main() = printSolutions(3, 2022, { input -> task1(input) }, { input -> task2(input) })