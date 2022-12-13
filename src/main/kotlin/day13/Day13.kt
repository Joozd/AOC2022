package day13

import common.Solution

class Day13: Solution(13) {
    lateinit var packets: List<Packet>

    override fun answer1(input: List<String>) = sumOfCorrectOrderIndices(input)

    //answer1 needs to run first for cached packets
    override fun answer2(input: List<String>) = locationOfDividerPackets()

    private fun sumOfCorrectOrderIndices(input: List<String>): Int{
        initializePackets(input)
        return (packets.indices step 2).sumOf { i ->
            if (packets[i + 1] > packets[i]) i / 2 + 1 else 0
        }
    }

    private fun initializePackets(input: List<String>) {
        packets = input.filter { it.isNotBlank() }.map {
            Packet.of(it)
        }
    }

    private fun locationOfDividerPackets(): Int {
        val divider1 = Packet.of(DIVIDER_PACKET_1)
        val divider2 = Packet.of(DIVIDER_PACKET_2)
        val sorted = (packets + listOf(divider1, divider2)).sorted()
        return (sorted.indexOf(divider1) + 1) * (sorted.indexOf(divider2) + 1)
    }

    companion object{
        private const val DIVIDER_PACKET_1 = "[[2]]"
        private const val DIVIDER_PACKET_2 = "[[6]]"
    }
}