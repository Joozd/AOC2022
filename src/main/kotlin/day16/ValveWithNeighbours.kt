package day16

import common.grids.pathfinding.AStar
import common.grids.pathfinding.Node

/**
 * [neighbours] s a map of neighbours with their distances.
 */
data class ValveWithNeighbours(val flow: Int, val neighbours: Map<String, Int>): AStar.AStarNode {
    private lateinit var universe: Map<String, ValveWithNeighbours>
    // override operator fun equals(other: Any?) = other is ValveWithNeighbours && other.name == name

    fun insertIntoUniverse(u: Map<String, ValveWithNeighbours>){
        universe = u
    }

    /**
     * Get a list of all Nodes that can be reached from this node without passing any other node
     */
    override fun getNeighbours(): List<AStar.AStarNode> =
        neighbours.keys.map{ universe[it]!! }

    override fun minimumDistanceTo(target: Node<Int>): Int = 0 // doesn't change, no heuristic.


    /**
     * Get the distance to a neighbour
     */
    override fun getDistanceToNeighbour(neighbour: Node<Int>): Int =
        universe.filterValues { it == neighbour }.keys.first().let{
            neighbours[it]!!
        }
}