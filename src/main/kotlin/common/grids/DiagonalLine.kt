package common.grids

class DiagonalLine(
    start: Coordinate,
    end: Coordinate
): Line(listOf(start, end).minBy { it.x }, listOf(start, end).maxBy { it.x }) { // line always goes left to right
    // direction = -1 if going up, 1 if going down.
    private val direction = if (this.end.y - this.start.y > 0) 1 else -1

    /**
     * Gives the intersect point or null if no intersection
     * Lines can also not intersect if they miss each other! ((when start.y - other.y) %2 == 1
     */
    override fun getIntersection(other: Line): Coordinate? {
        if (other is DiagonalLine){
            if (other.direction == direction) return null // parallel lines
            // formula of line = [direction] * x + [y at x=0]
            val yAt0 = start.y - start.x * direction
            val otherYAt0 = other.start.y - other.start.x * other.direction
            if ((yAt0 - otherYAt0) %2 == 1) return null // lines cross, but do not share a common coordinate
            val crossY = (yAt0 + otherYAt0) / 2
            val crossX = start.x + (crossY - start.y) * direction
            return Coordinate(crossX, crossY)
        }
        else
            TODO("Not implemented")
    }

    override fun contains(coordinate: Coordinate): Boolean =
        coordinate.x in (start.x..end.x) && coordinate.y-start.y == (coordinate.x-start.x) * direction

}