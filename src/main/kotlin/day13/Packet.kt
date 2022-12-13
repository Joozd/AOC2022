package day13

import common.extensions.IteratorWithPeek

class Packet(private val contents: List<Any>): Comparable<Packet> {
    override fun compareTo(other: Packet): Int =
        when(leftSmallerThanRight(this.contents, other.contents)){
            true -> -1
            false -> 1
            null -> 0
        }

    //null means equal, go to next item in list if able.
    private fun leftSmallerThanRight(left: Any, right: Any): Boolean? {
        return when {
            left is Int && right is Int -> if (left == right) null else (left < right)
            left is List<*> && right is Int -> leftSmallerThanRight(left, listOf(right))
            left is Int && right is List<*> -> leftSmallerThanRight(listOf(left), right)
            left is List<*> && right is List<*> -> {
                if (left.isEmpty() && right.isNotEmpty()) return true // left smaller than right and no value in left: true
                left.forEachIndexed { index, l ->
                    val r = right.getOrNull(index) ?: return false
                    leftSmallerThanRight(l!!, r)?.let { return it }
                }
                if (left.size < right.size) return true
                null // both lists are equal, continue with next
            }
            else -> error("BAD DATA!!!1 left: ${left::class.simpleName} / right: ${right::class.simpleName}")
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun of(line: String) =
            Packet((buildPacketItem(IteratorWithPeek(line.toList())) as List<Any>).first() as List<Any>)

        // I don't like parsing nested lists, but I think I got this pretty clean!
        // This does add an extra outer list but we can just flatten that out again -_-
        private fun buildPacketItem(iterator: IteratorWithPeek<Char>): Any = buildList {
            while (iterator.hasNext()) {
                when (val c = iterator.next()) {
                    '[' -> add(buildPacketItem(iterator))
                    ']' -> return this
                    ',' -> { /* this happens after a list is added and more data is available. I think ignore is the thing to do. */
                    }
                    else -> { // not checking if it is actually a digit, trusting the input.
                        val sb = StringBuilder("$c")
                        while (iterator.peek().isDigit()) {
                            sb.append(iterator.next())
                        }
                        add(sb.toString().toInt())
                    }
                }
            }
        }
    }
}