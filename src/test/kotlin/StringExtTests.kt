import common.extensions.grabInts
import common.extensions.lastWord
import common.extensions.words
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