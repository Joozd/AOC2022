package day23

import common.Solution
import common.grids.Coordinate
import common.grids.gridString
import common.linearalgebra.IntVector

class Day23: Solution(23) {
    // This takes care of which direction is tried first
    private val directionsIterator = DirectionsIterator()

    // The map is shared among answer 1 and 2.
    // Usually not a big fan of late init, but it needs to be filled in answer1 for timing.
    private lateinit var map: Map<IntVector, Elf>
    override fun answer1(input: List<String>): Any {
        map = initialMap(input)
        repeat(10){
            doRound()
        }
        return score(map)
    }

    override fun answer2(input: List<String>): Int{
        var round = 10
        while(doRound())
            round++

        return round + 1 // "round" is the last round in which an elf moved.
    }

    /**
     * Fill initial map with elves.
     */
    private fun initialMap(input: List<String>): Map<IntVector, Elf> =
        buildMap {
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    if(c == '#')
                        put(IntVector(x, y), Elf())
                }
            }
        }

    /**
     * Returns true if elves moved in this round
     */
    private fun doRound(): Boolean {
        map.entries.forEach { locationWithElf ->
            locationWithElf.value.proposeNextPos(locationWithElf.key, directionsIterator, map)
        }
        // count the proposed locations. Only the ones that are unique (count == 1) will get used.
        val proposedPositionCounts = map.values.groupingBy { it.proposedNextPos }.eachCount()

        var somethingChanged = false
        // build the new map according to the valid moves
        map = buildMap {
            map.entries.forEach { locationWithElf ->
                if (proposedPositionCounts[locationWithElf.value.proposedNextPos] == 1) {
                    locationWithElf.value.proposedNextPos?.let { put(it, locationWithElf.value) }
                    somethingChanged = true
                }
                else
                    put(locationWithElf.key, locationWithElf.value)
            }
        }
        directionsIterator.rotate()

        return somethingChanged
    }

    private fun score(map: Map<IntVector, Elf>): Int =
        map.keys.map { Coordinate(it[0], it[1])}.gridString().count { it == '.'}
}