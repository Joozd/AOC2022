package common.extensions

//no check on data
fun List<String>.toInts() = map{ it.toInt() }