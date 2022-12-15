package day14

import common.Solution
import common.extensions.grabInts
import common.grids.Coordinate
import common.grids.lineTo

/**
 * The naive way (dropping sand one at at time) takes about 1 ms for 1,
 * and 55 ms (not sure where the big split comes from) for 2.
 * Recursively tracing a path down and marking every visited position is about 15 times faster. (3.5 ms)
 * Finding edges and corners and filling from there would be much faster I presume,
 * but I didn't put in the time for that.
 */
class Day14: Solution(14) {

    override fun answer1(input: List<String>) =
        countSandDropped(input) // this takes about 1.0ms


    override fun answer2(input: List<String>) =
        countSandDropped2(input) // this takes about 3.5 ms
        // countSandDropped(input, true) // this takes about 55ms


    private fun countSandDropped(input: List<String>, hasFloor: Boolean = false): Int{
        val cave = buildCave(input.distinct()) // lot of duplicate inputs, no need to do all that double work
        val lowest = cave.maxBy {it.y}.y // could do this in Sand, but now I have to do it only once.
        val initialPos = Coordinate(500,0)
        var count = 0
        while(Sand(initialPos, cave, lowest, hasFloor).drop()){
            count++
        }
        return count
    }

    private fun countSandDropped2(input: List<String>): Int{
        val cave = buildCave(input)
        return dropSandRecursive(Coordinate(500,0), cave, cave.maxBy {it.y}.y + 1 ) + 1 //+ 1 for start pos
    }

    // sand needs to be added to set to prevent double counts from adjacent sands.
    private fun dropSandRecursive(coordinate: Coordinate, cave: MutableSet<Coordinate>, lowestPos: Int): Int{
        cave.add(coordinate)
        val d = coordinate.south()
        return listOf(d.west(), d, d.east()).filter { it !in cave }.sumOf{
            if (it in cave || it.y > lowestPos) 0
            else dropSandRecursive(it, cave, lowestPos) + 1
        }
    }

    private fun buildCave(lines: List<String>): MutableSet<Coordinate> {
        val cave = HashSet<Coordinate>()
        lines.forEach{ line ->
            val corners = line.grabInts().chunked(2).map {Coordinate(it[0], it[1])}
            (0 until corners.size - 1).forEach{ i ->
                cave.addAll(corners[i].lineTo(corners[i+1]))
            }
        }

        return cave
    }
}