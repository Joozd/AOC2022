import day15.Day15
import org.junit.jupiter.api.Test
import kotlin.system.measureNanoTime
import kotlin.test.assertEquals

class TestDay15 {
    @Test
    fun test1() {
        val d = Day15()
        d.wantedLine = l1
        assertEquals<Any?>(a1, d.answer1(t1))
        //assertEquals<Any?>(b1, d.answer2(t1))
    }

    @Test
    fun test2() {
        val d = Day15()
        d.wantedLine = l1 // needed for answer1() which is needed to fill caches
        d.maxRange = 20
        d.answer1(t1) // fill caches
        assertEquals<Any?>(b1, d.answer2(t1))
    }

    @Test
    fun benchmark1(){
        val d = Day15()
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
        val d = Day15()
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

    private val t1 = """Sensor at x=2, y=18: closest beacon is at x=-2, y=15
Sensor at x=9, y=16: closest beacon is at x=10, y=16
Sensor at x=13, y=2: closest beacon is at x=15, y=3
Sensor at x=12, y=14: closest beacon is at x=10, y=16
Sensor at x=10, y=20: closest beacon is at x=10, y=16
Sensor at x=14, y=17: closest beacon is at x=10, y=16
Sensor at x=8, y=7: closest beacon is at x=2, y=10
Sensor at x=2, y=0: closest beacon is at x=2, y=10
Sensor at x=0, y=11: closest beacon is at x=2, y=10
Sensor at x=20, y=14: closest beacon is at x=25, y=17
Sensor at x=17, y=20: closest beacon is at x=21, y=22
Sensor at x=16, y=7: closest beacon is at x=15, y=3
Sensor at x=14, y=3: closest beacon is at x=15, y=3
Sensor at x=20, y=1: closest beacon is at x=15, y=3""".lines()

    private val l1 = 10
    private val a1 = 26
    private val b1 = 56000011L
}