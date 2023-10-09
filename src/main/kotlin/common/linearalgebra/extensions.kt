package common.linearalgebra

/**
 * For performance, use a HashSet for [otherPositionVectors] if it is a large list.
 */
fun IntVector.hasNeighboursIn(otherPositionVectors: Collection<IntVector>): Boolean =
    listOf(
        IntVector.NW,
        IntVector.NORTH,
        IntVector.NE,

        IntVector.WEST,
        IntVector.EAST,

        IntVector.SW,
        IntVector.SOUTH,
        IntVector.SE).any {
            this + it in otherPositionVectors
}

