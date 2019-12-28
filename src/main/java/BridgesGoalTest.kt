import aima.core.search.framework.problem.GoalTest
import model.Table

class BridgesGoalTest: GoalTest {
    override fun isGoalState(state: Any?): Boolean {
        val goal = (state as Table).isGoal()
        println("is goal ? $goal")
        return goal
    }

}
