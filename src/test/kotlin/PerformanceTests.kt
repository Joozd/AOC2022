import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class PerformanceTests {
    @Test
    fun testStringVsCharArrayAccess(){
        val s = "ABCD"
        val ca = s.toCharArray()

        val arrayAccessTime = measureTimeMillis {
            repeat(Int.MAX_VALUE) {
                val y = ca[3]
            }
        }

        val stringAccessTime = measureTimeMillis {
            repeat(Int.MAX_VALUE) {
                val x = s[3]
            }
        }



        println("Time for string access: $stringAccessTime ms")
        println("Time for array access: $arrayAccessTime ms")
    }
}