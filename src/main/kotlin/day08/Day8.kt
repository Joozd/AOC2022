package day08

import common.Solution
import common.grids.Coordinate

/**
 * I have the feeling there is a huge performance win in here somewhere, will think about it in the shower.
 * A lot of duplicate work is being done, can that be avoided?
 * Scenic score depends on both current value and the values of everything in sight...
 */
class Day8: Solution(8) {
    // 0.14 ms
    // Noticed that there is no need to make ints from chars as '0'..'9' increases in the same way as 0..9
    override fun answer1(input: List<String>): Int =
        visibleTreesMap(input).sumOf { visibleTrees -> visibleTrees.count { it } }


    // Only looking at the trees found in answer 1 makes this about 4 times faster
    // (and gives the correct answer for my input) but I can imagine situations where that would give the wrong answer

    // Cutting the outsides off here instead of checking that in the functions (and removing check from functions)
    // is about 5-10% faster.
    // current 0.59 ms
    override fun answer2(input: List<String>) = (1..input.size - 2).maxOf{ y ->
        (1..input[y].length - 2).maxOf { x ->
            scenicScore(Coordinate(x,y), input)
        }
    }

    private fun scenicScore(coordinate: Coordinate, trees: List<String>) =
        countTreesToTheLeft(coordinate, trees) *
                countTreesToTheRight(coordinate, trees) *
                countTreesToTheNorth(coordinate, trees) *
                countTreesToTheSouth(coordinate, trees)



    private fun visibleTreesMap(trees: List<String>): Array<Array<Boolean>>{
        val visibleTrees = Array(trees.size){ Array(trees[0].length) { false } }
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
    private fun treesVisibleFromLeft(lineIndex: Int, map: List<String>): List<Int> = buildList{
        var highest = '0' - 1
        for (i in map[lineIndex].indices){
            val n = map[lineIndex][i]
            if (n > highest){
                //println("LEFT: $n($i) is higher than $highest")
                add(i)
                highest = n
            }
            if (n == '9') break
        }
    }

    private fun treesVisibleFromRight(lineIndex: Int, map: List<String>): List<Int> = buildList{
        var highest = '0' - 1
        for (i in map[lineIndex].length -1 downTo 0){
            val n = map[lineIndex][i]
            if (n > highest){
                //println("RIGHT: $n($i) is higher than $highest")
                add(i)
                highest = n
            }
            if (n == '9') break
        }
    }

    private fun treesVisibleFromTop(columnIndex: Int, map: List<String>): List<Int> = buildList{
        var highest = '0' - 1
        for (i in map.indices){
            val n = map[i][columnIndex]
            if (n > highest){
                //println("TOP: $n($i) is higher than $highest")
                add(i)
                highest = n
            }
            if (n == '9') break
        }
    }

    private fun treesVisibleFromBottom(columnIndex: Int, map: List<String>): List<Int> = buildList{
        var highest = '0' - 1
        for (i in map.size-1 downTo 0){
            val n = map[i][columnIndex]
            if (n > highest){
                //println("BOTTOM: $n($i) is higher than $highest")
                add(i)
                highest = n
            }
            if (n == '9') break
        }
    }

    private fun countTreesToTheLeft(coordinate: Coordinate, trees: List<String>): Int{
        var visibleTrees = 0
        for (x in coordinate.x -1 downTo 0){
            visibleTrees++
            if(trees[coordinate.y][x] >= trees[coordinate.y][coordinate.x])
                return visibleTrees

        }
        return visibleTrees
    }

    private fun countTreesToTheRight(coordinate: Coordinate, trees: List<String>): Int{
        var visibleTrees = 0
        for (x in coordinate.x +1 until trees[0].length){
            visibleTrees++
            if(trees[coordinate.y][x] >= trees[coordinate.y][coordinate.x])
                return visibleTrees

        }
        return visibleTrees
    }

    private fun countTreesToTheNorth(coordinate: Coordinate, trees: List<String>): Int{
        var visibleTrees = 0
        for (y in coordinate.y -1 downTo 0){
            visibleTrees++
            if(trees[y][coordinate.x] >= trees[coordinate.y][coordinate.x])
                return visibleTrees
        }
        return visibleTrees
    }

    private fun countTreesToTheSouth(coordinate: Coordinate, trees: List<String>): Int{
        var visibleTrees = 0
        for (y in coordinate.y +1 until trees.size){
            visibleTrees++
            if(trees[y][coordinate.x] >= trees[coordinate.y][coordinate.x])
                return visibleTrees

        }
        return visibleTrees
    }
}