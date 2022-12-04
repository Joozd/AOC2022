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
                    sign = 1
                    current = null
                }
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