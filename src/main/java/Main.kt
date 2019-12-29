import model.v2.Bridge
import model.v2.GameState
import java.util.*

fun getStartStateFromFile(path: String): String {
    return object {}.javaClass.getResource(path).readText().replace("\n", "")
}

fun getBoardFromString(str: String, size: Int): Array<Array<Int>> {
    val chars = str.toCharArray()
    val board = Array(size) { Array(size) { 0 }}
    for (y in 0 until size) {
        for (x in 0 until size) {
            board[x][y] = chars[x * size + y].toString().toInt()
        }
    }
    return board
}

fun main() {
    val board = getBoardFromString(getStartStateFromFile("easy-game1.txt"), 4)

    val initialState = GameState(board)

    val stack = Stack<GameState>()
    stack.push(initialState)

    var solution: GameState? = null

    while(!stack.isEmpty()) {
        val current = stack.pop()

        if(current.isTerminal()) {
            solution = current
            break
        }

        val nextStates = HashSet<GameState>()
        current.board.forEachIndexed { y, row ->
            row.forEachIndexed { x, node ->
                if (node != 0) {
                    current.getNeighbours(x, y).forEach { pos ->
                        val nextBridges = mutableListOf<Bridge>()
                        nextBridges.addAll(current.bridges)
                        val nextState = GameState(board = cloneBoard(current.board), bridges = nextBridges)
                        nextState.board[y][x]--
                        nextState.board[pos.y][pos.x]--
                        nextState.bridges.add(Bridge(x, y, pos.x, pos.y))
                        nextStates.add(nextState)
                    }
                }
            }
        }
        nextStates.forEach {
            stack.push(it)
        }
    }

    if(solution !== null) {
        println("Game is solvable using the following steps:")
        solution.bridges.forEach {
            println(" - ${it.from} === ${it.to}")
        }
    } else {
        println("Game could not be solved")
    }
}

fun cloneBoard(original: Array<Array<Int>>): Array<Array<Int>> {
    val clone = Array(original.size) { Array(original.size) { 0 }}
    for((y, row) in original.withIndex()) {
        for ((x, node) in row.withIndex()) {
            clone[y][x] = node
        }
    }
    return clone
}
