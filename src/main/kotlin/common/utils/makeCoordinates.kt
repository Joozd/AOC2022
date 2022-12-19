package common.utils

import common.grids.CoordinateWithValue

/**
 * Change a list of Strings into a grid of CoordinateWithValues
 */
fun makeCoordinatesWithValue(input: List<String>): List<CoordinateWithValue<Char>> =
    input.mapIndexed { y, s ->
        s.mapIndexed { x, c ->
            CoordinateWithValue(x,y,c)
        }
    }.flatten()
