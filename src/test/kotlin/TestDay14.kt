import day14.Day14
import org.junit.jupiter.api.Test
import kotlin.system.measureNanoTime
import kotlin.test.assertEquals

class TestDay14 {
    private val d = Day14()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
    }

    @Test
    fun test2(){
        assertEquals<Any?>(b1, d.answer2(t1))
    }

    @Test
    fun benchmark1(){
        val repeats = 1000 // 10000
        measureNanoTime {
            repeat(repeats){
                d.answer1()
            }
        }.let{
            println("1: ${String.format("%.3f", it.toDouble()/repeats / 1000000)} ms") // / 1000000 is to go from nanos to millis
        }
    }

    @Test
    fun benchmark2(){
        d.answer1() // fill caches
        val repeats = 1000 // 10000
        measureNanoTime {
            repeat(repeats){
                d.answer2()
            }
        }.let{
            println("2: ${String.format("%.5f", it.toDouble()/repeats / 1000000)} ms") // / 1000000 is to go from nanos to millis
        }
    }

    private val t1 = """498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9""".lines()

    private val a1 = 24
    private val b1 = 93
}