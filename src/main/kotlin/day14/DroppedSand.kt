package day14

import common.grids.Coordinate

// just a Coordinate but different class, so I can make pretty PNG pictures
class DroppedSand(coordinate: Coordinate): Coordinate(coordinate.x, coordinate.y) {
}