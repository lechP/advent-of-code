package common

data class Coordinate(
    val row: Int,
    val col: Int,
) {
    fun neighbors(): Set<Coordinate> =
        (-1..1).flatMap { r ->
            (-1..1).mapNotNull { c ->
                if (r == 0 && c == 0) null else Coordinate(row + r, col + c)
            }
        }.toSet()
}

data class Point(
    val x: Int,
    val y: Int,
)

data class Point3D(
    val x: Int,
    val y: Int,
    val z: Int
)