package day22

import common.grids.Vector

sealed class Direction {
    abstract val left: Direction
    abstract val right: Direction
    abstract val vector: Vector


    object LEFT: Direction(){
        override val left = DOWN
        override val right = UP
        override val vector = Vector(-1, 0)
    }


    object RIGHT: Direction(){
        override val left = UP
        override val right = DOWN
        override val vector = Vector(1, 0)
    }

    object UP: Direction(){
        override val left = LEFT
        override val right = RIGHT
        override val vector = Vector(0, -1)
    }

    object DOWN: Direction(){
        override val left = RIGHT
        override val right = LEFT
        override val vector = Vector(0, 1)
    }

}

