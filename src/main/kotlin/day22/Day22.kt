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

    override fun answer2(input: List<String>): Any {
        val cube = Cube(input.dropLast(2))
        val directions = makeDirections(input.last())
        directions.forEach {
            if(it is Int)
                repeat(it){
                    cube.step()
                }
            else{
                cube.turn(it as Char)
            }
        }

        return cube.result
    }

    /**
     * Make a list of Ints and Chars
     */
    private fun makeDirections(input: String): List<Any>{
        val sb = StringBuilder()
        return buildList{
            input.forEach {
                // if the current character is a digit, add it to SB
                if (it.isDigit())
                    sb.append(it)
                else{
                    //if the current character is NOT a digit, move the digits in SB as an Int to the list we are building if SB is not empty.
                    if (sb.isNotBlank()) {
                        add(sb.toString().toInt())
                        sb.clear()
                    }
                    //since the current character is not a digit, it is a turn that we add to the current list as a Char.
                    add(it)
                }
            }
            if (sb.isNotBlank())
                add(sb.toString().toInt())
        }
    }
}