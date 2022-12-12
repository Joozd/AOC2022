import day12.Day12
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay12 {
    private val d = Day12()

    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
        assertEquals<Any?>(b1, d.answer2(t1))
    }

    private val t1 = """Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi""".lines()
    private val a1 = 31
    private val b1 = 29
}