import common.extensions.grabFirstIntOrNull
import common.extensions.grabInts
import common.extensions.lastWord
import common.extensions.words
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StringExtTests {
    @Test
    fun testGrabInts(){
        // check basic functionality
        val t0 = "1 banana 2 banana 3 banana, go!"
        val e0 = listOf(1,2,3)

        //check if numbers in  the middle of words are grabbed
        val t1 = "hallo123 its-4banana something something 3-4=-1"
        val e1 = listOf(123, -4, 3, 4, -1)

        // check sign is correct if negative sign is used as a divider
        val t2 = "86-94,73--99"
        val e2 = listOf(86,94,73,-99)

        // check sign is correct when a negative sign is somewhere irrelevant
        val t3 = "something-somethingElse 1 yes-no -2 3 banana"
        val e3 = listOf(1,-2,3)

        assertEquals(e0, t0.grabInts())
        assertEquals(e1, t1.grabInts())
        assertEquals(e2, t2.grabInts())
        assertEquals(e3, t3.grabInts())
    }

    @Test
    fun grabFirstIntOrNull(){
        val t0 = "123"
        val e0 = 123

        val t1 = "123 extra text 456"
        val e1 = 123

        val t2 = "123,456"
        val e2 = 123

        val t3 = "something something 123"
        val e3 = 123

        val t4 = "nothing to see here"
        val e4 = null

        val t5 = "nothing (0) to see here"
        val e5 = 0

        assertEquals(e0, t0.grabFirstIntOrNull())
        assertEquals(e1, t1.grabFirstIntOrNull())
        assertEquals(e2, t2.grabFirstIntOrNull())
        assertEquals(e3, t3.grabFirstIntOrNull())
        assertEquals(e4, t4.grabFirstIntOrNull())
        assertEquals(e5, t5.grabFirstIntOrNull())
    }

    @Test
    fun testLastWord(){
        val t1 = "I am a banana!"
        val r1 = "banana!"

        val t2 = "noSpace"
        val r2 = "noSpace"

        val t3 = "tabs are\tignored"
        val r3 = "are\tignored"

        assertEquals(r1, t1.lastWord())
        assertEquals(r2, t2.lastWord())
        assertEquals(r3, t3.lastWord())
    }

    @Test
    fun wordsTest(){
        val line = "dit is een zin met woorden. Soms zit er ergens een spatie   te veel."
        // words() (split on space and discard blank strings) is a bit faster than the regex split() version.
        assertEquals(line.split("\\s+".toRegex()), line.words())
    }
}