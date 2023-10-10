import day25.Day25
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay25 {
    val d = Day25()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
    }

    @Test
    fun test2() {
        d.answer1(t1) // first lap is done in answer 1
        assertEquals<Any?>(b1, d.answer2(t1))
    }

    private val t1 = """
1=-0-2
12111
2=0=
21
2=01
111
20012
112
1=-1=
1-12
12
1=
122""".lines().drop(1)


    private val a1 = "2=-1=0"
    private val b1 = -1
}
