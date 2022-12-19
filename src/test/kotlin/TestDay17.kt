import day17.Day17
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay17 {
    val d = Day17()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
    }

    @Test
    fun test2() {
       assertEquals<Any?>(b1, d.answer2(t1))
    }

    private val t1 = """>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>""".lines()
    private val a1 = 3068
    private val b1 = 1707
}