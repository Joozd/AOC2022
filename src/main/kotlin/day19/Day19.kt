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

    private fun maxAmountOfGeodes(recipe: List<Int>, minutes: Int): Int{
        val startState = State(1,0,0,0,0,0,0,minutes)
        return getBestResult(startState, recipe)
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

    private fun getBestResult(state: State, recipe: List<Int>): Int {
        if (state.timeLeft == 1) return state.geodeBots // end condition
        return (findOptions(state, recipe).maxOfOrNull{ newState -> getBestResult(newState, recipe)}?: 0) + state.geodeBots
    }

    private fun findOptions(state: State, recipe: List<Int>): List<State> {
        val tick = state.tick()
        val maxOreNeeded = maxOf(recipe[INDEX_OREBOT_COST_ORE], recipe[INDEX_CLAYBOT_COST_ORE], recipe[INDEX_OBSIDIANBOT_COST_ORE], recipe[INDEX_GEODEBOT_COST_ORE])
        val maxClayNeeded = recipe[INDEX_OBSIDIANBOT_COST_CLAY]
        val options = mutableListOf<State>()

        if (state.ore < maxOreNeeded)
            options.add(tick)

        if (state.ore >= recipe[INDEX_OREBOT_COST_ORE] && state.oreBots <= maxOreNeeded){
            options.add(tick.copy(oreBots = tick.oreBots + 1, ore = tick.ore - recipe[INDEX_OREBOT_COST_ORE]))
        }

        if (state.ore >= recipe[INDEX_CLAYBOT_COST_ORE] && state.clay <= maxClayNeeded)
            options.add(tick.copy(clayBots = tick.clayBots + 1, ore = tick.ore - recipe[INDEX_CLAYBOT_COST_ORE]))


        if (state.ore >= recipe[INDEX_OBSIDIANBOT_COST_ORE] && state.clay >= recipe[INDEX_OBSIDIANBOT_COST_CLAY]){
            options.add(tick.copy(obsidianBots = tick.obsidianBots + 1, ore = tick.ore - recipe[INDEX_OBSIDIANBOT_COST_ORE], clay = tick.clay - recipe[INDEX_OBSIDIANBOT_COST_CLAY]))
        }

        if (state.ore >= recipe[INDEX_GEODEBOT_COST_ORE] && state.obsidian >= recipe[INDEX_GEODEBOT_COST_OBSIDIAN]){
            options.clear() // force build geodebots when able
            options.add(tick.copy(geodeBots = tick.geodeBots + 1, ore = tick.ore - recipe[INDEX_GEODEBOT_COST_ORE], obsidian = tick.obsidian - recipe[INDEX_GEODEBOT_COST_OBSIDIAN]))
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