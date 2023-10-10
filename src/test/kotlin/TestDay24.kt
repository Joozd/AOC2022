import day24.Day24
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay24 {
    val d = Day24()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
    }

    @Test
    fun test2() {
        d.answer1(t1) // first lap is done in answer 1
        assertEquals<Any?>(a2, d.answer2(t1))
    }

    private val t1 = """
#.######
#>>.<^<#
#.<..<<#
#>v.><>#
#<^v^^>#
######.#""".lines().drop(1)


    private val a1 = 18
    private val a2 = 54
}
