import day06.Day6
import org.junit.jupiter.api.Test
import kotlin.system.measureNanoTime
import kotlin.test.assertEquals

class TestDay6 {
    private val d = Day6()
    @Test
    fun test1(){
        assertEquals(a1, d.answer1(listOf(t1)))
        assertEquals(a2, d.answer1(listOf(t2)))
        assertEquals(a3, d.answer1(listOf(t3)))
        assertEquals(a4, d.answer1(listOf(t4)))
        assertEquals(a5, d.answer1(listOf(t5)))
    }

    @Test
    fun test2(){
        assertEquals(b1, d.answer2(listOf(t1)))
        assertEquals(b2, d.answer2(listOf(t2)))
        assertEquals(b3, d.answer2(listOf(t3)))
        assertEquals(b4, d.answer2(listOf(t4)))
        assertEquals(b5, d.answer2(listOf(t5)))
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

    private val t1 = "mjqjpqmgbljsphdztnvjfqwrcgsmlb"
    private val t2 = "bvwbjplbgvbhsrlpgdmjqwftvncz"
    private val t3 = "nppdvjthqldpwncqszvftbrmjlhg"
    private val t4 = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"
    private val t5 = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"

    private val a1 = 7
    private val a2 = 5
    private val a3 = 6
    private val a4 = 10
    private val a5 = 11

    private val b1 = 19
    private val b2 = 23
    private val b3 = 23
    private val b4 = 29
    private val b5 = 26

}