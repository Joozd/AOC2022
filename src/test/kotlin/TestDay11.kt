import day11.Day11
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay11 {
    private val d = Day11()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
        assertEquals<Any?>(b1, d.answer2(t1))
    }

    private val t1 = """Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1""".lines()

    private val a1 = 10605L
    private val b1 = 2713310158L
}