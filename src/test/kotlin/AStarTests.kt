import common.grids.Coordinate
import common.grids.pathfinding.AStar
import common.grids.pathfinding.Node
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue
import kotlin.test.assertEquals

class AStarTests {
    @Test
    fun test(){
        val depth = 10914
        val target = Coordinate(9,739)
        val cave = Cave(depth, target)

        val start = cave[Coordinate(0, 0)].withTool(RegionWithTool.Tool.TORCH)
        val end = cave[target].withTool(RegionWithTool.Tool.TORCH)
        assertEquals(1013, AStar(start, end).distance)
    }

    private class Cave(private val depth: Int, private val target: Coordinate) {
        private val caveSystem = HashMap<Coordinate, Region>().apply{
            put(target, Region(target, depth % MODULO, this@Cave))
        }
        operator fun set(coordinate: Coordinate, region: Region){
            caveSystem[coordinate] = region
        }

        operator fun get(coordinate: Coordinate): Region =
            caveSystem[coordinate] ?: calculateAndInsertRegion(coordinate)

        // calculates a region, inserts it in [caveSystem] and returns it.
        private fun calculateAndInsertRegion(coordinate: Coordinate): Region =
            Region(coordinate, getErosionLevel(coordinate), this).also{
                caveSystem[it] = it
            }

        private fun getErosionLevel(coordinate: Coordinate): Int{
            return (when{
                coordinate.x == 0 -> coordinate.y * Y_MULTIPLIER                    // this includes 0 for 0,0
                coordinate.y == 0 -> coordinate.x * X_MULTIPLIER
                else -> {
                    val n = this[coordinate.north()]
                    val w = this[coordinate.west()]
                    n.erosionLevel * w.erosionLevel
                }
            } + depth) % MODULO
        }

        //only shows cave up to target + 5
        override fun toString(): String =
            (0..target.x + 5).joinToString("\n") { y ->
                (0..target.y + 5).joinToString("") { x ->
                    this[Coordinate(x, y)].terrain()
                }
            }

        companion object{
            private const val X_MULTIPLIER = 16807 // coordinates at y=0 get level it.x * X_MULTIPLIER
            private const val Y_MULTIPLIER = 48271 // coordinates at x=0 get level it.y * Y_MULTIPLIER
            private const val MODULO = 20183
        }
    }

    private open class Region(coordinate: Coordinate, val erosionLevel: Int, protected val caveSystem: Cave):
        Coordinate(coordinate.x, coordinate.y)
    {
        val dangerLevel get() = erosionLevel % 3

        fun withTool(tool: RegionWithTool.Tool) = RegionWithTool(Coordinate(x,y), erosionLevel, caveSystem, tool)

        fun terrain() = when(dangerLevel){
            0 -> "."
            1 -> "="
            2 -> "|"
            else -> error("ERROR")
        }
    }

    private class RegionWithTool(coordinate: Coordinate, erosionLevel: Int, caveSystem: Cave, val tool: Tool):
        Region(coordinate, erosionLevel, caveSystem), AStar.AStarNode
    {
        override fun equals(other: Any?): Boolean = when {
            other == null || other !is Coordinate -> false
            other !is RegionWithTool -> other.x == x && other.y == y // other is Region but does not have a tool so we only compare x and y
            else -> other.x == x && other.y == y && other.tool == tool
        }

        override fun hashCode(): Int =
            super.hashCode() + tool.hashCode()

        /**
         * Get a list of all Nodes that can be reached from this node without passing any other node.
         * In this case: those that can be reached with this tool, or this with another tool.
         */
        override fun getNeighbours(): List<AStar.AStarNode> =
            fourNeighbors()
                .filter { it.x >= 0 && it.y >= 0}
                .map {
                    caveSystem[it]
                }.filter {
                    isAccessible(it)
                }.map { it.withTool(tool)} +
                    thisWithOtherTools()

        override fun minimumDistanceTo(target: Node<Int>): Int {
            require(target is Region)
            return (target.x-x).absoluteValue + (target.y - y).absoluteValue
        }

        /**
         * Get the distance to a neighbour
         */
        override fun getDistanceToNeighbour(neighbour: Node<Int>): Int =
            if (hasSameCoordinates(neighbour as RegionWithTool)) 7 else 1

        private fun isAccessible(neighbour: Region): Boolean = when(neighbour.dangerLevel){
            DangerLevel.WET -> tool != Tool.TORCH
            DangerLevel.NARROW -> tool != Tool.CLIMBING_GEAR
            DangerLevel.ROCKY -> tool != Tool.NEITHER
            else -> error("Error 17a")
        }

        private fun thisWithOtherTools() = Tool.values().filter{ it != tool}.map{
            this.withTool(it)
        }

        private fun hasSameCoordinates(neighbour: RegionWithTool) =
            neighbour.x == x && neighbour.y == y

        enum class Tool{
            TORCH,
            CLIMBING_GEAR,
            NEITHER
        }
    }

    object DangerLevel {
        const val ROCKY = 0
        const val WET = 1
        const val NARROW = 2
    }
}


/**
 * Below is pretty much my 1018 day 22 implementation. If ti works for that I trust it.
 */

