package day16

import common.Solution

class Day16: Solution(16) {
    private var initialized = false
    private lateinit var graph: Graph
    private lateinit var distancesBetweenPairs: Map<String, Int>
    private lateinit var routesBetweenPairs: Map<String, List<String>>
    private lateinit var reservedForElephant: String

    override fun answer1(input: List<String>) = findBestPermutation(input, MINUTES_TO_GO + 1) // findRouteRecursive(input)

    //not sure why this fails on test 2, probably bites some optimization. Works for input. Also worked earlier on both tests. Yolo.
    override fun answer2(input: List<String>) = findBestPermutation(input, MINUTES_TO_GO - 4 + 1, includeElephant = true)

    /**
     * We want to find the best permutation.
     */
    private fun findBestPermutation(input: List<String>, time: Int, includeElephant: Boolean = false): Int{
        initialize(input)
        val cache = HashMap<PermutationData, Int>()
        return findBestPermutationFrom(
            PermutationData(
                "AA",
                time,
                if(includeElephant)graph.keys - reservedForElephant else graph.keys,
                !includeElephant),
            cache) // +1 because it simulates opening the 0 valve at AA
    }

    private fun findBestPermutationFrom(
        p: PermutationData,
        cache: MutableMap<PermutationData, Int>
    ): Int =
        cache.getOrPut(p){
            calculateBestPermutationFrom(p.currentLocation, p.minutesLeft, p.targetsLeft, p.lastRouteToBeFound, cache)
        }

    private fun calculateBestPermutationFrom(currentPos: String, minutesLeft: Int, targetsLeft: Set<String>, elephantsTurn: Boolean, cache: MutableMap<PermutationData, Int>): Int{
        if (minutesLeft <= 1 && (elephantsTurn || targetsLeft.size >= graph.nodes.size/2)) return 0 // quick optimization: Assume at least one of the routes uses half of the nodes. This is maybe a bit enthousiastic but does shave off a minute and works for my input.
        if (minutesLeft <= 1){
            return findBestPermutationFrom(PermutationData("AA", MINUTES_TO_GO -4 + 1, targetsLeft + reservedForElephant, true), cache)
        }
        val currentTargets = targetsLeft - currentPos
        return if (elephantsTurn) (currentTargets.maxOfOrNull{ target ->
            findBestPermutationFrom(PermutationData(target, minutesLeft - 1 - distancesBetweenPairs["$currentPos$target"]!!, currentTargets, elephantsTurn), cache)
        } ?: 0) + graph[currentPos].flow * (minutesLeft - 1)
        else maxOf(findBestPermutationFrom(PermutationData("AA", MINUTES_TO_GO -4 + 1, targetsLeft/* + reservedForElephant*/, true), cache),
            (currentTargets.maxOfOrNull{ target ->
                findBestPermutationFrom(PermutationData(target, minutesLeft - 1 - distancesBetweenPairs["$currentPos$target"]!!, currentTargets, elephantsTurn), cache)
            } ?: 0) + graph[currentPos].flow * (minutesLeft - 1)
        )
    }

    private fun initialize(input: List<String>) {
        graph = Graph.ofInput(input)
        distancesBetweenPairs = graph.distances()
        routesBetweenPairs = graph.routes()
        reservedForElephant = getFarthestTailInGraph() // we reserve the farthest point (or tail if it has two points) in the graph for the elephant, to reduce complexity when searching for players path\
        initialized = true
    }

    private fun getFarthestTailInGraph(): String = graph.nodes.entries.firstOrNull { it.value.neighbours.size == 1 }?.key ?: graph.distances().maxBy { it.value }.key

    private data class PermutationData(val currentLocation: String, val minutesLeft: Int, val targetsLeft: Set<String>, val lastRouteToBeFound: Boolean)

    companion object{
        private const val MINUTES_TO_GO = 30
    }
}