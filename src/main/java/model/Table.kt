package model

data class Table(
    var nodes: Array<Array<Node>>
) {

    init {
        println("start\n${toString()}")
    }

    fun isGoal(): Boolean {
        for (row in nodes) {
            for (node in row) {
                if (!node.isFinished()) {
                    return false
                }
            }
        }
        return true
    }

    fun getNeighbour(x: Int, y: Int, direction: Edge): Node? {
        return when(direction.direction.toUpperCase()) {
            'U' -> getNeighbourVertical(x, y, -1)
            'D' -> getNeighbourVertical(x, y, 1)
            'L' -> getNeighbourHorizontal(x, y, -1)
            'R' -> getNeighbourHorizontal(x, y, 1)
            else -> throw UnsupportedOperationException("${direction.direction} is not a valid direction")
        }
    }

    private fun getNeighbourHorizontal(x: Int, startY: Int, dir: Int): Node? {
        if (dir > 0) {
            for (y in startY+dir until nodes.size) {
                val node = getNodeIfNotNull(x, y)
                if (node != null) return node
            }
        } else {
            for (y in startY+dir downTo 0) {
                val node = getNodeIfNotNull(x, y)
                if (node != null) return node
            }
        }
        return null
    }

    private fun getNeighbourVertical(startX: Int, y: Int, dir: Int): Node? {
        if (dir > 0) {
            for (x in startX+dir until nodes[0].size) {
                val node = getNodeIfNotNull(x, y)
                if (node != null) return node
            }
        } else {
            for (x in startX+dir downTo 0) {
                val node = getNodeIfNotNull(x, y)
                if (node != null) return node
            }
        }
        return null
    }

    private fun getNodeIfNotNull(x: Int, y: Int): Node? {
        try {
            val node = nodes[x][y]
            if (node.value > 0) {
                return node
            }
            return null
        } catch (ex: ArrayIndexOutOfBoundsException) {
            return null
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Table

        if (!nodes.contentDeepEquals(other.nodes)) return false

        return true
    }

    override fun hashCode(): Int {
        return nodes.contentDeepHashCode()
    }

    override fun toString(): String {
        var str = ""
        for (row in nodes) {
            for (node in row) {
                str += "${node.getBridgesRemaining()} "
            }
            str += "\n"
        }
        return str
    }
}
