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

fun List<String>.findFirstPositionOrNull(value: Char): Coordinate? {
    for (y in this.indices) {
        for (x in this[y].indices) {
            if (this[y][x] == value)
                return Coordinate(x, y)
        }
    }
    return null
}

operator fun List<String>.get(position: Coordinate) = this[position.y][position.x]

operator fun List<String>.contains(position: Coordinate) = position.y in this.indices && position.x in this[position.y].indices
