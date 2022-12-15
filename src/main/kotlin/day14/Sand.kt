package day14

import common.grids.Coordinate

class Sand(private val initialPos: Coordinate, private val cave: MutableSet<Coordinate>, private val maxYValue: Int, private val  hasFloor: Boolean = false){
    private val floor = maxYValue + 1

    var currentPos = initialPos

    // Drops this sand and returns true if added to cave, or false if dropped into the abyss.
    fun drop(): Boolean{
        if (initialPos in cave) return false
        while(goOneDown()){
            if (!hasFloor && currentPos.y > maxYValue)
                return false
            if (hasFloor && currentPos.y == floor){
                cave.add(currentPos)
                return true
            }
        }
        cave.add(currentPos)
        return true
    }

    //true if moved, false if come to rest
    private fun goOneDown(): Boolean{
        val d = currentPos.south()
        if (d !in cave){
            currentPos = d
            return true
        }
        if (d.west() !in cave) {
            currentPos = d.west()
            return true
        }
        if (d.east() !in cave) {
            currentPos = d.east()
            return true
        }
        return false
    }
}