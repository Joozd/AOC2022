package day05

import common.Solution
import common.extensions.grabInts
import java.util.*

/**
 * Today's optimization was only in part 2.
 * Initially, I cut and pasted and replaced the entire list:
 * {@code (to = to + from.takeLast(n); from = from.droplast(n))}
 * Changing that to the same implementation as answer 1 (removeLast, but with a little stack in between) was about
 * 3x faster.
 * I picked LinkedList, which is slightly faster than ArrayList for this situation;
 * changing the little stack in q2 to an ArrayList with predefined size was slower than LinkedList as well.
 */
class Day5: Solution(5) {
    //cached values
    private lateinit var initialStacks: List<List<Char>>
    private lateinit var operations: List<String>

    override fun answer1(input: List<String>): String {
        // Split, cache and parse input data
        val separatorLine = input.indexOfFirst { it.startsWith(" 1") }
        operations = input.drop(separatorLine + 2)
        initialStacks = buildStacks(input.take(separatorLine))

        return rearrangeStacks(initialStacks, operations).joinToString("") { "${it.last()}" }
    }

    //not using [input], only cached data, so answer1() needs to run first.
    override fun answer2(input: List<String>): String =
        rearrangeStacks2(initialStacks, operations).joinToString("") { "${it.last()}" }


    private fun buildStacks(input: List<String>): List<ArrayList<Char>> =
        (0..(input.last().length / 4)).map { stackIndex ->
            ArrayList<Char>(input.size).apply {
                ((input.size - 1).downTo(0)).forEach { line ->
                    input[line].getOrNull(1 + stackIndex * 4)?.takeIf { it != ' ' }?.let {
                        add(it)
                    }
                }
            }
        }

    // One at a time, like the crane does.
    // This gets the answer in about 0.057ms
    private fun rearrangeStacks(stacks: List<List<Char>>, operations: List<String>): List<List<Char>> {
        val looseStacks = stacks.map { LinkedList(it) }
        operations.forEach { op ->
            op.grabInts().let{ i ->
                val amount = i[0]
                val from = i[1] - 1 // stacks are numbered starting at 1, indices start at 0
                val to = i[2] - 1
                repeat(amount) {
                    looseStacks[to].add(looseStacks[from].removeLast())
                }
            }
        }
        return looseStacks
    }

    // Crane picks up big stacks, but one at a time via a temp stack is faster on my computer
    // This gets the answer in about 0.072ms
    private fun rearrangeStacks2(stacks: List<List<Char>>, operations: List<String>): List<List<Char>> {
        val looseStacks = stacks.map { LinkedList(it) }
        operations.forEach { op ->
            op.grabInts().let{ i ->
                val amount = i[0]
                val from = i[1] - 1 // stacks are numbered starting at 1, indices start at 0
                val to = i[2] - 1
                val tempStack = LinkedList<Char>()
                repeat(amount){
                    tempStack.add(looseStacks[from].removeLast())
                }
                repeat(amount){
                    looseStacks[to].add(tempStack.removeLast())
                }
            }
        }
        return looseStacks
    }
}