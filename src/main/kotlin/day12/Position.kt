package day12

import common.grids.Coordinate
import common.grids.CoordinateWithValue
import common.grids.pathfinding.AStar
import common.grids.pathfinding.Node
import kotlin.math.absoluteValue

class Position(
    private val network: Map<Coordinate, Position>,
    x: Int,
    y: Int,
    val height: Char
): CoordinateWithValue<Char>(x, y, height), AStar.AStarNode {
    override fun getNeighbours(): List<AStar.AStarNode> =
        fourNeighbors().mapNotNull{
            network[it]
        }.filter { it.height - this.height <= 1}

    override fun minimumDistanceTo(target: Node<Int>): Int {
        require (target is Position){ "ERROR 1: ${target::class.simpleName} !is Position" }
        return (target.x - x).absoluteValue + (target.y - y).absoluteValue
    }

    override fun getDistanceToNeighbour(neighbour: Node<Int>): Int = 1
}