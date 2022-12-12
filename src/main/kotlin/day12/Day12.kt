package day12

import common.Solution
import common.grids.Coordinate
import common.grids.pathfinding.AStar

class Day12: Solution(12) {
    private val mountain = HashMap<Coordinate, Position>()
    private lateinit var startPos: Coordinate
    private lateinit var endPos: Coordinate

    // 4.6 ms
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
        //buildGif(mountain.values, route.route!!, "c:\\temp\\2022-12-1.gif")
        return route.distance!!
    }

    //277 ms
    override fun answer2(input: List<String>): Int {
        val shortestRouteStartPos = mountain.values.filter {
            it.height == 'a'
        }.minBy {
            AStar(it, mountain[endPos]!!).distance ?: Int.MAX_VALUE
        }
        val route = AStar(mountain[shortestRouteStartPos]!!, mountain[endPos]!!)
        //buildGif(mountain.values, route.route!!, "c:\\temp\\2022-12-2.gif")
        return route.distance!!

    }
}
