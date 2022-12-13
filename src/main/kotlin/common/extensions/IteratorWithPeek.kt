package common.extensions

class IteratorWithPeek<T>(private val iterable: List<T>): Iterator<T> {
    private var index = 0
    override fun hasNext(): Boolean =
        index + 1 in iterable.indices

    /**
     * Returns the next element in the iteration.
     */
    override fun next(): T = iterable[index++]

    fun peek(): T = iterable[index]
}