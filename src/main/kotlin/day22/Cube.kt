package day22

import common.CharArrayWindow2D
import common.extensions.rotateLeft
import common.linearalgebra.IntVector
import common.linearalgebra.IntVectorMatrix
import java.util.*

/**
 * We create a cube by rolling it over all the faces in the net provided
 * Rolling is pretty literal; the cube gets rotated and moves just like in a physical roll.
 */
class Cube(contents: List<String>) {
    private val rib = getRibLength(to2dArray(contents))

    private val cubeMovementVectorsMap = buildMap {
        put(UP, IntVector(0, -rib))
        put(DOWN, IntVector(0, rib))
        put(LEFT, IntVector(-rib, 0))
        put(RIGHT, IntVector(rib, 0))
    }

    private lateinit var currentPosition: IntVector
    private val netPos get() =
        coordinateResultsCube[currentPosition]?.let { it / 1000 to it % 1000 / 4 }

    private var currentDirection: IntVector = IntVector(1,0, 0) // initially facing right, so towards increasing x
    private var currentUp: IntVector = IntVector(0, 0, -1)       // up is initially z down, because we are on the outside of the cube.

    private val cube: Map<IntVector, Char>
    private val coordinateResultsCube: Map<IntVector, Int>

    val result get() = netPos.let{
        (it!!.first + 1) * 1000 + (it.second + 1) * 4 + currentFacingValue()
    }

    init{
        initializeCube(to2dArray(contents)).let{
            cube = it.first
            coordinateResultsCube = it.second
        }
    }

    /**
     * Take a step. If a step runs into a wall, nothing happens, so you can safely run into a wall 23 times.
     */
    fun step(){
        var nextPos = currentPosition + currentDirection
        var nextDirection = currentDirection
        var nextUp = currentUp
        if (nextPos !in cube.keys){
            nextDirection = currentUp.inverse()
            nextUp = currentDirection
            nextPos += nextDirection
        }
        if(cube[nextPos] == '.'){
            currentPosition = nextPos
            currentDirection = nextDirection
            currentUp = nextUp
        }
    }

    //Turns are left or right
    fun turn(direction: Char){
        require(direction in "LR") { "Wrong direction: $direction"}
        currentDirection = if(direction == 'L')
            currentUp cross currentDirection
        else currentDirection cross currentUp


    }

    // Because we don't keep track of the facing along the original net, we'll have to calculate that from
    // the current position and direction information.
    // We check the difference of the current net position and the next or previous one,
    // which gives us the net direction.
    private fun currentFacingValue(): Int{
        val up = IntVector(-1, 0)
        val right = IntVector(0, 1)
        val down = IntVector(1, 0)
        val left = IntVector(0, -1)

        val facing = buildMap{
            put(right, 0)
            put(down, 1)
            put(left, 2)
            put(up, 3)
        }

        val currentNetPos = coordinateResultsCube[currentPosition]!!.let{ IntVector((it / 1000 to it % 1000 / 4).toList()) }
        val nextNetPos = coordinateResultsCube[currentPosition + currentDirection]?.let{ IntVector((it / 1000 to it % 1000 / 4).toList()) }

        if(nextNetPos != null){
            return facing[nextNetPos - currentNetPos]!!
        }

        //fallback in case no position ahead of the current position
        val previousNetPos = coordinateResultsCube[currentPosition + currentDirection.inverse()]!!.let{ IntVector((it / 1000 to it % 1000 / 4).toList()) }
        return facing[currentNetPos - previousNetPos]!!
    }

    private fun getRibLength(net: Array<CharArray>): Int {
        val shortestLine = getShortestLine(net)
        val shortestColumn = getShortestLine(net.rotateLeft())
        return minOf(shortestLine, shortestColumn)
    }

    private fun getShortestLine(array: Array<CharArray>): Int {
        return array.minOf { it.count { c -> c in ".#"} }
    }

    /**
     * This uses up the array provided, make sure you don't need it afterward!
     * The idea is that we roll the cube over the map, and roll back if we get into a dead end.
     * @return a pair of two cubes: One with the contents, the other one with the coordinate answer value of the position.
     * NOTE: The final answer also needs a "facing" component,
     *  get that by finding the original coordinates of the current position and the position in front or behind.
     */
    private fun initializeCube(net: Array<CharArray>): Pair<Map<IntVector, Char>, Map<IntVector, Int>> { // cannot use IntArray because intArrayOf(1,2,3) != intArrayOf(1,2,3); map key doesn't use contentEquals.

        var currentPos = findStartPosition(net) // e.g. [50,0] // position vector

        var cube: Map<IntVector, Char> = emptyMap()
        var coordinateResultsCube: Map<IntVector, Int> = emptyMap()

        // fake cube to keep track of starting position
        var currentPositionInitializationMap = buildMap{ put(IntVector(0, 0, -1), 0)} // putting it in a map, so I can treat it as a cube


        val movesCompleted = Stack<Int>()
        // At the start of this loop, the cube must have moved to an unconsumed part of the net.
        // It will consume this part and move to the next unconsumed part
        // The cube is only allowed to backtrack if there are no other consumable parts of the net to be found.


        fun rollCubes(direction: Int) {
            cube = cube.roll(direction)
            coordinateResultsCube = coordinateResultsCube.roll(direction)
            currentPositionInitializationMap = currentPositionInitializationMap.roll(direction)
            currentDirection = currentDirection.rotate(direction)
            currentUp = currentUp.rotate(direction)
        }


        while(true){
            val currentCoveredNet = CharArrayWindow2D(net, currentPos[1]until(currentPos[1] + rib), (currentPos[0] until (currentPos[0] + rib)))
            val facing = HashMap<IntVector, Char>(50*50)
            val coordinateResultFacing = HashMap<IntVector, Int>(50*50)

            currentCoveredNet.forEachIndexed { y, ca ->
                ca.forEachIndexed { x, c ->
                    // add the current layer to the cube, and move it one down to it actually lies on top of the cube.
                    // This ensures that every side ends in emptiness, which gives the monkey room to make a turn when walking over the edge of the cube.
                    facing[IntVector(x,y,-1)] = c
                    coordinateResultFacing[IntVector(x,y,-1)] = 1000*(y + currentPos[1]) + 4*(x + currentPos[0]) // this, plus the "facing" value is the answer if we end on this position.
                    currentCoveredNet[y][x]= ' ' // remove data that is on the cube from the net
                }
            }
            cube = cube + facing
            coordinateResultsCube = coordinateResultsCube + coordinateResultFacing

            if(!net.hasContent()) break // if we are done, exit the while loop. A bad net with unreachable content will give an exception later on.

            var nextDir: Int? = null
            while(nextDir == null){
                val lastMove = if (movesCompleted.isEmpty()) null else movesCompleted.peek()
                nextDir = findNextDirOrNull(net, currentPos, lastMove)
                if (nextDir == null) {
                    // If nextDir == null, it means there is no visitable neighbour, se we backtrack
                    // (move back and roll back, and remove last move from stack, so we can retry from previous location)
                    val returnDir = try { movesCompleted.pop().reverseMove() } catch (e: EmptyStackException) { error("Empty stack; map is now ${net.joinToString("\n") { it.toList().joinToString("")} }") }
                    currentPos += cubeMovementVectorsMap[returnDir]!! // step back
                    rollCubes(returnDir)
                }
            }
            rollCubes(nextDir)

            currentPos += cubeMovementVectorsMap[nextDir]!!
            movesCompleted.push(nextDir)
        }
        currentPosition = currentPositionInitializationMap.keys.first()

        return cube to coordinateResultsCube
    }

    private fun findStartPosition(net: Array<CharArray>) = IntVector(net.first().indexOfFirst { it in ".#" }, 0)

    private fun findNextDirOrNull(net: Array<CharArray>, currentPos: IntVector, lastMove: Int?): Int?{

        val directionsWithVectors = listOf(UP, DOWN, LEFT, RIGHT) // put direction vectors in list
            .filter { it != lastMove?.reverseMove()} // remove the direction that leads to the previous location from list
            .map {it to cubeMovementVectorsMap[it]!! + currentPos } // get the vectors and add current position to get position vectors to possible destinations, matched to the direction.


        return directionsWithVectors.firstOrNull{ newPos ->
            // get the first position where moving that way makes us end up on a valid square.
            newPos.second[1] in net.indices
                && (net[newPos.second[1]].getOrNull(newPos.second[0])?: ' ') in ".#"
        }?.first // first of the pair of <DIRECTION, VECTOR>, so DIRECTION
    }

    // this copies the list into an array.
    private fun to2dArray(contents: List<String>) =
        contents.map { it.toCharArray() }.toTypedArray()

    /**
     * Check if all data in the net has been consumed
     */
    private fun Array<CharArray>.hasContent()=
        any{row ->
            row.any { c -> c in ".#"}
        }

    // reverses a move, so LEFT becomesRIGHT etc
    private fun Int.reverseMove(): Int = (this + 2) % 4

    /**
     * Roll a map of vectors around one of the axes, according to [direction]
     */
    private fun <T> Map<IntVector, T>.roll(direction: Int): Map<IntVector, T> = buildMap{
        val transformation = when(direction){
            UP -> ROLL_TOWARDS_LOWER_Y
            RIGHT -> ROLL_RIGHT
            DOWN -> ROLL_TOWARDS_HIGHER_Y
            LEFT -> ROLL_LEFT
            else -> error ("Wrong direction $direction, must be 0 until 4")
        }

        val oldMap =
            if (direction in listOf(DOWN, RIGHT))
                // DOWN and RIGHT need the cube to be moved before rotating
                this@roll.translate(direction)
            else this@roll

        oldMap.entries.forEach{ oldEntry ->
            val newKey = oldEntry.key * transformation
            put(newKey, oldEntry.value)
        }
    }
        .let{
        if (direction in listOf(UP, LEFT))
            // UP and LEFT need the cube to be moved after rotating
            it.translate(direction)
        else it
    }

    /**
     * Rotate a vector
     */
    private fun IntVector.rotate(direction: Int): IntVector{
        val transformation = when(direction){
            UP -> ROLL_TOWARDS_LOWER_Y
            RIGHT -> ROLL_RIGHT
            DOWN -> ROLL_TOWARDS_HIGHER_Y
            LEFT -> ROLL_LEFT
            else -> error ("Wrong direction $direction, must be 0 until 4")
        }
        return this * transformation
    }


    /**
     * Translate a map of vectors along one of the axes, according to [direction].
     * Only for [RIGHT] and [DOWN]
     */
    private fun <T> Map<IntVector, T>.translate(direction: Int): Map<IntVector, T> =
        buildMap {
            val currentMap = this@translate
            val d = rib - 1
            val translation = when (direction){
                UP      -> IntVector(0, d, 0)
                RIGHT   -> IntVector(-d, 0, 0)
                DOWN    -> IntVector(0, -d, 0)
                LEFT    -> IntVector(d, 0, 0)
                else -> error ("BAD DIRECTION $direction")
            }
            currentMap.entries.forEach {
                put(it.key + translation, it.value)
            }
        }/*.also{ println("TRANSLATED TO ${it.keys*//*.filter {it.toList().all { it %10 == 0 || it == -1 }}*//*}")}*/


    companion object{
        // "down" for y means lower values
        // "down" for z means down.

        // Constructing the Matrices from row vectors for readability
        private val ROLL_LEFT = IntVectorMatrix.ofRowVectors(
            IntVector(0,0,-1), // z -> -x  (z down becomes x to the right)
            IntVector(0,1,0),  // y -> y   (up stays up)
            IntVector(1,0,0),  // x -> z   (x to the left becomes z down)
        )
        private val ROLL_TOWARDS_LOWER_Y = IntVectorMatrix.ofRowVectors( // "up" is the lowest value of y
            IntVector(1,0,0),  // x -> x   (left stays left)
            IntVector(0,0,-1),  // z -> y   (z down becomes y up)
            IntVector(0,1,0), // y -> -z  (y up becomes z up). This means side going up should be at the higher y-side, needing translation
        )

        // NOTE: Translate cube by 1 rib so the cube rotates around the "highest x" rib.
        private val ROLL_RIGHT = IntVectorMatrix.ofRowVectors(
            IntVector(0,0,1),  // z -> x   (z up becomes x up)
            IntVector(0,1,0),  // y -> y   (y up stays y up)
            IntVector(-1,0,0), // x -> -y  (higher negative x becomes higher z)
        )

        private val ROLL_TOWARDS_HIGHER_Y = IntVectorMatrix.ofRowVectors( // "down" is the highest value of y
            IntVector(1,0,0),  // x -> x   (left stays left)
            IntVector(0,0,1), // z -> -y  (z up becomes y up)
            IntVector(0,-1,0),  // y -> z   (y down (higher negative values) becomes z up (higher values).
        )

        // this way, (currentDirection + 2) % 4 means reverse direction.
        private const val UP = 0    // towards lower y
        private const val RIGHT = 1
        private const val DOWN = 2  // towards higher y
        private const val LEFT = 3
    }
}
