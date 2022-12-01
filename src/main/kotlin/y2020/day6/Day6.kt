package y2020.day6

import java.io.File


fun countAnswers(input: List<String>): Int {
    val set = emptySet<Char>().toMutableSet()
    var result = 0
    for (inp in input) {
        if (inp == "") {
            result += set.size
            set.clear()
        } else {
            set.addAll(inp.toCharArray().toSet())
        }
    }

    return result + set.size
}

fun countEveryoneAnswers(input: List<String>): Int {
    var set = emptySet<Char>().toMutableSet()
    var result = 0
    var newGroup = true
    for (inp in input) {
        if (inp == "") {
            result += set.size
            set.clear()
            newGroup = true
        } else {
            if(newGroup) {
                set.addAll(inp.toCharArray().toSet())
                newGroup = false
            } else {
                set = set.intersect(inp.toCharArray().toSet()).toMutableSet()
            }
        }
    }

    return result + set.size
}

fun main() {
    val input = File("src/main/resources/y2020/day6/input").useLines { it.toList() }
    println(countAnswers(input))
    println(countEveryoneAnswers(input))
}

