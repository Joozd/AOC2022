import day18.Day18
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay18{
    val d = Day18()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
    }

    @Test
    fun test2() {
        assertEquals<Any?>(b1, d.answer2(t1))
    }

    private val t1 = """2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5""".lines()
    private val a1 = 64
    private val b1 = 58
}