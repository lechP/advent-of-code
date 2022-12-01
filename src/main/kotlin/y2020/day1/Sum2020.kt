package y2020.day1

import java.io.File

class Sum2020 {

    fun find(items: List<Int>): Int? {
        for (i in 0..items.lastIndex) {
            for (j in i + 1..items.lastIndex) {
                if (items[i] + items[j] == 2020) return items[i] * items[j]
            }
        }
        return null
    }

    fun findThree(items: List<Int>): Int? {
        for (i in 0..items.lastIndex) {
            for (j in i + 1..items.lastIndex) {
                for(k in j+1..items.lastIndex) {
                    if (items[i] + items[j] + items[k] == 2020) return items[i] * items[j] * items[k]
                }
            }
        }
        return null
    }

}

fun main() {
    val input = File("src/main/resources/y2020/input").useLines { it.toList() }.map { it.toInt() }
    println(Sum2020().findThree(input))
}