package common.grids

open class CoordinateWithValue<T>(x: Int, y: Int, val value: T): Coordinate(x, y) {
    constructor(coordinate: Coordinate, value: T): this(coordinate.x, coordinate.y, value)

    override fun north() = CoordinateWithValue(x, y-1, value)
    override fun east() = CoordinateWithValue(x+1, y, value)
    override fun south() = CoordinateWithValue(x, y+1, value)
    override fun west() = CoordinateWithValue(x-1, y, value)
}