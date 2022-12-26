import day21.Day21
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay21 {
    val d = Day21()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
    }

    @Test
    fun test2() {
        d.answer1(t1) // fill cache
        assertEquals<Any?>(b1, d.answer2(t1))
    }

    private val t1 = """root: pppw + sjmn
dbpl: 5
cczh: sllz + lgvd
zczc: 2
ptdq: humn - dvpt
dvpt: 3
lfqf: 4
humn: 5
ljgn: 2
sjmn: drzm * dbpl
sllz: 4
pppw: cczh / lfqf
lgvd: ljgn * ptdq
drzm: hmdt - zczc
hmdt: 32""".lines()
    private val a1 = 152L
    private val b1 = 301L
}