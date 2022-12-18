import day16.Day16
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDay16 {
    val d = Day16()
    @Test
    fun test1() {
        assertEquals<Any?>(a1, d.answer1(t1))
    }

    @Test
    fun test2() {
        assertEquals<Any?>(b1, d.answer2(t1))
    }

    private val t1 = """Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II""".lines()
    private val a1 = 1651
    private val b1 = 1707

    private val t2 = """Valve AA has flow rate=0; tunnels lead to valve BB
Valve BB has flow rate=1; tunnels lead to valves AA, CC
Valve CC has flow rate=100; tunnels lead to valve BB""".lines()
    private val a2 = 27*100 + 25*1
    private val b2 = 22*100 + 24*1

    private val t3 = """Valve AA has flow rate=0; tunnels lead to valves BB
Valve BB has flow rate=1; tunnels lead to valves AA, CC
Valve CC has flow rate=0; tunnels lead to valves BB, DD
Valve DD has flow rate=100; tunnels lead to valves CC""".lines()
    private val a3 = 25*100 + 28*1
    private val b3 = 22*100 + 24*1


}