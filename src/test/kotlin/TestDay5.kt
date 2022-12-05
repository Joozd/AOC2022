import day05.Day5
import org.junit.jupiter.api.Test
import kotlin.system.measureNanoTime
import kotlin.test.assertEquals

class TestDay5 {
    private val d = Day5()
    @Test
    fun test1(){
        assertEquals(a1, d.answer1(t1))
        assertEquals(a2, d.answer2(t1))
    }

    @Test
    fun benchmark1(){
        val repeats = 100000
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
        val repeats = 100000
        d.answer1() // fill caches
        measureNanoTime {
            repeat(repeats){
                d.answer2()
            }
        }.let{
            println("2: ${String.format("%.3f", it.toDouble()/repeats / 1000000)} ms") // / 1000000 is to go from nanos to millis
        }
    }

    private val t1 = """    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2""".lines()
    private val a1 = "CMZ"
    private val a2 = "MCD"
}