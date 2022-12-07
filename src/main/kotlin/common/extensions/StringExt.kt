package common.extensions

fun String.grabInts(): List<Int> = buildList{
    val i = this@grabInts.iterator()
    var current: Int? = null
    var sign = 1
    while(i.hasNext()){
        val c = i.next()
        when {
            current == null && c == '-' -> sign = -1

            !c.isDigit() -> { // add current number
                current?.let {
                    add(it * sign)
                    current = null
                }
                sign = 1
            }

            //c is digit
            else -> current = (current ?: 0) * 10 + c.digitToInt()
        }
    }
    current?.let {
        add(it * sign)
        sign = 1
        current = null
    }
}

fun String.grabFirstIntOrNull(): Int? {
    val i = iterator()
    var current: Int? = null
    var sign = 1
    while(i.hasNext()){
        val c = i.next()
        when {
            current == null && c == '-' -> sign = -1

            !c.isDigit() -> {
                current?.let {
                    return it * sign
                }
                sign = 1 // if a '-' was encountered somewhere else it has to be reset if it wasn't part of a number
            }

            //c is digit
            else -> current = (current ?: 0) * 10 + c.digitToInt()
        }
    }
    return null
}

/**
 * Get last word from a String: "I am a banana!" returns "banana!"
 * Only looks at ' ' character to determine if word ends/starts
 */
fun String.lastWord(): String{
    (this.length-1).downTo(0).forEach{
        if(this[it] == ' ') return drop(it+1)
    }
    return this
}

fun String.words() = split(' ').filter { it.isNotBlank() }