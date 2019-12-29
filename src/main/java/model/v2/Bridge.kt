package model.v2

data class Bridge(
        val from: Position,
        val to: Position
) {
    constructor(fromX: Int, fromY: Int, toX: Int, toY: Int) : this(Position(fromX, fromY), Position(toX, toY))

    override fun hashCode(): Int {
        return from.hashCode() * to.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bridge
        return (other === this) || (
                from.x == other.from.x &&
                        from.y == other.from.y &&
                        to.x == other.to.x &&
                        to.y == other.to.y
                ) || (
                from.x == other.to.x &&
                        from.y == other.to.y &&
                        to.x == other.from.x &&
                        to.y == other.from.y
                )
    }
}
