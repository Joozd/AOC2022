package common.grids

open class CoordinateWithValue<T>(x: Int, y: Int, val value: T): Coordinate(x, y) {
    constructor(coordinate: Coordinate, value: T): this(coordinate.x, coordinate.y, value)
}