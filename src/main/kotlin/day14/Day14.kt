package day14

import common.Solution
import common.extensions.grabInts
import common.grids.Coordinate
import common.grids.CoordinateWithValue
import common.grids.imaging.PNGMap
import common.grids.lineTo

class Day14: Solution(14) {
    override fun answer1(input: List<String>) = countSandDropped(input)


    override fun answer2(input: List<String>) = countSandDropped(input, true)

    private fun countSandDropped(input: List<String>, hasFloor: Boolean = false, fileNameForImage: String? = null): Int{
        val cave = buildCave(input)
        val lowest = cave.keys.maxBy {it.y}.y
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