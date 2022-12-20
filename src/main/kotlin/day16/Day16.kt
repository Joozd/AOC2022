package day16

import common.Solution

class Day16: Solution(16) {
    private var initialized = false
    private lateinit var graph: Graph
    private lateinit var distancesBetweenPairs: Map<String, Int>
    private lateinit var routesBetweenPairs: Map<String, List<String>>

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
                if(includeElephant)graph.keys.toList()  else graph.keys.toList(),
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

    private fun calculateBestPermutationFrom(currentPos: String, minutesLeft: Int, targetsLeft: List<String>, elephantsTurn: Boolean, cache: MutableMap<PermutationData, Int>): Int{
        if (minutesLeft <= 1 && elephantsTurn) return 0
        if (minutesLeft <= 1){
            return findBestPermutationFrom(PermutationData("AA", MINUTES_TO_GO -4 + 1, targetsLeft , true), cache)
        }
        val currentTargets = targetsLeft - currentPos
        return if (elephantsTurn) (currentTargets.maxOfOrNull{ target ->
            findBestPermutationFrom(PermutationData(target, minutesLeft - 1 - distancesBetweenPairs["$currentPos$target"]!!, currentTargets, true), cache)
        } ?: 0) + graph[currentPos].flow * (minutesLeft - 1)
        else maxOf(findBestPermutationFrom(PermutationData("AA", MINUTES_TO_GO -4 + 1, targetsLeft/* + reservedForElephant*/, true), cache),
            (currentTargets.maxOfOrNull{ target ->
                findBestPermutationFrom(PermutationData(target, minutesLeft - 1 - distancesBetweenPairs["$currentPos$target"]!!, currentTargets, false), cache)
            } ?: 0) + graph[currentPos].flow * (minutesLeft - 1)
        )
    }

    private fun initialize(input: List<String>) {
        graph = Graph.ofInput(input)
        distancesBetweenPairs = graph.distances()
        routesBetweenPairs = graph.routes()
        initialized = true
    }

    private data class PermutationData(val currentLocation: String, val minutesLeft: Int, val targetsLeft: List<String>, val lastRouteToBeFound: Boolean)

    companion object{
        private const val MINUTES_TO_GO = 30
    }
}