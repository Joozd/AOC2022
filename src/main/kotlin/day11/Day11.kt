package day11

import common.Solution

class Day11: Solution(11) {
    override fun answer1(input: List<String>) = calculateMonkeyBusiness(inputAsOne(input), 20)

    override fun answer2(input: List<String>) = calculateMonkeyBusiness(inputAsOne(input), 10000, false)

    private fun calculateMonkeyBusiness(input: String, repeats: Int, divide: Boolean = true): Long{
        val monkeys = input.split("\n\n").map{ Monke.of(it, divide) }

        val uberMod = monkeys.map{it.modulo}.reduce { acc, i -> acc * i }
        val counts = monkeys.associateWith { 0L }.toMutableMap()
        repeat(repeats){
            monkeys.forEach { monke ->
                with(monke) {
                    while (items.isNotEmpty()) {
                        val item = items.removeFirst()
                        val currentItem = op(item) % uberMod
                        counts[this] = counts[this]!! + 1
                        val target = if (currentItem % modulo == 0L) targetModuloMatches else targetModuloNoMatch
                        monkeys[target].items.add(currentItem)
                    }
                }
            }
        }
        return counts.values.sortedDescending().take(2).let{
            it[0] * it[1]
        }
    }
}