package y2022.day15

import common.Point
import common.printSolutions
import kotlin.math.abs


fun task1(input: List<String>): Int {
    val testedY = input.first().split(',')[0].toInt()
    val sensorOutputs = input.drop(1).map { it.toSensorOutput() }
    // hugely suboptimal - elegant solution should be a mutable set of ranges
    val ranges = sensorOutputs.mapNotNull { it.coverageOnY(testedY) }
    val r = ranges.map { it.toSet() }
    val allPositions = r.reduce { acc, intRange -> acc + intRange }
    val beaconsOnY = sensorOutputs.filter { it.beacon.y == testedY }.map { it.beacon.x }.toSet()
    println(beaconsOnY)
    return (allPositions - beaconsOnY).size
}

fun task1v2(input: List<String>): Int {
    val testedY = input.first().split(',')[0].toInt()
    val sensorOutputs = input.drop(1).map { it.toSensorOutput() }
    val ranges = sensorOutputs.mapNotNull { it.coverageOnY(testedY) }
    var wholeRange = emptyList<IntRange>()
    ranges.forEach { rng ->
        wholeRange = integrate(wholeRange, rng)
    }
    val beaconsInRange = sensorOutputs.filter { it.beacon.y == testedY }.map { it.beacon.x }.toSet()
        .count { beacon -> wholeRange.any { beacon in it } }
    return wholeRange.sumOf { it.last - it.first + 1 } - beaconsInRange
}


fun task2(input: List<String>): Long {
    val limit = input.first().split(',')[1].toInt()
    val sensorOutputs = input.drop(1).map { it.toSensorOutput() }
    val beacons = sensorOutputs.map { it.beacon }.toSet()

    for (y in 0..limit) {
        val coverage = wholeCoverageOnY(y, sensorOutputs)
        coverage.forEachIndexed { index, range ->
            if (range.last < limit) {
                val end = if (index + 1 == coverage.size) {
                    limit
                } else {
                    coverage[index + 1].first - 1
                }

                for (k in range.last + 1..end) {
                    val candidate = Point(y = y, x = k)
                    if (candidate !in beacons) {
                        return candidate.x * 4000000L + candidate.y
                    }
                }
            }
        }
    }

    return -1
}

fun wholeCoverageOnY(y: Int, sensorOutputs: List<SensorOutput>): List<IntRange> {
    val ranges = sensorOutputs.mapNotNull { it.coverageOnY(y) }
    var wholeRange = emptyList<IntRange>()
    ranges.forEach { rng ->
        wholeRange = integrate(wholeRange, rng)
    }
    return wholeRange
}


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

fun main() = printSolutions(15, 2022, { input -> task1v2(input) }, { input -> task2(input) })
