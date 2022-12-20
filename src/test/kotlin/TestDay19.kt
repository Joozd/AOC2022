import day19.Day19
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay19{
    val d = Day19()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
    }

    @Test
    fun test2() {
        assertEquals<Any?>(b1, d.answer2(t1))
    }

    private val t1 = """Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian
Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.""".lines()
    private val a1 = 33
    private val b1 = 56*62
}