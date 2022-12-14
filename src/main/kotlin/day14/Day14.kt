package day14

import common.Solution
import common.extensions.grabInts
import common.grids.Coordinate
import common.grids.CoordinateWithValue
import common.grids.imaging.PNGMap
import common.grids.lineTo

/**
 * The naive way (dropping sand one at at time) takes about 1 ms for 1,
 * and 55 ms (not sure where the big split comes from) for 2.
 * Finding edges and corners and filling from there would be much faster I presume,
 * but I didn't put in the time for that.
 */
class Day14: Solution(14) {
    // this takes about 1.0ms
    override fun answer1(input: List<String>) = countSandDropped(input)

    // this takes about 55ms
    override fun answer2(input: List<String>) = countSandDropped(input, true)


    private fun countSandDropped(input: List<String>, hasFloor: Boolean = false, fileNameForImage: String? = null): Int{
        val cave = buildCave(input.distinct()) // lot of duplicate inputs, no need to do all that double work
        val lowest = cave.keys.maxBy {it.y}.y // could do this in Sand, but now I have to do it only once.
        val initialPos = Coordinate(500,0)
        var count = 0
        while(Sand(initialPos, cave, lowest, hasFloor).drop()){
            count++
        }
        fileNameForImage?.let { fileName ->
            PNGMap(cave.keys.map { CoordinateWithValue(it, true) }) {
                if (cave[it] is DroppedSand) 0xC2B280 else 0xFFFFFF
            }.saveImage(fileName)
        }

        return count
    }

    private fun buildCave(lines: List<String>): MutableMap<Coordinate, Coordinate> {
        val cave = HashMap<Coordinate, Coordinate>()
        lines.forEach{ line ->
            val corners = line.grabInts().chunked(2).map {Coordinate(it[0], it[1])}
            (0 until corners.size - 1).forEach{ i ->
                corners[i].lineTo(corners[i+1]).forEach {
                    cave[it] = it
                }
            }
        }


        return cave
    }
}