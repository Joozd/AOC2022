package common.utils

import common.extensions.overlaps
import java.util.*

/**
 * Joins [ranges] so any overlapping ranges become one., eg [1..3, 2..5] becomes [1..5].
 * The order of the output is undefined.
 */
fun joinRanges(ranges: Collection<IntRange>): List<IntRange>{
    val completedRanges = mutableListOf<IntRange>()
    val input = LinkedList(ranges)
    while(input.isNotEmpty()){
        val r = input.removeFirst()
        val match = input.firstOrNull{ it overlaps r }
        if (match == null)
            completedRanges.add(r)
        else {
            input.remove(match)
            input.add(minOf(r.first, match.first)..maxOf(r.last, match.last))
        }
    }
    return completedRanges
}