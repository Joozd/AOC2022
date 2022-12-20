package day20

import common.Solution
import java.util.*

class Day20: Solution(20) {
    override fun answer1(input: List<String>): Long {
        val mixed = mix(input.map{ it.toLong() })
        return mixed[1000%mixed.size] + mixed[2000%mixed.size] + mixed[3000%mixed.size] // modulo needed for tests.
    }

    override fun answer2(input: List<String>): Long{
        val mixed = mix(input.map{ it.toLong() }, rounds = 10, multiplier = DECRYPTION_KEY)
        return mixed[1000%mixed.size] + mixed[2000%mixed.size] + mixed[3000%mixed.size] // modulo needed for tests.
    }


    private fun mix (input: List<Long>, rounds: Int = 1, multiplier: Long = 1L): List<Long> {
        val objects = input.map { object { val value = it * multiplier } }                  // the reference list
        val result = LinkedList(objects)                    // the list that has objects moving all over the place
        repeat(rounds) {
            objects.forEach { o ->
                val i = result.indexOf(o)
                result.remove(o)
                result.add((i + o.value).mod(result.size), o)
            }
        }
        val longs = result.map { it.value }
        val zeroIndex = longs.indexOf(0L)
        return longs.drop(zeroIndex) + longs.take(zeroIndex) // make the list start at 0
    }

    companion object{
        private const val DECRYPTION_KEY = 811589153L
    }
}