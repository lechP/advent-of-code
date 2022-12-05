package common

import java.io.File

const val year = 2022
const val day = 5

fun main() {

    val srcDirectory = "src/main/kotlin/y$year/day$day"
    val resourcesDirectory = "src/main/resources/y$year/day$day"

    if (File(srcDirectory).exists()) {
        println("Files to generate already exist")
    } else {
        File(srcDirectory).mkdirs()
        File("$srcDirectory/Day$day.kt").writeText(solutionFileTemplate)

        File(resourcesDirectory).mkdirs()
        File("$resourcesDirectory/input").createNewFile()
        File("$resourcesDirectory/testInput").createNewFile()
    }
}

val solutionFileTemplate = """
package y$year.day$day

import common.printSolutions


fun task1(input: List<String>) = -1


fun task2(input: List<String>) = -1


fun main() = printSolutions($day, $year, { input -> task1(input) }, { input -> task2(input) })
""".trimIndent()