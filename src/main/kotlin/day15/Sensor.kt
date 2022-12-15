package day15

import common.grids.Coordinate
import kotlin.math.absoluteValue

class Sensor(xPos: Int, yPos: Int, closestBeaconX: Int, closestBeaconY: Int): Coordinate(xPos, yPos) {
    val range = distanceTo(Coordinate(closestBeaconX, closestBeaconY))

    fun xRangeAtY(fixedY: Int): IntRange?{
        val rangeAtLine = range - (y - fixedY).absoluteValue
        if (rangeAtLine < 0)  return null
        return (x-rangeAtLine)..(x+rangeAtLine)
    }
}