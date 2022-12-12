package common.grids.pathfinding

/**
 * A Node to be used in a network for Dijkstra-ing my way to another Node.
 */
interface Node<T> where T: Number, T: Comparable<T> {
    /**
     * Get a list of all Nodes that can be reached from this node without passing any other node
     */
    fun getNeighbours(): List<Node<T>>

    /**
     * Get the distance to a neighbour
     */
    fun getDistanceToNeighbour(neighbour: Node<T>): T
}