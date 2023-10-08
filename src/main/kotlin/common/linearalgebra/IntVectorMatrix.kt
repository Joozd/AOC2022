package common.linearalgebra

class IntVectorMatrix(vararg columnVectors: IntVector) {
    init{
        require(
            columnVectors.isEmpty() ||
            columnVectors.all{
                it.size == columnVectors[0].size
            }
        ) { "Vectors in matrix must be the same length"}
    }
    private val matrix = columnVectors

    private val length = matrix.getOrNull(0)?.size ?: 0

    private val rowVectors: Array<IntVector> =
        if (matrix.isEmpty()) emptyArray()
    else {
        matrix.first().indices.map { i ->
            IntVector(matrix.map{it[i]})
        }.toTypedArray()
    }


    /**
     * Multiply this matrix with an IntVector
     */
    operator fun times(vector: IntVector): IntVector{
        require(vector.size == matrix.size) { "Canot multiply vector with length${vector.size} with matrix with ${matrix.size} vectors; must be equal."}
        if(length == 0) return IntVector() // multiplying an empty vector with an empty matrix, or a matrix with empty vectors, gives another empty vector
        return IntVector(rowVectors.map { it * vector })
    }

    companion object{

        // Getting the rowVectors from the rowVectors of a matrix rotates them straight back to column vectors
        fun ofRowVectors(vararg rowVectors: IntVector): IntVectorMatrix =
            IntVectorMatrix(*IntVectorMatrix(*rowVectors).rowVectors)
    }
}