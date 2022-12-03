import day02.Day2
import kotlin.test.Test
import kotlin.test.assertEquals

class TestDay2 {
    @Test
    fun test(){
        val s = Day2()
        assertEquals(3 + 0, s.answer2(listOf("A X"))) // A beats C
        assertEquals(1 + 3, s.answer2(listOf("A Y")))
        assertEquals(2 + 6, s.answer2(listOf("A Z"))) // B beats A
        assertEquals(1 + 0, s.answer2(listOf("B X"))) // B beats A
        assertEquals(2 + 3, s.answer2(listOf("B Y")))
        assertEquals(3 + 6, s.answer2(listOf("B Z"))) // C beats B
        assertEquals(2 + 0, s.answer2(listOf("C X"))) // C beats B
        assertEquals(3 + 3, s.answer2(listOf("C Y")))
        assertEquals(1 + 6, s.answer2(listOf("C Z"))) // A beats C
    }
}