package common.grids

import common.linearalgebra.IntVector
import kotlin.math.absoluteValue

/**
 * because Pairs are too confusing for Joozd
 */
open class Coordinate(x: Int, y: Int): Comparable<Coordinate>, IntVector(x,y) {
    constructor(v: IntVector): this(v[0], v[1])
    val x get() = vector[0]
    val y get() = vector[1]
    open fun north() = Coordinate(x, y-1)
    open fun east() = Coordinate(x+1, y)
    open fun south() = Coordinate(x, y+1)
    open fun west() = Coordinate(x-1, y)


    // Functions for when we get to do pathfinding or game of life

    fun fourNeighbors() = listOf(
        Coordinate(x, y-1),
        Coordinate(x-1, y),
        Coordinate(x+1, y),
        Coordinate(x, y+1)
    )

    fun distanceTo(other: Coordinate): Int =
        (other.y - y).absoluteValue + (other.x - x).absoluteValue

    /*
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
    */

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