package model

data class Edge(
        var direction: Char
) {

    init {
        if (DIRECTIONS.contains(direction.toUpperCase())) {
            direction = direction.toUpperCase()
        } else {
            throw UnsupportedOperationException("$direction is not a valid direction")
        }
    }

    fun getOppositeEdge(): Edge {
        return Edge(
                when (direction.toUpperCase()) {
                    'U' -> 'D'
                    'D' -> 'U'
                    'L' -> 'R'
                    'R' -> 'L'
                    else -> throw UnsupportedOperationException("$direction is not a valid direction")
                })
    }

    override fun toString(): String {
        return direction.toString()
    }

    companion object {
        val DIRECTIONS = listOf('U', 'D', 'L', 'R')
    }
}
