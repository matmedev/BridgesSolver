package model.v2

data class Position (var x: Int, var y: Int) {
    override fun toString(): String {
        return "(${x+1}, ${y+1})"
    }
}
