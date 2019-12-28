import aima.core.agent.Action
import aima.core.search.framework.problem.ResultFunction
import model.Table

class BridgesResultFunction: ResultFunction {
    override fun result(state: Any?, action: Action?): Table {
        val table = state as Table
        val bAction = action as BridgesAction
        val startNode = table.nodes[bAction.x][bAction.y]
        val endNode = table.getNeighbour(bAction.x, bAction.y, bAction.direction)
                ?: throw UnsupportedOperationException("${bAction.x}:${bAction.y} island has no neighbour to the ${bAction.direction} direction")
        startNode.bridges.add(bAction.direction)
        endNode.bridges.add(bAction.direction.getOppositeEdge())

        println("(${action.x} ${action.y}; ${action.direction})\n$table")
        return table
    }
}
