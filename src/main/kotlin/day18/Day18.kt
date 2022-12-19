package day18

import common.Solution
import common.grids.Coordinate3D
import java.util.LinkedList

class Day18: Solution(18) {
    override fun answer1(input: List<String>) = input.map{
    Coordinate3D.of(it)
}.toSet().countSurfaceAreas()

    override fun answer2(input: List<String>) = input.map{
        Coordinate3D.of(it)
    }.toSet().let{ droplet ->
        val xRange = droplet.minBy { it.x }.x - 1..droplet.maxBy { it.x }.x + 1
        val yRange = droplet.minBy { it.y }.y - 1..droplet.maxBy { it.y }.y + 1
        val zRange = droplet.minBy { it.z }.z - 1..droplet.maxBy { it.z }.z + 1

        val water = Coordinate3D(xRange.first, yRange.first, zRange.first).maxExpansionInBox(xRange, yRange, zRange, droplet)

        droplet.sumOf{ c ->
            c.sixNeighbours().count { it !in droplet && it in water }
        }
    }

    private fun Set<Coordinate3D>.countSurfaceAreas() = sumOf{ c ->
        c.sixNeighbours().count { it !in this }
    }

    //returns 0 if unbounded
    private fun Coordinate3D.maxExpansionInBox(xRange: IntRange, yRange: IntRange, zRange: IntRange, droplet: Set<Coordinate3D>): Set<Coordinate3D>{
        val frontier = LinkedList(listOf(this))
        val visited = HashSet<Coordinate3D>()
        while(frontier.isNotEmpty()){
            val c = frontier.removeFirst()
            visited.add(c)
            frontier.addAll(c.sixNeighbours().filter { it !in droplet && it !in visited && it.x in xRange && it !in frontier && it.y in yRange && it.z in zRange})
        }
        return visited
    }
}
