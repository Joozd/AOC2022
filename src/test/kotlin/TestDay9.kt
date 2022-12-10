import day09.Day9
import org.junit.jupiter.api.Test
import kotlin.system.measureNanoTime
import kotlin.test.assertEquals

class TestDay9 {
    private val d = Day9()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
    }

    @Test
    fun test2() {
        assertEquals<Any?>(b1, d.answer2(t1))
        assertEquals<Any?>(a2, d.answer2(t2))
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
        val repeats = 10000 // 10000
        measureNanoTime {
            repeat(repeats){
                d.answer2()
            }
        }.let{
            println("2: ${String.format("%.5f", it.toDouble()/repeats / 1000000)} ms") // / 1000000 is to go from nanos to millis
        }
    }


    private val t1 = """R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2""".lines()
    private val a1 = 13
    private val b1 = 1

    private val t2 = """R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20""".lines()
    private val a2 = 36
}