package model

data class Node(
   val value: Int,
   var bridges: MutableList<Edge>
) {

    fun getBridgesRemaining(): Int {
        return value - bridges.size
    }

    fun isFinished(): Boolean {
        return getBridgesRemaining() == 0
    }
}
