package day08

import common.Solution
import common.grids.Coordinate

/**
 * I have the feeling there is a huge performance win in here somewhere, will think about it in the shower.
 * A lot of duplicate work is being done, can that be avoided?
 * Scenic score depends on both current value and the values of everything in sight...
 */
class Day8: Solution(8) {
    // cache for performance reasons
    private lateinit var trees: List<List<Int>>

    override fun answer1(input: List<String>): Int {
        trees = input.map{ line ->
            line.map { it.digitToInt() }
        }
        return visibleTreesMap().sumOf { it.count { it } }
    }

    // Only looking at the trees found in answer 1 makes this about 4 times faster
    // (and gives the correct answer for my input) but I can imagine situations where that would give the wrong answer

    // Cutting the outsides off here instead of checking that in the functions (and removing check from functions)
    // is about 5-10% faster.
    override fun answer2(input: List<String>) = (1..trees.size - 2).maxOf{ y ->
        (1..trees[y].size - 2).maxOf { x ->
            scenicScore(Coordinate(x,y), trees)
        }
    }

    private fun scenicScore(coordinate: Coordinate, trees: List<List<Int>>) =
        countTreesToTheLeft(coordinate, trees) *
                countTreesToTheRight(coordinate, trees) *
                countTreesToTheNorth(coordinate, trees) *
                countTreesToTheSouth(coordinate, trees)



    private fun visibleTreesMap(): Array<Array<Boolean>>{
        val visibleTrees = Array(trees.size){ Array(trees[0].size) { false } }
        for (line in trees.indices){
            treesVisibleFromLeft(line, trees).forEach{ visibleTreeIndex ->
                visibleTrees[line][visibleTreeIndex] = true
            }
            treesVisibleFromRight(line, trees).forEach{ visibleTreeIndex ->
                visibleTrees[line][visibleTreeIndex] = true
            }
        }
        for (column in trees[0].indices){
            treesVisibleFromTop(column, trees).forEach{ visibleTreeIndex ->
                visibleTrees[visibleTreeIndex][column] = true
            }
            treesVisibleFromBottom(column, trees).forEach{ visibleTreeIndex ->
                visibleTrees[visibleTreeIndex][column] = true
            }
        }
        return visibleTrees
    }

    //early exit at 9 makes the answer about 15% faster
    private fun treesVisibleFromLeft(lineIndex: Int, map: List<List<Int>>): List<Int> = buildList{
        var highest = -1
        for (i in map[lineIndex].indices){
            val n = map[lineIndex][i]
            if (n > highest){
                //println("LEFT: $n($i) is higher than $highest")
                add(i)
                highest = n
            }
            if (n == 9) break
        }
    }

    private fun treesVisibleFromRight(lineIndex: Int, map: List<List<Int>>): List<Int> = buildList{
        var highest = -1
        for (i in map[lineIndex].size -1 downTo 0){
            val n = map[lineIndex][i]
            if (n > highest){
                //println("RIGHT: $n($i) is higher than $highest")
                add(i)
                highest = n
            }
            if (n == 9) break
        }
    }

    private fun treesVisibleFromTop(columnIndex: Int, map: List<List<Int>>): List<Int> = buildList{
        var highest = -1
        for (i in map.indices){
            val n = map[i][columnIndex]
            if (n > highest){
                //println("TOP: $n($i) is higher than $highest")
                add(i)
                highest = n
            }
            if (n == 9) break
        }
    }

    private fun treesVisibleFromBottom(columnIndex: Int, map: List<List<Int>>): List<Int> = buildList{
        var highest = -1
        for (i in map.size-1 downTo 0){
            val n = map[i][columnIndex]
            if (n > highest){
                //println("BOTTOM: $n($i) is higher than $highest")
                add(i)
                highest = n
            }
            if (n == 9) break
        }
    }

    private fun countTreesToTheLeft(coordinate: Coordinate, trees: List<List<Int>>): Int{
        var visibleTrees = 0
        for (x in coordinate.x -1 downTo 0){
            visibleTrees++
            if(trees[coordinate.y][x] >= trees[coordinate.y][coordinate.x])
                return visibleTrees

        }
        return visibleTrees
    }

    private fun countTreesToTheRight(coordinate: Coordinate, trees: List<List<Int>>): Int{
        var visibleTrees = 0
        for (x in coordinate.x +1 until trees[0].size){
            visibleTrees++
            if(trees[coordinate.y][x] >= trees[coordinate.y][coordinate.x])
                return visibleTrees

        }
        return visibleTrees
    }

    private fun countTreesToTheNorth(coordinate: Coordinate, trees: List<List<Int>>): Int{
        var visibleTrees = 0
        for (y in coordinate.y -1 downTo 0){
            visibleTrees++
            if(trees[y][coordinate.x] >= trees[coordinate.y][coordinate.x])
                return visibleTrees
        }
        return visibleTrees
    }

    private fun countTreesToTheSouth(coordinate: Coordinate, trees: List<List<Int>>): Int{
        var visibleTrees = 0
        for (y in coordinate.y +1 until trees.size){
            visibleTrees++
            if(trees[y][coordinate.x] >= trees[coordinate.y][coordinate.x])
                return visibleTrees

        }
        return visibleTrees
    }
}