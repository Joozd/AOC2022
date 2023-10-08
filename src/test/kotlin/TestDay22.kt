import day22.Day22
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay22 {
    val d = Day22()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
    }

    @Test
    fun test2() {
        assertEquals<Any?>(a2, d.answer2(t1))
    }

    private val t1 = """
        ...#
        .#..
        #...
        ....
...#.......#
........#...
..#....#....
..........#.
        ...#....
        .....#..
        .#......
        ......#.

10R5L5R10L4R5L5""".lines().drop(1) // first line is empty for readability
    private val a1 = 6032
    private val a2 = 5031
}