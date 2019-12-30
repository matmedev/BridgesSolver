package model.v2

import java.util.*

data class GameState(var board: Array<Array<Int>>, var bridges: MutableList<Bridge> = mutableListOf()) {

    fun getNeighbours(x: Int, y: Int): MutableList<Position> {
        val neighbours = mutableListOf<Position>()

        // left
        for(x1 in x-1 downTo 0) {
           if (board[y][x1] > 0) {
               neighbours.add(Position(x1, y))
               break
           }
        }
        // right
        for(x1 in x+1 until board[0].size) {
            if (board[y][x1] > 0) {
                neighbours.add(Position(x1, y))
                break
            }
        }
        // top
        for (y1 in y-1 downTo 0) {
            if (board[y1][x] > 0) {
                neighbours.add(Position(x, y1))
                break
            }
        }
        // bottom
        for (y1 in y+1 until board.size) {
            if (board[y1][x] > 0) {
                neighbours.add(Position(x, y1))
                break
            }
        }
        return neighbours
    }

    fun isBridgeValid(fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        val q = Bridge(fromX, fromY, toX, toY)
        // Checking number of bridges between the two islands
        if (hasTooManyBridges(q)) return false

        val provisionalBridges = mutableListOf<Bridge>()
        provisionalBridges.addAll(bridges)
        provisionalBridges.add(q)
        // Checking the isolation of the two islands
        if (hasIsolatedBridges(provisionalBridges)) return false

        // Checking that bridges won't cross
        if (hasCrossingBridges(provisionalBridges)) return false
        return true
    }

    fun isTerminal(): Boolean {
        for (row in board) {
            for (node in row) {
                if (node > 0) {
                    return false
                }
            }
        }
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (javaClass != other?.javaClass) return false

        other as GameState

        if(bridges.containsAll(other.bridges) && other.bridges.containsAll(bridges)) {
            return true
        }

        return false
    }

    override fun hashCode(): Int {
        return bridges.hashCode()
    }

    override fun toString(): String {
        var boardStr = ""
        for(row in board) {
            boardStr += row.joinToString("")
            boardStr += "\n"
        }
        return "GameState(bridges=$bridges, board=[\n$boardStr])"
    }

    private fun hasTooManyBridges(q: Bridge): Boolean {
        val count = bridges.count { it == q }
        if (count >= 2) {
            return true
        }
        return false
    }

    private fun hasIsolatedBridges(provisionalBridges: MutableList<Bridge>): Boolean {
        val islandsWithBridges = hashSetOf<Position>()
        for (bridge in provisionalBridges) {
            islandsWithBridges.add(bridge.from)
            islandsWithBridges.add(bridge.to)
        }
        val reachableIslands = hashSetOf<Position>()
        val islandStack = Stack<Position>()
        islandStack.push(provisionalBridges[0].from)
        reachableIslands.add(provisionalBridges[0].from)
        while (islandStack.isNotEmpty()) {
            val current = islandStack.pop()
            val connectedIslands = provisionalBridges
                    .filter { it.from == current || it.to == current }
                    .mapNotNull { bridge ->
                        when {
                            bridge.from == current -> bridge.to
                            bridge.to == current -> bridge.from
                            else -> null
                        }
                    }
            connectedIslands.forEach {
                if (!reachableIslands.contains(it)) {
                    islandStack.push(it)
                }
            }
            reachableIslands.addAll(connectedIslands)
        }
        if (islandsWithBridges.size != reachableIslands.size) {
            return true
        }
        return false
    }

    private fun hasCrossingBridges(provisionalBridges: MutableList<Bridge>): Boolean {
        // TODO Check that bridges won't cross
        return false
    }
}
