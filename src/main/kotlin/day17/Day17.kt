package day17

import common.Solution
import common.grids.Coordinate
import common.grids.CoordinateWithValue
import common.utils.makeCoordinatesWithValue

@Suppress("SameParameterValue")
class Day17: Solution(17) {

    override fun answer1(input: List<String>) = dropShapes(input.first(), 2022).minOf{it.y} * -1

    override fun answer2(input: List<String>) = findPeriod(input.first())


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

                if(nextRock.none{it.x < 0 || it.x >= WIDTH || it in this})
                    currentRock = nextRock

                nextRock =  currentRock.map { it.south() }
                if(nextRock.any { it in this }) break
                currentRock = nextRock
            }
            addAll(currentRock.map { CoordinateWithValue(it.x, it.y, '#') })
            currentTop = minOf(currentTop, currentRock.minOf { it.y } - 1)
        }
    }

    private fun findPeriod(instructions: String): Int{
        buildSet<Coordinate>{
            addAll(makeCoordinatesWithValue(listOf("1234567"))) // add bottom to chamber. }
            var currentTop = -1 // saves me a one-off error in counting how high it gets
            var currentWind = 0
            val rocks = ShapesGenerator()
            val blocksDroppedAtEndOfWind = HashMap<Pair<Int, Int>, Int>() // block number to block position (left most pixel) to currentHeight

            while(true){
                var currentRock = rocks.next(currentTop - 3)

                // move rocks until a downward movement would currentRock to overlap a rock already in chamber
                while(true){
                    var nextRock = if (instructions[currentWind++ % instructions.length] == '<')
                        currentRock.map { it.west() }
                    else
                        currentRock.map { it.east() }

                    if(nextRock.none{it.x < 0 || it.x >= WIDTH || it in this})
                        currentRock = nextRock

                    nextRock =  currentRock.map { it.south() }
                    if(nextRock.any { it in this }) break
                    currentRock = nextRock
                }
                addAll(currentRock.map { CoordinateWithValue(it.x, it.y, '#') })
                if (currentTop % 100 == 0)
                    removeAll(filter {it.y > (currentTop + 100) })

                if (currentWind % instructions.length == 0) {
                    val blockData = rocks.lastShapeGenerated to currentRock.minOf { it.x }
                    println("Dropped $blockData at $currentTop")
                    blocksDroppedAtEndOfWind[blockData]?.let{
                        println("dropped block $blockData at $it and at $currentTop")
                        return -1
                    }
                    blocksDroppedAtEndOfWind[blockData] = currentTop
                }
                currentTop = minOf(currentTop, currentRock.minOf { it.y } - 1)
            }
        }
    }

    companion object{
        private const val WIDTH: Int = 7
        private const val LARGE_NUMBER = 1000000000000
    }
}