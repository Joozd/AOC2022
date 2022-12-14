import common.grids.Coordinate
import common.grids.lineTo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CoordinatesTest {
    @Test
    fun testLineTo(){
        val c1 = Coordinate(1,1)
        val h1 = Coordinate(1,3)
        val v1 = Coordinate(3,1)

        val hr = listOf(Coordinate(1,1), Coordinate(1,2), Coordinate(1,3))
        val vr = listOf(Coordinate(1,1), Coordinate(2,1), Coordinate(3,1))

        assertEquals(hr, c1.lineTo(h1))
        assertEquals(hr, h1.lineTo(c1))

        assertEquals(vr, c1.lineTo(v1))
        assertEquals(vr, v1.lineTo(c1))

    }
}