package day24

import common.Solution
import common.linearalgebra.IntVector

class Day24: Solution(24) {
    private val origin = IntVector(0,1)
    private lateinit var target: IntVector // depends on input
    private lateinit var xRange: IntRange
    private lateinit var yRange: IntRange

    private lateinit var blizzards: Map<IntVector, List<IntVector>>
    private lateinit var possiblePositions: Set<IntVector>

    private var minutes = 0

    override fun answer1(input: List<String>): Int {
        xRange = (1 until input.first().length - 1)
        yRange = (1 until input.size - 1)

        target = IntVector(xRange.last, yRange.last + 1)

        blizzards = buildBlizzardMap(input)
        possiblePositions = setOf(origin)

        while(target !in possiblePositions)
            takeAStep()

        return minutes
    }

    override fun answer2(input: List<String>): Int{
        // finished question 1: We made it to the end for the first time!
        possiblePositions = setOf(target)
        while(origin !in possiblePositions)
            takeAStep()

        // We made it back to the start!
        possiblePositions = setOf(origin)
        while(target !in possiblePositions)
            takeAStep()

        return minutes
    }

    /**
     * Progresses time by a minute, thereby moving blizzards and moving elves in all possible universes
     */
    private fun takeAStep() {
        blizzards = updateBlizzards(blizzards)
        possiblePositions = updatePossiblePositions(possiblePositions, blizzards)

        minutes++
    }


    /**
     * Builds a map of coordinates(position vectors) to blizzards (direction vectors)
     * There can be multiple blizzards at a location, so they are in a List.
     */
    private fun buildBlizzardMap(input: List<String>): Map<IntVector, List<IntVector>> {
        val blizzardDirection = buildMap {
            put('>', IntVector.EAST)
            put('^', IntVector.NORTH)
            put('<', IntVector.WEST)
            put('v', IntVector.SOUTH)
        }
        return buildMap{
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    if(c !in ".#")
                        put(IntVector(x,y), listOf(blizzardDirection[c]!!))
                }
            }
        }
    }

    private fun updateBlizzards(currentBlizzards: Map<IntVector, List<IntVector>>): Map<IntVector, List<IntVector>> =
        buildMap<IntVector, MutableList<IntVector>> {
            currentBlizzards.entries.forEach { coordinateWithBlizzards ->
                coordinateWithBlizzards.value.forEach { direction ->
                    val nextPos = wrapToValidLocation(coordinateWithBlizzards.key + direction)
                    val blizzardsAtNewLocation = getOrElse(nextPos) { ArrayList(4) }.apply {
                        add(direction)
                    }
                    put(nextPos, blizzardsAtNewLocation) // overwrite with either old or newly created list
                }
            }
        }

    private fun wrapToValidLocation(v: IntVector): IntVector = when{
        v[0] in xRange && v[1] in yRange    -> v
        v[0] < xRange.first                 -> IntVector(xRange.last, v[1])
        v[0] > xRange.last                  -> IntVector(xRange.first, v[1])
        v[1] < yRange.first                 -> IntVector(v[0], yRange.last)
        v[1] > yRange.last                  -> IntVector(v[0], yRange.first)
        else                                -> error ("this should not happen")
    }

    private fun updatePossiblePositions(possiblePositions: Set<IntVector>, blizzards: Map<IntVector, List<IntVector>>) =
        buildSet {
            val takenPositions = blizzards.keys
            possiblePositions.forEach { currentPos ->
                getNextPossiblePositions(currentPos, takenPositions).forEach {
                    add(it)
                }
            }
        }

    /**
     * Get next possible positions from a current position. Can be none or up to 5 (n, e, s, w and stay)
     */
    private fun getNextPossiblePositions(currentPos: IntVector, takenPositions: Set<IntVector>): List<IntVector> =
        (fourNeighbours.map{ currentPos + it } + listOf(currentPos))
            .filter { it !in takenPositions }
            .filter { (it[0] in xRange && it[1] in yRange) || it == origin || it == target }

    companion object{
        private val fourNeighbours = listOf(IntVector.NORTH, IntVector.EAST, IntVector.SOUTH, IntVector.WEST)
    }
}