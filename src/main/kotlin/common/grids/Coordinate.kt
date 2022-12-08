package common.grids

/**
 * because Pairs are too confusing for Joozd
 */
open class Coordinate(val x: Int, val y: Int): Comparable<Coordinate> {
    constructor(coordinatesList: List<Int>): this(coordinatesList[0], coordinatesList[1])

    fun fourNeighbors() = listOf(
        Coordinate(x, y-1),
        Coordinate(x-1, y),
        Coordinate(x+1, y),
        Coordinate(x, y+1)
    )

    fun eightNeighbors() =
        (y-1..y+1).map{ y ->
            (x-1..x+1).mapNotNull { x->
                if(x == this.x && y == this.y) null
                else Coordinate(x,y)
            }
        }.flatten()

    /**
     * Make a block n steps deep on all sides
     * This is an n*2+1 by n*2+1 block, so 3x3 is distance 1, 5x5 is distance 2, etc
     */
    fun block(distance: Int = 1) =
        (y-distance..y+distance).map{ y ->
            (x-distance..x+distance).mapNotNull { x->
                Coordinate(x,y)
            }
        }.flatten()

    override fun equals(other: Any?) = if (other !is Coordinate) false else
        other.x == x && other.y == y
    override fun toString() = "($x, $y)"

    override fun hashCode(): Int  = x * 53 + y

    /**
     *  top to bottom, left to right (y=0 is top row)
     *  so in order:
     *  1,2,3
     *  4,5,6
     *  7,8,9
     */
    override fun compareTo(other: Coordinate): Int = if (y == other.y) x-other.x else y - other.y

    operator fun plus(other: Vector) = Coordinate (x+other.directionX, y+other.directionY)
}