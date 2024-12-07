package puzzles.commons


fun isValidXAndY(position: Position, lineLength: Int, matrizLength: Int): Boolean{
    return position.x in 0..<lineLength && position.y in 0..<matrizLength
}

fun isValidXAndY(x: Int, y: Int, lineLength: Int, matrizLength: Int): Boolean{
    return x in 0..<lineLength && y in 0..<matrizLength
}

data class Position(var x: Int, var y: Int){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Position) return false

        return x == other.x && y == other.y
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}