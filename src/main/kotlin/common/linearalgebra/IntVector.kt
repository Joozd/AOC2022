package common.linearalgebra

/**
 * IntVectors are immutable.
 */
class IntVector(vararg x: Int): Iterable<Int> {
    constructor(x: List<Int>): this(*x.toIntArray())

    private val vector: IntArray = x

    val size get() = vector.size

    val indices get() = vector.indices

    fun inverse(): IntVector = IntVector(vector.map { it * -1})

    operator fun get(i: Int): Int = vector[i]

    operator fun plus(other: IntVector): IntVector {
        require(other.size == this.size) { "Can only add Vectors of same size" }
        return IntVector(*IntArray(size) { vector[it] + other[it] })
    }

    operator fun minus(other: IntVector): IntVector {
        require(other.size == this.size) { "Can only subtract Vectors of same size" }
        return IntVector(*IntArray(size) { vector[it] - other[it] })
    }

    /**
     * Dot product. For cross product use (IntVector.cross(otherIntVector)
     */
    operator fun times(other: IntVector): Int {
        require(other.size == this.size) { "Can only dot Vectors of same size" }
        return vector.indices.sumOf { vector[it] * other[it] }
    }

    /**
     * Matrix multiplication, done in [IntVectorMatrix]
     */
    operator fun times(matrix: IntVectorMatrix) = matrix * this

    /**
     * Multiply this vector by a scalar
     */
    operator fun times(scalar: Int): IntVector = IntVector(this.map { it * scalar})

    /**
     * Get the cross product of two (3-dimensional) vectors.
     */
    infix fun cross(other: IntVector): IntVector{
        require(this.size == 3 && other.size == 3)
        val newVector = IntArray(3)
        newVector[0] = vector[1] * other[2] - vector[2] * other[1] // x = ay*bz - az*by
        newVector[1] = vector[2] * other[0] - vector[0] * other[2] // y = az*bx - ax*bz
        newVector[2] = vector[0] * other[1] - vector[1] * other[0] // z = ax*by - ay*bx
        return IntVector(*newVector)
    }

    override fun equals(other: Any?) =
        if (other !is IntVector) false
        else other.vector.contentEquals(vector)

    override fun hashCode(): Int {
        return vector.contentHashCode()
    }

    override fun iterator(): Iterator<Int> = vector.iterator()

    override fun toString(): String = "[${vector.joinToString()}]"
}