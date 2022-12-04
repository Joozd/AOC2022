import day04.Day4
import org.junit.jupiter.api.Test
import kotlin.system.measureNanoTime
import kotlin.test.assertEquals

class TestDay4 {
    private val d = Day4()
    @Test
    fun test1(){
        assertEquals(a1, d.answer1(testInput))
    }

    @Test
    fun benchmark1(){
        val repeats = 10000
        measureNanoTime {
            repeat(repeats){
                d.answer1()
            }
        }.let{
            println("1: ${String.format("%.3f", it.toDouble()/repeats / 1000000)} ms") // / 1000000 is to go from nanos to millis
        }
    }

    @Test
    fun test2(){
        assertEquals(a2, d.answer2(testInput))
    }

    @Test
    fun benchmark2(){
        val repeats = 10000
        measureNanoTime {
            repeat(repeats){
                d.answer2()
            }
        }.let{
            println("2: ${String.format("%.3f", it.toDouble()/repeats / 1000000)} ms") // / 1000000 is to go from nanos to millis
        }
    }

    private val testInput = """2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8""".lines()

    private val a1 = 2
    private val a2 = 4
}