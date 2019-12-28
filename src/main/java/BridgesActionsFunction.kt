import aima.core.search.framework.problem.ActionsFunction
import model.Edge
import model.Node
import model.Table

class BridgesActionsFunction: ActionsFunction {

    override fun actions(state: Any?): MutableSet<BridgesAction> {
        val table = state as Table
        val actions = HashSet<BridgesAction>()
        val nodesChanged = HashSet<Node>()
        table.nodes.forEachIndexed { x, row ->
            row.forEachIndexed { y, node ->
                if (!node.isFinished() && !nodesChanged.contains(node)) {
                    Edge.DIRECTIONS.forEach { direction ->
                        val neighbour = table.getNeighbour(x, y, Edge(direction))
                        if(neighbour != null && !neighbour.isFinished() && !nodesChanged.contains(neighbour)) {
                            nodesChanged.add(node)
                            nodesChanged.add(neighbour)
                            actions.add(BridgesAction(x, y, Edge(direction)))
                        }
                    }
                }
            }
        }

        println("Actions found: ${actions.size}")
        actions.forEach { action -> println(action) }
        readLine()

        return actions
    }
}
