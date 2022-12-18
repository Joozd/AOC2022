package day16

import common.extensions.grabFirstIntOrNull
import common.extensions.words
import common.grids.pathfinding.AStar

data class Graph(val nodes: Map<String, ValveWithNeighbours>) {
    val keys = nodes.keys
    val reverseNodes = nodes.entries.associate { it.value to it.key }

    operator fun get(valve: String): ValveWithNeighbours = nodes[valve] ?: error ("ERROR: Trying to get $valve from $nodes")

    // e.g. "AAHX" = 2, "AAAW" = 8.
    // Both ways, so AWAA also works.
    fun distances(): Map<String, Int> = nodes.keys.map{ first ->
        nodes.keys.filter { it != first }.map{ second ->
            "$first$second" to getDistance(first, second)
        }
    }.flatten().toMap()

    fun routes(): Map<String, List<String>> = nodes.keys.map{ first ->
        nodes.keys.filter { it != first }.map{ second ->
            "$first$second" to getRoute(first, second)
        }
    }.flatten().toMap()


    // using A* because thats what I have. Dijkstra would probably be a little better.
    private fun getDistance(first: String, second: String): Int{
        val start = this[first]
        val end = this[second]
        return AStar(start, end).distance?: error ("Cannot get distance from $first to $second")
    }

    // using A* because thats what I have. Dijkstra would probably be a little better.
    private fun getRoute(first: String, second: String): List<String>{
        val start = this[first]
        val end = this[second]
        return AStar(start, end).route?.map{
            reverseNodes[it]!!
        } ?: error ("Cannot get route from $first to $second")
    }


    companion object{
        fun ofInput(input: List<String>): Graph {
            val valvesMap = buildValvesMap(input)
            val relevantValves =
                valvesMap.keys.filter {
                    valvesMap[it]!!.flowRate != 0 || valvesMap[it]!!.exits.size > 2 || it == "AA"
                }.associateWith { valve ->
                    val exits = valvesMap[valve]!!.exits.mapNotNull {
                        var distance = 1
                        var currentExit: String? = it
                        var prev: String? = valve
                        var exitValve = valvesMap[currentExit]!!
                        while (exitValve.flowRate == 0 && exitValve.exits.size <= 2 && currentExit != "AA") {
                            val nextExit = exitValve.exits.firstOrNull { x -> x != prev }
                            prev = currentExit
                            currentExit = nextExit
                            exitValve = valvesMap[currentExit ?: break]!!
                            distance++
                        }
                        currentExit?.let { e ->
                            e to distance
                        }
                    }.toMap()
                    ValveWithNeighbours(valvesMap[valve]!!.flowRate, exits)
                }
            relevantValves.values.forEach {
                it.insertIntoUniverse(relevantValves)
            }

            return Graph(relevantValves)
        }

        private fun buildValvesMap(input: List<String>): Map<String, Valve> = HashMap<String, Valve>().apply{
            input.forEach{ line ->
                val words = line.words()
                val exits = words.filter { it.endsWith(",")}.map { it.dropLast(1)} + words.last()
                this[words[1]] = Valve(line.grabFirstIntOrNull()!!, exits)
            }
        }
    }
}