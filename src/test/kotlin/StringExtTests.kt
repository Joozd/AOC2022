import common.extensions.grabInts
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StringExtTests {
    @Test
    fun testGrabInts(){
        val t1 = "hallo123 -4 something something 3-4=-1"
        val e1 = listOf(123, -4, 3, 4, -1)

        val t2 = "86-94,73-99"
        val e2 = listOf(86,94,73,99)

        assertEquals(e1, t1.grabInts())
        assertEquals(e2, t2.grabInts())
    }
}