package common.extensions

/**
 * Fills empty spaces at end of lines with whitespace
 */
fun Array<CharArray>.rotateLeft() =
    Array(this.firstOrNull()?.size ?: 0)
    { x ->
        CharArray(this.size){ y->
            this.getOrNull(y)?.getOrNull(x) ?: ' '
        }
    }

