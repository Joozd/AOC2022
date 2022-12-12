package day12

import common.Solution
import common.grids.Coordinate
import common.grids.CoordinateWithValue
import common.grids.imaging.GifSequenceBuilder
import common.grids.pathfinding.AStar

class Day12: Solution(12) {
    private val mountain = HashMap<Coordinate, Position>()
    private lateinit var startPos: Coordinate
    private lateinit var endPos: Coordinate

    override fun answer1(input: List<String>): Int {
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                val height = when (c) {
                    'S' -> 'a'.also { startPos = Coordinate(x, y) }
                    'E' -> 'z'.also { endPos = Coordinate(x, y) }
                    else -> c
                }
                Position(mountain, x, y, height).let {
                    mountain[it] = it
                }
            }
        }
        val route = AStar(mountain[startPos]!!, mountain[endPos]!!)
        buildGif(mountain.values, route.route!!, "c:\\temp\\2022-12-1.gif")
        return route.distance!!
    }

    override fun answer2(input: List<String>): Int {
        val shortestRouteStartPos = mountain.values.filter {
            it.height == 'a'
        }.minBy {
            AStar(it, mountain[endPos]!!).distance ?: Int.MAX_VALUE
        }
        val route = AStar(mountain[shortestRouteStartPos]!!, mountain[endPos]!!)
        buildGif(mountain.values, route.route!!, "c:\\temp\\2022-12-2.gif")
        return route.distance!!

    }

}

private fun buildGif(mountain: Collection<Position>, route: Collection<Position>, fileName: String){
    val gifBuilder = GifSequenceBuilder(100, true)
    gifBuilder.addCoordinates(mountain, scale = 5){
        if (it.value == 'X')
            0xFF0000
        else
            greenToBrown(getHeightRatio(it.value))
    }

    for (fraction in 0..route.size step maxOf(1, route.size / 50)){
        val grid = HashSet(route.take(fraction).map{
            CoordinateWithValue(it.x, it.y, 'X')
        })
        grid.addAll(mountain) // like this so route doesn't get overwritten
        gifBuilder.addCoordinates(grid, scale = 5){
            if (it.value == 'X')
                0xFF0000
            else
                greenToBrown(getHeightRatio(it.value))
        }
    }

    //add full route
    val grid = HashSet(route.map{
        CoordinateWithValue(it.x, it.y, 'X')
    })
    grid.addAll(mountain) // like this so route doesn't get overwritten
    gifBuilder.addCoordinates(grid, scale = 5){
        if (it.value == 'X')
            0xFF0000
        else
            greenToBrown(getHeightRatio(it.value))
    }

    gifBuilder.writeGif(fileName)


}

private fun getHeightRatio(height: Char): Double =
    (height - 'a').toDouble() / 25


private const val BROWN = 0x964b00
private const val GREEN = 0x378805

private fun greenToBrown(ratio: Double): Int{
    val rBrown = BROWN.shr(16)
    val gBrown = BROWN.shr(8) and 255
    val bBrown = BROWN and 255

    val rGreen = GREEN.shr(16)
    val gGreen = GREEN.shr(8) and 255
    val bGreen = GREEN and 255

    val rDif = rGreen - rBrown
    val gDif = gGreen - gBrown
    val bDif = bGreen - bBrown

    val r = (rGreen + rDif * ratio).toInt().shl(16)
    val g = (gGreen + gDif * ratio).toInt().shl(8)
    val b = (bGreen + bDif * ratio).toInt()

    return r + g + b
}