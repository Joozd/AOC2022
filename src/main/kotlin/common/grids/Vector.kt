package common.grids

class Vector(val directionX: Int, val directionY: Int): Comparable<Vector> {
    override fun equals(other: Any?) = if (other !is Vector) false else
        other.directionX == directionX && other.directionY == directionY
    /**
     * Bigger vector by manhattan distance
     */
    override operator fun compareTo(other: Vector): Int {
        return (directionX + directionY).compareTo(other.directionX + other.directionY)
    }

    override fun hashCode(): Int =
        directionX.shl(16) + directionY

    override fun toString() = "Vector ($directionX,$directionY)"

    val reverse get() = Vector(directionX * -1, directionY * -1)
}