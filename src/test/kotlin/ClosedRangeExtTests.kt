import common.extensions.overlaps
import common.extensions.size
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ClosedRangeExtTests {


    @Test
    fun testOverlaps(){
        val ref = 1..5
        val t1 = 4..10
        val t2 = 5..10
        val t3 = 2..3
        val t4 = 0..1

        val f1 = -3..0
        val f2 = 6..8

        assert(ref overlaps t1)
        assert(ref overlaps t2)
        assert(ref overlaps t3)
        assert(ref overlaps t4)

        assertFalse(ref overlaps f1)
        assertFalse(ref overlaps f2)
    }

    @Test
    fun testSize(){
        val t1 = 1..3
        val t2 = -2..1

        assertEquals(t1.count(), t1.size)
        assertEquals(t2.count(), t2.size)
    }

}