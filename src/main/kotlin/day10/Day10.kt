package day10

import common.Solution
import common.extensions.grabFirstIntOrNull

class Day10: Solution(10) {
    // cached as we need it in both questions
    private lateinit var signal: List<Int>

    override fun answer1(input: List<String>): Int {
        signal = makeFullSignal(input)

        return signal.mapIndexed { index, i ->
            // 1-indexed index * value, only if index in (20, 60, 100, 140, ...)
            (i * (index + 1)).takeIf { (index - 19) % 40 == 0 }
        }.filterNotNull().sum()
    }

    override fun answer2(input: List<String>) = getMessageString(signal)

    private fun makeFullSignal(input: List<String>): List<Int> =
        ArrayList<Int>(240).apply { // 0 indexed. Elf signal is 1 indexed, remember that!
            var currentStrength = 1
            input.forEach {
                if (it[0] == 'n')
                    add(currentStrength)
                else {
                    add(currentStrength)
                    add(currentStrength)
                    currentStrength += it.grabFirstIntOrNull()!!
                }
            }
        }

    private fun getMessageString(signal: List<Int>) = signal.mapIndexed { index, i ->
        // screen is 0-indexed, so we can just use i-1..i+1
        if (index%40 in i-1..i+1) '#' else ' '
    }.chunked(40).joinToString("\n") { it.joinToString("")}
}