import common.CharArrayWindow2D
import kotlin.test.Test

class CharArrayWindowTest {
    @Test
    fun testWindow(){
        val array = arrayOf(
            "123456789".toCharArray(),
            "abcdefghi".toCharArray(),
            "ABCDEFGHI".toCharArray()
        )

        println(CharArrayWindow2D(array, 0..2, 3..4))

    }
}