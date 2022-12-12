package common.grids.pathfinding


import java.util.PriorityQueue

@Suppress("UNCHECKED_CAST")
class AStar<T: AStar.AStarNode>(private val start: T, private val end: T) {
    private val routeData: RouteData<T> by lazy{
        generateRoute()
    }

    val route by lazy{
        routeData.route(start, end)
    }

    val distance by lazy{
        routeData.distance(end)
    }

    private fun generateRoute(): RouteData<T> {
        val frontier = PriorityQueue(Comparator<NodeWithPrio> { o1, o2 -> o1!!.totalCost - o2!!.totalCost }) // reverse order as highest prio goes first
        frontier.add(NodeWithPrio(start, 0))
        val previous = HashMap<T, T>()
        val totalCostTo = HashMap<T, Int>().apply{
            this[start] = 0
        }

        while(frontier.isNotEmpty()){
            val current = frontier.poll().node
            if(current == end) break
            val currentCost = totalCostTo[current]!!

            current.getNeighbours().forEach { n ->
                val newCost = currentCost + current.getDistanceToNeighbour(n)
                if((totalCostTo[n] ?: Int.MAX_VALUE) > newCost){
                    totalCostTo[n as T] = newCost
                    frontier.add(NodeWithPrio(n, newCost + n.minimumDistanceTo(end)))
                    previous[n] = current as T
                }
            }
        }
        return RouteData(previous, totalCostTo)
    }

    private class RouteData<T: Node<Int>> (val previousMap: Map<T, T>, val distancesMap: Map<T, Int>){
        fun route(start: T, end: T): List<T>?{
            val r = buildList {
                add(end)
                while(previousMap[this.last()] != start){
                    add(previousMap[this.last()] ?: return null)
                }
            }
            return r.reversed()
        }

        fun distance(end: T): Int? =
            distancesMap[end]
    }

    private class NodeWithPrio(val node: AStarNode, val totalCost: Int){
        override fun equals(other: Any?): Boolean = other is NodeWithPrio && other.node == node
        override fun hashCode(): Int = node.hashCode()
    }

    interface AStarNode: Node<Int>{
        override fun getNeighbours(): List<AStarNode>

        fun minimumDistanceTo(target: Node<Int>): Int
    }
}