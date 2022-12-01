package y2020.day4

import java.io.File

class Day4 {

    private fun String.transform() = split(" ")
        .map { it.split(":") }
        .map { it[0] to it[1] }
        .toMap()

    fun count(input: List<String>): Int {
        val map = emptyMap<String, String>().toMutableMap()
        val parsedResult = mutableListOf<Map<String, String>>()
        for (inp in input) {
            if (inp == "") {
                parsedResult.add(HashMap(map))
                map.clear()
            } else {
                map += inp.transform()
            }
        }
        parsedResult.add(HashMap(map))
        map.clear()

        return parsedResult.count { it.isValid2() }
    }

    private fun Map<String, String>.isValid() =
        containsKey("byr") && containsKey("iyr") && containsKey("eyr") && containsKey("hgt")
                && containsKey("hcl") && containsKey("ecl") && containsKey("pid")

    private fun Map<String, String>.isValid2() =
        containsKey("byr") && containsKey("iyr") && containsKey("eyr") && containsKey("hgt")
                && containsKey("hcl") && containsKey("ecl") && containsKey("pid")
                && get("byr")!!.byrValid()
                && get("iyr")!!.iyrValid()
                && get("eyr")!!.eyrValid()
                && get("hgt")!!.hgtValid()
                && get("hcl")!!.hclValid()
                && get("ecl")!!.eclValid()
                && get("pid")!!.pidValid()


}

fun String.byrValid() = toInt() in 1920..2002
fun String.iyrValid() = toInt() in 2010..2020
fun String.eyrValid() = toInt() in 2020..2030
fun String.hgtValid() =
    when {
        endsWith("cm") -> substringBefore("cm").toInt() in 150..193
        endsWith("in") -> substringBefore("in").toInt() in 59..76
        else -> false
    }

fun String.hclValid() = matches(Regex("^#[0-9a-f]{6}$"))
fun String.eclValid() = matches(Regex("^amb|blu|brn|gry|grn|hzl|oth$")) && length == 3
fun String.pidValid() = matches(Regex("^[0-9]{9}$"))

/*

    byr (Birth Year) - four digits; at least 1920 and at most 2002.
    iyr (Issue Year) - four digits; at least 2010 and at most 2020.
    eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
    hgt (Height) - a number followed by either cm or in:
        If cm, the number must be at least 150 and at most 193.
        If in, the number must be at least 59 and at most 76.
    hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
    ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
    pid (Passport ID) - a nine-digit number, including leading zeroes.
    cid (Country ID) - ignored, missing or not.

 */

fun main() {
    val input = File("src/main/resources/y2020/day4/input").useLines { it.toList() }
    println(Day4().count(input))
}