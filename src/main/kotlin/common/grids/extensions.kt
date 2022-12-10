package common.grids


//this removes any offsets (positive or negative) so result always touches 0 axes
fun Collection<Coordinate>.gridString(): String{
    val xOffSet = minOf{ it.x }
    val yOffset = minOf{ it.y }
    val biggestX = maxOf{ it.x - xOffSet }
    val biggestY = maxOf{ it.y - yOffset }
    return (0..biggestY).joinToString("\n"){ y ->
        (0..biggestX).joinToString(""){ x ->
            if (Coordinate(x + xOffSet, y + yOffset) in this) "X" else "."
        }
    }
}
