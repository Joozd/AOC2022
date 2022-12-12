package common.grids.pathfinding


import java.util.PriorityQueue

@Suppress("UNCHECKED_CAST")
class AStar<T: AStar.AStarNode>(
    private val startPoints: Collection<T>,
    private val endPrerequisite: Prerequisite<T>,
    private val heuristic: Heuristic<T>
) {
    constructor(start: T, end: T)
            : this( listOf(start),
                    Prerequisite { node -> end == node },
                    Heuristic { node, costToHere -> costToHere + node.minimumDistanceTo(end) }){
        this.end = end
    }

    constructor(start: T, end: T, heuristic: Heuristic<T>)
            : this( listOf(start),
                    Prerequisite { node -> end == node },
                    heuristic){
                this.end = end
            }

    private var end: T? = null

    private val routeData: RouteData<T> by lazy{
        generateRoute()
    }

    val route by lazy{
        routeData.route(end)
    }

    val distance by lazy{
        routeData.distance(end)
    }

    private fun generateRoute(): RouteData<T> {
        val frontier = PriorityQueue(Comparator<NodeWithPrio> { o1, o2 -> o1!!.totalCost - o2!!.totalCost }) // reverse order as highest prio goes first
        val previous = HashMap<T, T>()
        val totalCostTo = HashMap<T, Int>()
        startPoints.forEach { start ->
            frontier.add(NodeWithPrio(start, 0))
            totalCostTo[start] = 0
        }

        while(frontier.isNotEmpty()){
            val current = frontier.poll().node
            if(endPrerequisite(current as T)) {
                end = current
                break
            }
            val currentCost = totalCostTo[current]!!

            current.getNeighbours().forEach { n ->
                val newCost = currentCost + current.getDistanceToNeighbour(n)
                if((totalCostTo[n] ?: Int.MAX_VALUE) > newCost){
                    totalCostTo[n as T] = newCost
                    frontier.add(NodeWithPrio(n, heuristic(n, newCost)))
                    previous[n] = current
                }
            }
        }
        return RouteData(previous, totalCostTo)
    }

    private class RouteData<T: Node<Int>> (val previousMap: Map<T, T>, val distancesMap: Map<T, Int>){
        fun route(end: T?): List<T>?{
            if(end == null) return emptyList()
            val r = buildList {
                add(end)
                while(previousMap[this.last()] != null){
                    add(previousMap[this.last()] ?: return null)
                }
            }
            return r.reversed()
        }

        fun distance(end: T?): Int? =
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

    fun interface Prerequisite<T: AStarNode>{
        operator fun invoke(node: T): Boolean
    }

    fun interface Heuristic<T: AStarNode>{
        operator fun invoke(node: T, costToHere: Int): Int
    }
}