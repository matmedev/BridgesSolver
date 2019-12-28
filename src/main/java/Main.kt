import aima.core.search.framework.problem.Problem
import aima.core.search.framework.qsearch.GraphSearch
import aima.core.search.uninformed.DepthFirstSearch
import model.Node
import model.Table

fun getStartStateFromFile(path: String): String {
    return object {}.javaClass.getResource(path).readText()
            .replace(" ", "")
            .replace("\n", "")
}

fun getTableFromString(str: String, size: Int): Table {
    val chars = str.toCharArray()
    val nodes = Array(size) { Array(size) { Node(0, mutableListOf()) } }
    for (y in 0 until size) {
        for (x in 0 until size) {
            nodes[x][y] = Node(chars[x * size + y].toString().toInt(), mutableListOf())
        }
    }
    return Table(nodes)
}

fun main() {
    val table = getTableFromString(getStartStateFromFile("easy-game1.txt"), 4)

    val problem = Problem(table, BridgesActionsFunction(), BridgesResultFunction(), BridgesGoalTest())
    val search = DepthFirstSearch(GraphSearch())

    println(search.findActions(problem))
}

// ‖
// =
// |
// -
