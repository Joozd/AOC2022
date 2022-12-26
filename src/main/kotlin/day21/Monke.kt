package day21

abstract class Monke {
    var isHuman = false

    protected abstract fun shout(): Long?

    abstract fun requiredHumanShout(wantedResult: Long): Long

    operator fun invoke() = if (isHuman) null else shout()
}