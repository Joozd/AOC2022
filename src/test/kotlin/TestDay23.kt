import day23.Day23
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay23 {
    val d = Day23()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t2))
    }

    @Test
    fun test2() {
        d.answer1(t2) // first 10 rounds are done in answer1
        assertEquals<Any?>(a2, d.answer2(t1))
    }

    private val t1 = """
.....
..##.
..#..
.....
..##.
.....""".lines().drop(1)


    private val t2 = """
....#..
..###.#
#...#.#
.#...##
#.###..
##.#.##
.#..#..""".lines().drop(1)

    private val a1 = 110
    private val a2 = 20
}