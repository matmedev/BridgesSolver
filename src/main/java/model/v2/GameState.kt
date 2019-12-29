package model.v2

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
        val count = bridges.count { it == q }
        return count < 2
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
}
