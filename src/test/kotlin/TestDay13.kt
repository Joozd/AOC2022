import day13.Day13
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay13 {
    private val d = Day13()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
        assertEquals<Any?>(b1, d.answer2(t1))
    }

    private val t1 = """[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]""".lines()

    private val a1 = 13
    private val b1 = 140
}