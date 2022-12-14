import day14.Day14
import org.junit.jupiter.api.Test
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

    private val t1 = """498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9""".lines()

    private val a1 = 24
    private val b1 = 93
}