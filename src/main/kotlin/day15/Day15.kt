package day15

import common.Solution
import common.extensions.grabInts
import common.extensions.size
import common.grids.Coordinate
import common.grids.DiagonalLine
import common.utils.joinRanges

/**
 * Part 1: Get cross section entry and exit coordinates for every sensor, combine them and sum by size.
 * Part 2: Initially: bruteforce (a line takes maybe a few us so doing that 4 million times is a few seconds at most
 * Part 2 smart: There is only one open point. This must be at a point exactly at the edge of the ranges of 4 points
 * (or at the edge of the area but that is only the case if no point is found)
 * this means it is somewhere on the lines between two pairs of sensors that are exactly their ranges + 2 apart
 * (range + empty + range + 1 because distance includes one of the points)
 */
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

    // 1400 ms for bruteforce :/
    // 0.077ms for optimized function :D
    // answer is 12051287042458
    override fun answer2(input: List<String>): Long {
        val c = findTargetCoordinate() // bruteForce2() //
        return c.x * TUNING_FREQ + c.y
    }

    private fun initializeSensors(input: List<String>) {
        sensors = input.map { line ->
            line.grabInts().let { Sensor(it[0], it[1], it[2], it[3]) }
        }
    }

    private fun getBeaconsOnLine(input: List<String>, lineNumber: Int) = input.map { line ->
        line.grabInts().let { Coordinate(it[2], it[3]) }
    }.distinct().filter { it.y == lineNumber}

    // also in theory, there could be more than 1 point at such an intersection, but once again: there isn't.
    // If it does happen it's simply fixed by
    private fun findTargetCoordinate(): Coordinate{
        val sensorsWithOneSpaceBetween = sensors.map{
            findSensorsWithOneSpace(it, sensors)
        }.filter { it.size >= 2 }.distinct()

        // In theory, more than 2 sensors could be in a group. In that case, they should be paired up.
        // In practice that is not the case, so I am not doing that.
        val lines = sensorsWithOneSpaceBetween.map{ sensors ->
            createLineFromSensors(sensors)
        }
        // This can be much simpler as my input only has two lines, but that is not guaranteed.
        val intersections = getAllIntersectionsForLines(lines)
        return intersections.first { target -> sensors.none { target.x in (it.xRangeAtY(target.y) ?: IntRange.EMPTY) } }
    }

    private fun getAllIntersectionsForLines(lines: Collection<DiagonalLine>): Set<Coordinate>{
        val intersections = HashSet<Coordinate>()
        lines.forEach { line1 ->
            lines.forEach { line2 ->
                line1.getIntersection(line2)?.let{
                    if(it in line1 && it in line2 && it.x in (0..maxRange) && it.y in (0..maxRange))
                        intersections.add(it)
                }
            }
        }
        return intersections
    }

    private fun createLineFromSensors(sensors: List<Sensor>): DiagonalLine {
        val pair = sensors.sortedBy { it.x } // make sure left is always first
        val xStart = pair[0].x
        val xEnd = pair[0].x + pair[0].range + 1
        val yStart = if (pair[1].y >= pair[0].y) pair[0].y + pair[0].range + 1 else pair[0].y - pair[0].range - 1
        val yEnd = pair[0].y

        return DiagonalLine(Coordinate(xStart, yStart), Coordinate(xEnd, yEnd))
    }

    private fun findSensorsWithOneSpace(s: Sensor, sensors: Collection<Sensor>): List<Sensor>{
        val result = mutableListOf(s)
        sensors.forEach {
            if (s.distanceTo(it) - s.range - it.range == 2)
                result.add(it)
        }
        return result.sorted()
    }

    companion object{
        private const val TUNING_FREQ = 4000000L
    }
}