package common.extensions

infix fun <T: Comparable<T>> ClosedRange<T>.overlaps(other: ClosedRange<T>): Boolean =
    maxOf(this.start, other.start) <= minOf(this.endInclusive, other.endInclusive)

/**
 * [other] completely inside of [this]
 */
infix fun <T: Comparable<T>> ClosedRange<T>.contains(other: ClosedRange<T>): Boolean =
    other.start >= start && other.endInclusive <= endInclusive

val ClosedRange<Int>.size get() = endInclusive - start + 1