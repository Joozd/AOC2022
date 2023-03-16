package day22

import common.grids.Vector

sealed class Directions {
    abstract val left: Directions
    abstract val right: Directions
    abstract val vector: Vector


    object LEFT: Directions(){
        override val left = DOWN
        override val right = UP
        override val vector = Vector(-1, 0)
    }


    object RIGHT: Directions(){
        override val left = UP
        override val right = DOWN
        override val vector = Vector(1, 0)
    }

    object UP: Directions(){
        override val left = LEFT
        override val right = RIGHT
        override val vector = Vector(0, -1)
    }

    object DOWN: Directions(){
        override val left = RIGHT
        override val right = LEFT
        override val vector = Vector(0, 1)
    }

}

