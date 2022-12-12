package day11

import common.extensions.grabFirstIntOrNull
import common.extensions.grabInts
import common.extensions.words
import java.util.*

class Monke(val items: LinkedList<Long>, val op: Operation, val modulo: Int, val targetModuloMatches: Int, val targetModuloNoMatch: Int) {
    fun interface Operation{
        operator fun invoke(old: Long): Long
    }

    companion object{
        fun of(monkeString: String, divide: Boolean): Monke{
            val monke = monkeString.lines()
            val items = LinkedList(monke[1].grabInts().map{it.toLong()})
            val opOperation = monke[2].words()[4].first()
            val opValue = monke[2].grabFirstIntOrNull()?.toLong()
            val modulo = monke[3].grabFirstIntOrNull()!!
            val targetModuloMatches = monke[4].grabFirstIntOrNull()!!
            val targetModuloNoMatch = monke[5].grabFirstIntOrNull()!!

            val op = if (opOperation == '+')
                Operation { old ->
                    (old + (opValue ?: old)) / if(divide) WORRY_DECREASE_FACTOR else 1
                }
            else // only + or *
                Operation { old ->
                    (old * (opValue ?: old)) / if(divide) WORRY_DECREASE_FACTOR else 1
                }

            return Monke(items, op, modulo, targetModuloMatches, targetModuloNoMatch)
        }

        private const val WORRY_DECREASE_FACTOR = 3
    }
}