package common.extensions

import java.util.*

//no check on data
fun List<String>.toInts() = map{ it.toInt() }

operator fun List<Int>.contains(other: List<Int>): Boolean =
    Collections.indexOfSubList(this, other) != -1
