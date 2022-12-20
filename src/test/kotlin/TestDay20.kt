import day20.Day20
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay20 {
    val d = Day20()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
    }

    @Test
    fun test2() {
        assertEquals<Any?>(b1, d.answer2(t1))
    }

    private val t1 = """1
2
-3
3
-2
0
4""".lines()
    private val a1 = 3L
    private val b1 = 1623178306L
}