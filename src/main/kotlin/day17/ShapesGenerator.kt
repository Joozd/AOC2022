package day17

import common.grids.Coordinate
import common.utils.makeCoordinatesWithValue

class ShapesGenerator {
    private val shapes = makeShapes()

    private var current = 0

    val lastShapeGenerated get() = (current + 4) % 5

    fun next(bottomY: Int) = shapes[current++ % 5].map{
        Coordinate(it.x, it.y + bottomY)
    }

    private fun makeShapes() = SHAPES.split("\n\n").map{ shape ->
        makeCoordinatesWithValue(shape.lines()).mapNotNull { c ->
            if(c.value == '.') null else Coordinate(c.x + 2, c.y - shape.lines().size + 1) // they always start 2 from the left, bottom row is index 0.
        }
    }

    companion object{
        private val SHAPES = """####

.#.
###
.#.

..#
..#
###

#
#
#
#

##
##""".lines().joinToString("\n")
    }
}