package y2022.day18

import common.Point3D
import common.printSolutions
import kotlin.math.abs


fun task1(input: List<String>): Int {
    val points = input.map { line ->
        line.split(',').let {
            Point3D(it[0].toInt(), it[1].toInt(), it[2].toInt())
        }
    }

    val pointsSet = points.toSet()

    return surfaceArea(pointsSet)
}

private fun surfaceArea(points: Set<Point3D>) = points.sumOf {
    (if (points.contains(Point3D(it.x - 1, it.y, it.z))) 0 else 1) +
            (if (points.contains(Point3D(it.x + 1, it.y, it.z))) 0 else 1) +
            (if (points.contains(Point3D(it.x, it.y - 1, it.z))) 0 else 1) +
            (if (points.contains(Point3D(it.x, it.y + 1, it.z))) 0 else 1) +
            (if (points.contains(Point3D(it.x, it.y, it.z - 1))) 0 else 1) +
            (if (points.contains(Point3D(it.x, it.y, it.z + 1))) 0 else 1)
}

fun task2(input: List<String>): Int {
    val points = input.map { line ->
        line.split(',').let {
            Point3D(it[0].toInt(), it[1].toInt(), it[2].toInt())
        }
    }.toSet()

    //find the coord with the smallest x,y, and z
    //this is likely to have external air adjacent to it
    val minimum: Point3D = points.minBy { it.x + it.y + it.z }

    //find first coordinate around the min x, y, and z that is air
    //this should be external
    val firstAir = minimum.neighbors().first { it !in points }

    //flood-fill from first air block?
    val queue = mutableListOf<Point3D>()
    queue.add(firstAir)
    val airBlocks = mutableSetOf<Point3D>()
    while (queue.size > 0) {
        val current = queue.removeFirst()
        airBlocks.add(current)
        for (neighbor in current.neighbors()) {
            if (airBlocks.contains(neighbor) || points.contains(neighbor) || queue.contains(neighbor)) continue
            //if shortest dist from any droplet is more than two, we're expanding out into infinity
            if (shortestDistToDroplet(points, neighbor) > 2) continue
            queue.add(neighbor)
        }
    }

    return airBlocks.sumOf { airBlock -> airBlock.neighbors().count { it in points } }
}

//calculate and return shortest distance to any droplet
fun shortestDistToDroplet(droplets: Set<Point3D>, pos: Point3D) = droplets.minOf { x: Point3D -> x.dist(pos) }

fun Point3D.dist(o: Point3D): Int {
    return abs(o.x - x) + abs(o.y - y) + abs(o.z - z)
}

private fun Point3D.neighbors() = setOf(
    Point3D(x + 1, y, z),
    Point3D(x - 1, y, z),
    Point3D(x, y - 1, z),
    Point3D(x, y + 1, z),
    Point3D(x, y, z - 1),
    Point3D(x, y, z + 1),
)


fun main() = printSolutions(18, 2022, { input -> task1(input) }, { input -> task2(input) })