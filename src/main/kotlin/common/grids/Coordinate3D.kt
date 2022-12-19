package common.grids

import common.extensions.grabInts

class Coordinate3D(val x: Int, val y: Int, val z: Int) {
    fun sixNeighbours() = listOf(
        Coordinate3D(x, y, z-1),
        Coordinate3D(x, y-1, z),
        Coordinate3D(x-1, y, z),
        Coordinate3D(x+1, y, z),
        Coordinate3D(x, y+1, z),
        Coordinate3D(x, y, z+1)
    )

    override fun equals(other: Any?) = if (other !is Coordinate3D) false else
        other.x == x && other.y == y && other.z == z
    override fun toString() = "($x, $y, $z)"
    override fun hashCode(): Int = 31 * (31 * x + y) + z

    companion object{
        fun of(line: String) = line.grabInts().let{
            Coordinate3D(it[0], it[1], it[2])
        }
    }


}