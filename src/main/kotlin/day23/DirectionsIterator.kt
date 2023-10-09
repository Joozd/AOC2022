package day23

class DirectionsIterator: Iterable<Char> {
    private var currentPosInRotation = 0
    val rotations = "NSWE".toCharArray() // benchmarked it and CharArray access is faster than string access

    fun rotate(){
        currentPosInRotation++
    }
    override fun iterator() = object: Iterator<Char>{
        var currentDir = 0
        override fun hasNext(): Boolean =
            currentDir < 4

        override fun next(): Char =
            rotations[(currentDir++ + currentPosInRotation) % 4]
    }
}