import common.linearalgebra.IntVector
import common.linearalgebra.IntVectorMatrix
import kotlin.test.Test
import kotlin.test.assertEquals

class LinearAlgebraTests {
    @Test
    fun testMatrixMultiplication(){
        // make matrix:
        // [ 0 1 ]
        // [ 1 0 ]
        val matrix1 = IntVectorMatrix.ofRowVectors(IntVector(0,1), IntVector(1,0))
        // multiplying by this should reverse a vector, means it gets rotated 90 degrees left
        val v1 = IntVector(3,7)
        assertEquals(IntVector(7,3), v1 * matrix1)
    }

    @Test
    fun testCrossMultiplication(){
        val a = IntVector(1,0,0)
        val b = IntVector(0,1,0)
        val c = IntVector(0,0,1)
        assertEquals(c, a cross b)
        assertEquals(c.inverse(), b cross a)
    }
}