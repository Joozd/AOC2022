package common.grids


//this removes any offsets (positive or negative) so result always touches 0 axes
fun Collection<Coordinate>.gridString(empty: Char = '.', padding: Int = 0): String{
    val xOffSet = minOf{ it.x - padding }
    val yOffset = minOf{ it.y - padding }
    val biggestX = maxOf{ it.x - xOffSet + padding }
    val biggestY = maxOf{ it.y - yOffset + padding }
    val lines = Array(biggestY + 1){
        CharArray(biggestX + 1){ empty }
    }
    forEach {
        lines[it.y - yOffset][it.x - xOffSet] = if (it is CoordinateWithValue<*>) it.value.toString().first() else '#'
    }
    return lines.joinToString("\n"){ it.joinToString("")}
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

// only works for horizontal or vertical lines
fun Coordinate.lineTo(other: Coordinate): List<Coordinate> =
   if (other.x != x)
       (minOf(other.x, x)..maxOf(other.x,x)).map{ Coordinate(it, y) }
   else
    (minOf(other.y, y)..maxOf(other.y,y)).map{ Coordinate(x, it) }