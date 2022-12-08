import day08.Day8
import org.junit.jupiter.api.Test
import kotlin.system.measureNanoTime
import kotlin.test.assertEquals

class TestDay8 {
    private val d = Day8()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
        assertEquals<Any?>(b1, d.answer2(t1)) // both in one test because a1 needs to run before a2 for caching
    }

    @Test
    fun benchmark1(){
        val repeats = 10000 // 10000
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
        d.answer1() // fill cache
        val repeats = 10000 // 10000
        measureNanoTime {
            repeat(repeats){
                d.answer2()
            }
        }.let{
            println("2: ${String.format("%.5f", it.toDouble()/repeats / 1000000)} ms") // / 1000000 is to go from nanos to millis
        }
    }

    private val t1 = """30373
25512
65332
33549
35390""".lines()
    private val a1 = 21
    private val b1 = 8
}