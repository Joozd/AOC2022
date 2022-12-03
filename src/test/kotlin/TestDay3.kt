import day03.Day3
import kotlin.test.Test
import kotlin.test.assertEquals

class TestDay3 {
    @Test
    fun test1(){
        assertEquals(a1, Day3().answer1(testInput))
    }

    @Test
    fun test2(){
        assertEquals(a2, Day3().answer2(testInput))
    }

    private val testInput = """vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw""".lines()

    private val a1 = 157
    private val a2 = 70
}



