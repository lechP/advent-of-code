package y2020.day3

import java.io.File

class Trees {

    private fun String.toLine() = toCharArray().map {
        when(it) {
            '#' -> 1
            '.' -> 0
            else -> 0
        }
    }

    fun count(input: List<String>): Int {
        val map = input.map { it.toLine() }
        var trees = 0
        val period = map[0].size
        for(i in 2..map.lastIndex step 2) {  // 2.. step 2
            trees += map[i][i/2 % period]
        }
        return trees
    }
}

fun main() {
    val input = File("src/main/resources/y2020/day3/input").useLines { it.toList() }
    println(Trees().count(input))
    val a: Long = 80
    val b: Long = 162
    val c: Long = 77
    val d: Long = 83
    val e: Long = 37
    println(a*b*c*d*e)
}