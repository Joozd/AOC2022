package day19

import common.Solution
import common.extensions.grabInts

class Day19: Solution(19) {
    override fun answer1(input: List<String>) = input.map{ it.grabInts() }.sumOf {
        it[0] * maxAmountOfGeodes(it, 24)
    }

    override fun answer2(input: List<String>) = input.take(3).map{ it.grabInts() }.fold(1){ acc , recipe ->
        acc * maxAmountOfGeodes(recipe, 32)
    }

    fun maxAmountOfGeodes(recipe: List<Int>, minutes: Int): Int{
        val startState = State(1,0,0,0,0,0,0,minutes)
        return getBestResult(startState, recipe).also{
            println("best result for recipe ${recipe[0]} = $it")
            cache.clear()
        }
    }


    private data class State(
        val oreBots: Int,
        val clayBots: Int,
        val obsidianBots: Int,
        val geodeBots: Int,
        val ore: Int,
        val clay: Int,
        val obsidian: Int,
        val timeLeft: Int,
    ){
        fun tick() = State(oreBots, clayBots, obsidianBots, geodeBots, ore + oreBots, clay + clayBots, obsidian + obsidianBots, timeLeft - 1)
    }

    private val cache = HashMap<State, Int>()

    private fun getBestResult(state: State, recipe: List<Int>) =
        cache.getOrPut(state){
            calculateBestResult(state, recipe)
        }

    private fun calculateBestResult(state: State, recipe: List<Int>): Int {
        if (state.timeLeft == 1) return state.geodeBots // end condition
        return (findOptions(state, recipe).maxOfOrNull{ newState -> getBestResult(newState, recipe)}?: 0) + state.geodeBots
    }

    private fun findOptions(state: State, recipe: List<Int>): List<State> {
        val doNothing = state.tick()
        val options = mutableListOf<State>() // doing nothing is always an option

        if (state.ore < maxOf(recipe[INDEX_OREBOT_COST_ORE], recipe[INDEX_CLAYBOT_COST_ORE], recipe[INDEX_OBSIDIANBOT_COST_ORE], recipe[INDEX_GEODEBOT_COST_ORE]))
            options.add(doNothing)

        if (state.ore >= recipe[INDEX_OREBOT_COST_ORE]){
            //if(state.clayBots / state.oreBots + 1 > recipe[INDEX_OBSIDIANBOT_COST_CLAY] / recipe[INDEX_OBSIDIANBOT_COST_ORE])
            options.add(doNothing.copy(oreBots = doNothing.oreBots + 1, ore = doNothing.ore - recipe[INDEX_OREBOT_COST_ORE]))
        }

        if (state.ore >= recipe[INDEX_CLAYBOT_COST_ORE])
            options.add(doNothing.copy(clayBots = doNothing.clayBots + 1, ore = doNothing.ore - recipe[INDEX_CLAYBOT_COST_ORE]))


        if (state.ore >= recipe[INDEX_OBSIDIANBOT_COST_ORE] && state.clay >= recipe[INDEX_OBSIDIANBOT_COST_CLAY]){
            if (state.timeLeft < 15) options.clear() // force build obsidian if able, but only in the last 15 turns. This is a bit "wet finger work" but it works for my input. If it doesn't I'd have to do something smarter here but turns out I don't
            options.add(doNothing.copy(obsidianBots = doNothing.obsidianBots + 1, ore = doNothing.ore - recipe[INDEX_OBSIDIANBOT_COST_ORE], clay = doNothing.clay - recipe[INDEX_OBSIDIANBOT_COST_CLAY]))
        }

        if (state.ore >= recipe[INDEX_GEODEBOT_COST_ORE] && state.obsidian >= recipe[INDEX_GEODEBOT_COST_OBSIDIAN]){
            options.clear() // force build geodebots when able
            options.add(doNothing.copy(geodeBots = doNothing.geodeBots + 1, ore = doNothing.ore - recipe[INDEX_GEODEBOT_COST_ORE], obsidian = doNothing.obsidian - recipe[INDEX_GEODEBOT_COST_OBSIDIAN]))
            // .also{println("Can build geodebot from $state")}
        }

        return options
    }

    companion object{
        private const val INDEX_OREBOT_COST_ORE = 1
        private const val INDEX_CLAYBOT_COST_ORE = 2
        private const val INDEX_OBSIDIANBOT_COST_ORE = 3
        private const val INDEX_OBSIDIANBOT_COST_CLAY = 4
        private const val INDEX_GEODEBOT_COST_ORE = 5
        private const val INDEX_GEODEBOT_COST_OBSIDIAN = 6
    }
}