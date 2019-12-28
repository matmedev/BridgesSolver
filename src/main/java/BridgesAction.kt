import aima.core.agent.Action
import model.Edge

data class BridgesAction(
    var x: Int,
    var y: Int,
    var direction: Edge
): Action {

    override fun isNoOp(): Boolean {
        return false
    }

    override fun toString(): String {
        return "($y $x: $direction)"
    }
}
