package common.grids

abstract class Line(val start: Coordinate, val end: Coordinate){
    override fun toString(): String = "Line $start -> $end"

    abstract fun getIntersection(other: Line): Coordinate?

    abstract operator fun contains(coordinate: Coordinate): Boolean
}
