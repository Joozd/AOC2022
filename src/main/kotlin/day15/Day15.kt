package day15

import common.Solution
import common.extensions.contains
import common.extensions.grabInts
import common.extensions.size
import common.grids.Coordinate
import common.utils.joinRanges

class Day15: Solution(15) {
    var wantedLine = 2000000 // can be changed for testing purposes
    var maxRange = 4000000

    private lateinit var sensors: List<Sensor>

    //0.072ms
    override fun answer1(input: List<String>): Int {
        initializeSensors(input)
        val rangesOnLine = sensors.mapNotNull{ it.xRangeAtY(wantedLine) }

        return joinRanges(rangesOnLine).sumOf { it.size } - getBeaconsOnLine(input, wantedLine).size
    }

    private fun getBeaconsOnLine(input: List<String>, lineNumber: Int) = input.map { line ->
        line.grabInts().let { Coordinate(it[2], it[3]) }
    }.distinct().filter { it.y == lineNumber}

    //1400 ms :/
    override fun answer2(input: List<String>): Long {
        val targetArea = 0..maxRange

        // y coordinate is the first line where the entire target area is not completely contained
        // in the combined ranges of the sensors
        val y = targetArea.first{ l ->
            joinRanges(sensors.mapNotNull { it.xRangeAtY(l) })
                .none { it contains targetArea}
        }

        // x coordinate is the first value after the end of the first range that ends inside the target area
        val x = joinRanges(sensors.mapNotNull { it.xRangeAtY(y) })
            .first { it.last in targetArea }.last + 1

        return x * TUNING_FREQ + y
    }


    private fun initializeSensors(input: List<String>) {
        sensors = input.map { line ->
            line.grabInts().let { Sensor(it[0], it[1], it[2], it[3]) }
        }
    }

    companion object{
        private const val TUNING_FREQ = 4000000L
    }
}