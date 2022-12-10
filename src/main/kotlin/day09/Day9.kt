package day09

import common.Solution
import common.extensions.grabFirstIntOrNull
import common.grids.Coordinate
import kotlin.math.absoluteValue

/**
 * I am guessing the creation of a lot of objects slows things down.
 * Tracking x and y as ints is about 20% faster for answer 1 (didn't look for 2),
 * presumably because only primitives get overwritten instead of objects being made and garbage collected later.
 * However, knowing me, I would make a LOT of mistakes and bugs if not using Coordinate objects,
 * so I would normally take my 20% penalty unless this is really critical.
 * Interestingly: An Array of IntArrays is about the same speed as two (immutable) Coordinates getting replaced,
 * so perhaps the JIT compiler optimizes away the garbage collection and instead overwrites the old Coordinates with new?
 * Either that, or the 2d lookups take more time than I thought.
 */
class Day9: Solution(9) {
    // 0.62ms with objects
    // 0.49 with only primitives
    // 0.58 with method of answer 2
    override fun answer1(input: List<String>) = countTailPositionsWithPrimitives(input)

    // 0.96 ms
    // 0.73 ms when using an Array of IntArrays instead of an Array of coordinates.
    @Suppress("SameParameterValue")
    override fun answer2(input: List<String>) = countTailPositionsWithPrimitivesOnly(input,10)

    private fun countTailPositionsWithPrimitives(input: List<String>): Int{
        var xHead = 0
        var yHead = 0
        var xTail = 0
        var yTail = 0
        val visitedPositions = HashSet<Pair<Int, Int>>().apply{
            add(0 to 0) // x to y
        }
        input.forEach { movement ->
            repeat(movement.grabFirstIntOrNull()!!){
                when(movement[0]){
                    'U' -> yHead--
                    'D' -> yHead++
                    'L' -> xHead--
                    'R' -> xHead++
                    else -> error("nope! $movement")
                }
                if ((yHead - yTail).absoluteValue == 2){
                    yTail += (yHead - yTail) / 2
                    xTail = xHead
                    visitedPositions.add(xTail to yTail)
                }
                else if ((xHead - xTail).absoluteValue == 2){
                    xTail += (xHead - xTail) / 2
                    yTail = yHead
                    visitedPositions.add(xTail to yTail)
                }
            }
        }
        return visitedPositions.size

    }

    private fun countTailPositionsWithPrimitivesOnly(input: List<String>, @Suppress("SameParameterValue") amountOfKnots: Int = 2): Int{
        // knots[numberOfKnot][0 for x / 1 for y]
        val knots = Array(amountOfKnots){ IntArray(2){ 0 } }
        val visitedPositions = HashSet<Pair<Int, Int>>().apply{
            add(0 to 0)
        }
        input.forEach { movement ->
            repeat(movement.grabFirstIntOrNull()!!){
                when(movement[0]){
                    'U' -> knots[0][1]--
                    'D' -> knots[0][1]++
                    'L' -> knots[0][0]--
                    'R' -> knots[0][0]++
                    else -> error ("bad movement $movement")
                }
                (1 until knots.size).forEach { k ->
                    if ((knots[k-1][0] - knots[k][0]).absoluteValue == 2){
                        knots[k][0] += (knots[k-1][0] - knots[k][0]) / 2
                        knots[k][1] = knots[k-1][1]
                    }
                    else if ((knots[k-1][1] - knots[k][1]).absoluteValue == 2){
                        knots[k][1] += (knots[k-1][1] - knots[k][1]) / 2
                        knots[k][0] = knots[k-1][0]
                    }
                }
                visitedPositions.add(knots.last()[0] to knots.last()[1])
            }
        }
        return visitedPositions.size
    }

    @Suppress("unused")
    private fun countTailPositions(input: List<String>, amountOfKnots: Int = 2): Int{
        val knots = Array(amountOfKnots){ Coordinate(0,0) }
        val visitedPositions = HashSet<Coordinate>().apply{
            add(knots[1])
        }
        input.forEach { movement ->
            repeat(movement.grabFirstIntOrNull()!!){
                knots[0] = when(movement[0]){
                    'U' -> knots[0].north()
                    'D' -> knots[0].south()
                    'L' -> knots[0].west()
                    'R' -> knots[0].east()
                    else -> error ("bad movement $movement")
                }
                (1 until knots.size).forEach { k ->
                    val head = knots[k - 1] // not declaring this probably saves a nanosecond, but i'll leave that to the JIT optimizer
                    val tail = knots[k]

                    if ((head.x - tail.x).absoluteValue >= 2 || (head.y - tail.y).absoluteValue >= 2)
                        knots[k] = moveTailTowardsHead(tail, head)
                }
                visitedPositions.add(knots.last())
            }
        }
        return visitedPositions.size
    }

    // doesn't check for valid data
    private fun moveTailTowardsHead(tail: Coordinate, head: Coordinate): Coordinate{
        val newX = when{
            head.x > tail.x -> tail.x + 1
            head.x < tail.x -> tail.x - 1
            else -> tail.x
        }
        val newY = when{
            head.y > tail.y -> tail.y + 1
            head.y < tail.y -> tail.y - 1
            else -> tail.y
        }
        return Coordinate(newX, newY)
    }
}