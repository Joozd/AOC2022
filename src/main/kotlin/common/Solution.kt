package common

import java.io.File
import kotlin.system.measureNanoTime

abstract class Solution(private val day: Int) {
    protected val inputFile by lazy { inputLines.joinToString("\n") } // to prevent ambiguous newlines. It's always just '\n' this way.
    protected val inputLines by lazy { inputLines() }

    abstract fun answer1(): Any?

    abstract fun answer2(): Any?

    operator fun invoke() {
        println("Answers for day $day:\n1:\n${answer1()}\n\n2:\n${answer2()}")
    }

    fun runTimed() {
        println("Answers for day $day:")
        val a1: Any?
        val a2: Any?
        val preparationDuration = measureNanoTime { prepare() }
        val duration1 = measureNanoTime { a1 = answer1() }
        println("1:\n$a1\n\n")
        val duration2 = measureNanoTime { a2 = answer2() }
        println("2:\n$a2\n\n")
        val durationTotal = preparationDuration +duration1 + duration2
        println("Preparation: ${preparationDuration/1000000.toDouble()} ms")
        println("time 1:      ${duration1/1000000.toDouble()} ms")
        println("time 2:      ${duration2/1000000.toDouble()} ms")
        println("total time:  ${durationTotal/1000000.toDouble()} ms")
    }

    /**
     * This will run before answers
     * usually used to read input from file to memory
     * Standard implementation just initializes lazy values.
     */
    protected open fun prepare(){
        inputFile  // initialized lazy, this also initializes inputLines as that is called from the lazy init.
    }

    private  fun inputLines() = readInput().lines()

    // private because no protection from ambiguos newLines.
    // use [inputFile] or [inputLines]
    private fun readInput() = File("inputs\\$day.txt").readText()
}