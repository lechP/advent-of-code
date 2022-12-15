package y2022.day15

import common.Point
import common.printSolutions
import kotlin.math.abs


fun task1(input: List<String>): Int {
    val testedY = input.first().toInt()
    val sensorOutputs = input.drop(1).map { it.toSensorOutput() }
    // hugely suboptimal - elegant solution should be a mutable set of ranges
    val ranges = sensorOutputs.mapNotNull { it.coverageOnY(testedY) }.map { it.toSet() }
    val allPositions = ranges.reduce { acc, intRange -> acc + intRange }
    val beaconsOnY = sensorOutputs.filter { it.beacon.y == testedY }.map { it.beacon.x }.toSet()
    return (allPositions - beaconsOnY).size
}


fun task2(input: List<String>) = -1

//Sensor at x=3050752, y=2366125: closest beacon is at x=2715626, y=2000000
private fun String.toSensorOutput() = split("=").let {
    SensorOutput(
        sensor = Point(
            x = it[1].dropLast(", y".length).toInt(),
            y = it[2].dropLast(": closest beacon is at x".length).toInt(),
        ),
        beacon = Point(
            x = it[3].dropLast(", y".length).toInt(),
            y = it[4].toInt(),
        ),
    )
}

data class SensorOutput(
    val sensor: Point,
    val beacon: Point,
) {
    fun coverageOnY(y: Int): IntRange? {
        val distance = abs(sensor.x - beacon.x) + abs(sensor.y - beacon.y)
        val halfDistanceToY = abs(sensor.y - y)
        val availableDistance = distance - halfDistanceToY
        return if (availableDistance >= 0) {
            sensor.x - availableDistance..sensor.x + availableDistance
        } else {
            null
        }
    }
}

fun main() = printSolutions(15, 2022, { input -> task1(input) }, { input -> task2(input) })
