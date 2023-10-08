package day22

import common.grids.Coordinate

class Maze(private val mazeInput: List<String>) {
    private var currentPos = Coordinate(mazeInput[0].indexOfFirst { it == '.' }, 0)

    fun turn(direction: Char){
        currentDirection = if (direction == 'L') currentDirection.left else currentDirection.right
    }

    fun stepNTimes(n: Int){
        repeat(n){
            val nextPos = currentPos + currentDirection.vector
            currentPos = when(valueOfPos(nextPos)){
                '.' -> nextPos
                '#' -> currentPos
                else -> wrapAround()
            }
        }
    }

    private var currentDirection: Direction = Direction.RIGHT

    fun calculateValue(): Int =
        (currentPos.y + 1) * 1000 +
        (currentPos.x + 1) * 4 +
        when(currentDirection){
            Direction.RIGHT -> 0
            Direction.DOWN -> 1
            Direction.LEFT -> 2
            Direction.UP -> 3
        }

    private fun valueOfPos(pos: Coordinate) = mazeInput.getOrNull(pos.y)?.getOrNull(pos.x)

    private fun wrapAround(): Coordinate{
        val reverseVector = currentDirection.vector.reverse
        var nextPos = currentPos
        // move in opposite direction until we hit a blank spot
        while ((valueOfPos(nextPos) ?: ' ') != ' '){
            nextPos += reverseVector
        }
        //now do one step back
        nextPos += currentDirection.vector
        return if (valueOfPos(nextPos) == '.')
            nextPos
        else currentPos
    }

    override fun toString() = mazeInput.joinToString("\n")
}