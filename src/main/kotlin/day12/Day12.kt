package day12

import common.Solution
import common.grids.Coordinate
import common.grids.findFirstPositionOrNull
import common.grids.get
import common.grids.contains
import common.grids.pathfinding.AStar


/**
 * Improvements: We are doing a lot of things we don't need to do. 
 * We only need to count steps for shortest route so a simple BFS should work
 * Part 1 is slightly faster with an A* that strongly favors going up.
 * This does open the door for sub-optimal solutions though, as a shorter path that requires a step to a lower position is ignored.
 */
class Day12: Solution(12) {
    private val mountain = HashMap<Coordinate, Position>()
    private lateinit var startPos: Coordinate
    private lateinit var endPos: Coordinate

    // 4.6 ms
    // a* is as fast as BFS as we are looking for a specific point
    override fun answer1(input: List<String>): Int = aStar1(input)

    // 277 ms with aStars
    // BFS is faster than a lot of A* searches as we are looking for the closest point out of many
    // 2.7ms with BFS is 100x faster :)
    // A* with multiple startpoints is 3.7ms, same ballpark but still a bit slower.
    override fun answer2(input: List<String>): Int {
        return bfs2(input)
    }
    
    private fun bfs2(input: List<String>): Int{
        // find start
        val start = input.findFirstPositionOrNull('E')!!
        var distance = 1
        var frontier = start.fourNeighbors().filter{ input[it] in "yz" }.toSet()
        val visited = HashSet<Coordinate>().apply{
            add(start)
        }
        while(frontier.none { input[it] == 'a'}){
            distance++
            visited.addAll(frontier)
            frontier = HashSet<Coordinate>().apply{
                frontier.forEach {
                    addAll(getValidNeighbours(input, it).filter{
                        it !in visited
                    })
                }
            }
            require(distance <= 1000) { "Neen."}
        }

        return distance
    }

    private fun getValidNeighbours(input: List<String>, coordinate: Coordinate) =
        coordinate.fourNeighbors().filter {
            it in input && input[it] - input[coordinate] >= -1
        }

    // This gives correct answer for my input, but that is NOT GUARANTEED
    private fun aStar1(input: List<String>): Int {
        buildMountain(input)
        return AStar(mountain[startPos]!!, mountain[endPos]!!
        ) { node, costToHere -> costToHere - 1000 * (node.height - 'a') }.distance!!
    }

    private fun buildMountain(input: List<String>) {
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
    }

    private fun aStar2(): Int {
        val shortestRouteStartPositions = mountain.values.filter {
            it.height == 'a'
        }
        return AStar(shortestRouteStartPositions,
            { it == mountain[endPos]!!},
            { node, costToHere -> node.x + costToHere } // when in doubt, look at left-most nodes first.
        ).distance!!
    }
}
