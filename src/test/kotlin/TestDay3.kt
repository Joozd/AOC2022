import day03.Day3
import kotlin.system.measureNanoTime
import kotlin.test.Test
import kotlin.test.assertEquals

class TestDay3 {
    @Test
    fun test1(){
        assertEquals(a1, Day3().answer1(testInput))
    }

    @Test
    fun benchmark1(){
        val d = Day3()
        val repeats = 100000
        measureNanoTime {
            repeat(repeats){
                d.answer1()
            }
        }.let{
            println("1: ${String.format("%.3f", it.toDouble()/repeats / 1000000)} ms")
        }
    }

    @Test
    fun test2(){
        assertEquals(a2, Day3().answer2(testInput))
    }

    @Test
    fun benchmark2(){
        val d = Day3()
        val repeats = 10000
        measureNanoTime {
            repeat(repeats){
                d.answer2()
            }
        }.let{
            println("2: ${String.format("%.3f", it.toDouble()/repeats / 1000000)} ms")
        }
    }

    private val testInput = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw""".lines()

    private val a1 = 157
    private val a2 = 70
}



