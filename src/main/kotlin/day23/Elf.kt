package day23

import common.linearalgebra.IntVector
import common.linearalgebra.hasNeighboursIn

// Elves take up space, remember where they wanted to go, and every elf is unique!
class Elf {
    var proposedNextPos: IntVector? = null
        private set
    // getting currentPos as a parameter is much faster than reverse-looking it up.
    // The calling function already knows the coordinate because that's how it finds the elves.
    fun proposeNextPos(currentPos: IntVector, directions: DirectionsIterator, map: Map<IntVector, Elf>){
        if(!currentPos.hasNeighboursIn(map.keys)) {
            proposedNextPos = null
            return
        }

        val nextPos = when(getAvailableDirectionOrNull(currentPos, directions, map)){
            'N' -> currentPos + IntVector.NORTH
            'S' -> currentPos + IntVector.SOUTH
            'E' -> currentPos + IntVector.EAST
            'W' -> currentPos + IntVector.WEST
            else -> null
        }
        proposedNextPos = nextPos
    }



    private fun getAvailableDirectionOrNull(
        currentPos: IntVector,
        directions: DirectionsIterator,
        map: Map<IntVector, Elf>
    ): Char? = directions.firstOrNull { dir ->
        val directionsToCheck = when(dir){
            'N' -> listOf(currentPos + IntVector.NW, currentPos + IntVector.NORTH, currentPos + IntVector.NE)
            'S' -> listOf(currentPos + IntVector.SW, currentPos + IntVector.SOUTH, currentPos + IntVector.SE)
            'E' -> listOf(currentPos + IntVector.NE, currentPos + IntVector.EAST, currentPos + IntVector.SE)
            'W' -> listOf(currentPos + IntVector.NW, currentPos + IntVector.WEST, currentPos + IntVector.SW)
            else -> error ("Wrong direction \'$dir\'!")
        }
        map.positionsAvailable(directionsToCheck)
    }

    /**
     * Checks if all [directions] are empty in [map].
     */
    private fun Map<IntVector, Elf>.positionsAvailable(directions: List<IntVector>): Boolean =
        directions.none{ it in keys }
}