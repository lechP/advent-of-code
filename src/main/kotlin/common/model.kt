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

    fun left() = Coordinate(row, col - 1)
    fun right() = Coordinate(row, col + 1)
    fun up() = Coordinate(row - 1, col)
    fun down() = Coordinate(row + 1, col)

    fun directNeighbors(): Set<Coordinate> = setOf(left(), right(), up(), down())

}

data class Point(
    val x: Int,
    val y: Int,
)

data class Point2D(
    val x: Int,
    val y: Int,
) {
    operator fun minus(other: Point2D): Point2D =
        Point2D(this.x - other.x, this.y - other.y)
    operator fun plus(other: Point2D): Point2D =
        Point2D(this.x + other.x, this.y + other.y)
}

data class Point3D(
    val x: Int,
    val y: Int,
    val z: Int
)