
import day07.Day7
import org.junit.jupiter.api.Test
import kotlin.system.measureNanoTime
import kotlin.test.assertEquals

class TestDay7 {
    private val d = Day7()
    @Test
    fun test1(){
        assertEquals<Any?>(a1, d.answer1(t1))

    }

    @Test
    fun test2(){
        assertEquals<Any?>(b1, d.answer2(t1))
    }

    @Test
    fun benchmark1(){
        val repeats = 1 // 10000
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
        val repeats = 1 // 10000
        measureNanoTime {
            repeat(repeats){
                d.answer2()
            }
        }.let{
            println("2: ${String.format("%.3f", it.toDouble()/repeats / 1000000)} ms") // / 1000000 is to go from nanos to millis
        }
    }

    val t1= """insert_test_data_here""".lines()
    val a1 = 42

    val b1 = 42
}