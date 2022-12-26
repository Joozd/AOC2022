package day21

import common.Solution

class Day21: Solution(21) {
    private val monkeys = HashMap<String, Monke>()
    override fun answer1(input: List<String>): Long? {
        input.forEach {
            monkeys[it.take(4)] = make(it)
        }
        return monkeys["root"]!!()
    }

    override fun answer2(input: List<String>): Long {
        monkeys["humn"]!!.isHuman = true

        //if left minus right == 0, it is equal. Both my input and test have '+' for root so I can easily replace that wit '-'.
        monkeys["root"] = make(input.first { it.startsWith("root") }.replace('+', '-'))
        return monkeys["root"]!!.requiredHumanShout(0L)
    }

    fun make(line: String): Monke = object: Monke(){
        private val w = line.split(" ")
        override fun shout(): Long? {
            return if (w.size == 2) w[1].toLong()
            else {
                val l = monkeys[w[1]]!!()
                val r = monkeys[w[3]]!!()
                when {
                    l == null || r == null -> null
                    w[2] == "-" -> l - r
                    w[2] == "+" -> l + r
                    w[2] == "*" -> l * r
                    w[2] == "/" -> l / r
                    else -> error("A U NEE")
                }
            }
        }

        override fun requiredHumanShout(wantedResult: Long): Long {
            if (isHuman) return wantedResult
            val l = monkeys[w[1]]!!
            val r = monkeys[w[3]]!!
            val left = l()
            val right = r()
            require((left == null || right == null) && !(left== null && right == null))

            return when(w[2]){
                "+" -> if (left == null) l.requiredHumanShout(wantedResult - right!!) else r.requiredHumanShout(wantedResult - left)
                "-" -> if (left == null) l.requiredHumanShout(wantedResult + right!!) else r.requiredHumanShout(left - wantedResult)
                "*" -> if (left == null) l.requiredHumanShout(wantedResult / right!!) else r.requiredHumanShout(wantedResult / left)
                "/" -> if (left == null) l.requiredHumanShout(wantedResult * right!!) else r.requiredHumanShout(left / wantedResult)

                else -> error("A U NEE")
            }
        }
    }
}