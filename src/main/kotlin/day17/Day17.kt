package day17

import common.Solution
import common.extensions.contains
import common.grids.Coordinate
import common.utils.makeCoordinatesWithValue
import java.util.*

@Suppress("SameParameterValue")
class Day17: Solution(17) {

    override fun answer1(input: List<String>) = dropShapes(input.first(), 2022).minOf{it.y} * -1

    // 1581449275318too low
    // 1581449275318
    override fun answer2(input: List<String>): Long {
        val periodData = findPeriod(input.first())
        val gainPerPeriod = periodData.gains.sum().toLong()
        val wholePeriodsInBigNumber = (LARGE_NUMBER - periodData.start) / periodData.periodLength
        val heightFromWholePeriodsPlusStart = wholePeriodsInBigNumber * gainPerPeriod + periodData.gainBeforeStart
        val remaining = (LARGE_NUMBER - periodData.start) % periodData.periodLength
        val gainsInRemaining = periodData.gains.take(remaining.toInt()).sum()
        return (heightFromWholePeriodsPlusStart + gainsInRemaining) * -1
    }


    private fun dropShapes(instructions: String, amount: Int): Set<Coordinate> = buildSet{
        addAll(makeCoordinatesWithValue(listOf("1234567"))) // add bottom to chamber. }
        var currentTop = -1 // saves me a one-off error in counting how high it gets
        var currentWind = 0
        val rocks = ShapesGenerator()

        repeat(amount){
            var currentRock = rocks.next(currentTop - 3)

            // move rocks until a downward movement would currentRock to overlap a rock already in chamber
            while(true){
                var nextRock = if (instructions[currentWind++ % instructions.length] == '<')
                    currentRock.map { it.west() }
                else
                    currentRock.map { it.east() }

                //discard moved rock if out of bounds
                if(nextRock.none{it.x < 0 || it.x >= WIDTH || it in this})
                    currentRock = nextRock

                nextRock =  currentRock.map { it.south() }
                //if moving down overlaps, rock has come to rest.
                if(nextRock.any { it in this }) break
                currentRock = nextRock
            }
            addAll(currentRock.map { Coordinate(it.x, it.y) })
            currentTop = minOf(currentTop, currentRock.minOf { it.y } - 1)
        }
    }

    private fun findPeriod(instructions: String): RepeatData {
        val set = HashSet<Coordinate>()
        set.addAll(makeCoordinatesWithValue(listOf("1234567"))) // add bottom to chamber. }
        var currentTop = -1 // saves me a one-off error in counting how high it gets
        var currentWindIndex = 0
        val rocks = ShapesGenerator()
        val heightDifferences = mutableListOf<Int>()
        val indexOfStart: Int
        val period: Int
        val gainsInPeriod: List<Int>


        while(true){ // keep going until Int overflows
            var currentRock = rocks.next(currentTop - 3)

            // move rocks until a downward movement would currentRock to overlap a rock already in chamber
            while(true){
                var nextRock = if (instructions[currentWindIndex++ % instructions.length] == '<')
                    currentRock.map { it.west() }
                else
                    currentRock.map { it.east() }

                //discard moved rock if out of bounds
                if(nextRock.none{it.x < 0 || it.x >= WIDTH || it in set})
                    currentRock = nextRock

                nextRock =  currentRock.map { it.south() }
                //if moving down overlaps, rock has come to rest.
                if(nextRock.any { it in set }) break
                currentRock = nextRock
            }
            set.addAll(currentRock.map { Coordinate(it.x, it.y) }) //
            val nextTop = minOf(currentTop, currentRock.minOf { it.y } - 1)
            heightDifferences.add(nextTop - currentTop)
            if(heightDifferences.takeLast(MINIMUM_REPEAT) in heightDifferences.dropLast(MINIMUM_REPEAT)) {
                indexOfStart = Collections.indexOfSubList(heightDifferences.dropLast(MINIMUM_REPEAT), heightDifferences.takeLast(MINIMUM_REPEAT))
                val indexOfEnd = heightDifferences.size - MINIMUM_REPEAT
                period = indexOfEnd - indexOfStart
                gainsInPeriod = heightDifferences.drop(indexOfStart).dropLast(MINIMUM_REPEAT)
                println("repeat started from $indexOfStart, has length ${indexOfEnd - indexOfStart} and a gain of ${gainsInPeriod.sum()}")
                break
            }
            currentTop = nextTop
            if (currentTop % 100 == 0)
                set.removeAll(set.filter{it.y > currentTop + 100}.toSet())

        }
        return RepeatData(gainsInPeriod, indexOfStart, period, heightDifferences.take(indexOfStart + 1).sum())
    }


    private class RepeatData(val gains: List<Int>, val start: Int, val periodLength: Int, val gainBeforeStart: Int)

    companion object{
        private const val WIDTH: Int = 7
        private const val MINIMUM_REPEAT = 100
        private const val LARGE_NUMBER = 1000000000000
    }
}