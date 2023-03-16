package day22

import common.Solution
import java.lang.StringBuilder

class Day22: Solution(22) {
    override fun answer1(input: List<String>): Any {
        val directionsInput = input.last()
        val directions: List<Any> = makeDirections(directionsInput)
        val mazeInput = input.dropLast(2)

        val maze = Maze(mazeInput)


        directions.forEach{
            if (it is Char)
                maze.turn(it)
            else
                maze.stepNTimes(it as Int)
        }

        return maze.calculateValue()
    }

    override fun answer2(input: List<String>) = input

    private fun makeDirections(input: String): List<Any>{
        val sb = StringBuilder()
        return buildList{
            input.forEach {
                if (it.isDigit())
                    sb.append(it)
                else{
                    if (sb.isNotBlank()) {
                        add(sb.toString().toInt())
                        sb.clear()
                    }
                    add(it)
                }
            }
            if (sb.isNotBlank())
                add(sb.toString().toInt())
        }
    }
}