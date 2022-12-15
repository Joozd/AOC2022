import common.grids.Coordinate
import common.grids.DiagonalLine
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse

class LineTest {
    @Test
    fun testCrossingDiagonalLines(){
        val l1 = DiagonalLine(Coordinate(1,2), Coordinate(5,-2))
        val l2 = DiagonalLine(Coordinate(0, -5), Coordinate(1, -4))

        println(l1.getIntersection(l2))

        val t1 = Coordinate(4, -1)
        val f1 = Coordinate(4,-2)


        // coordinate.x in (start.x..end.x) && coordinate.y-start.y == (coordinate.x-start.x) * direction

        assert(t1 in l1)
        assertFalse(f1 in l1)
    }
}