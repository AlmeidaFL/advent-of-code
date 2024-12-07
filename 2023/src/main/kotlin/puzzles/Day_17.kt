package puzzles

import PuzzleDay
import puzzles.commons.Position
import puzzles.commons.isValidXAndY

enum class Axis{
    X,
    Y,
    NOTHING
}

class Day_17: PuzzleDay {
    override fun puzzleOne(input: String): Any? {
        val matriz = input
            .split("\r\n")
            .map { it.map { it.digitToInt() } }

        return walkThroughMap(matriz)
    }

    private fun walkThroughMap(matriz: List<List<Int>>): Int{
        val trackList = mutableListOf<Position>()
        val copyMatrizVisual = matriz.map {
            it.toMutableList()
        }
        val positionsWeight = mutableMapOf(
            Position(0,0) to matriz[0][0]
        )

        var actualVertice = positionsWeight.minBy { it.value }

        val lastPosition = Position(matriz[0].count()-1, matriz.count()-1)
        val alreadyGoneVertices = hashSetOf<Position>()

        while(actualVertice.key != lastPosition){
            val lastPositionVisited = actualVertice
            actualVertice = positionsWeight.minBy { it.value }
            positionsWeight.remove(actualVertice.key)

            alreadyGoneVertices.add(actualVertice.key)
            trackList.add(actualVertice.key)

            var axisOfChange = Axis.NOTHING
            if (trackList.count() == 3) {
                if (trackList.all { it.x == trackList[0].x }) {
                    axisOfChange = Axis.X
                } else if (trackList.all { it.y == trackList[0].y }) {
                    axisOfChange = Axis.Y
                }
                //Letting only the last vertice visited
                trackList.removeFirst()
                trackList.removeFirst()
            }

            //Print matriz
            copyMatrizVisual[actualVertice.key.y][actualVertice.key.x] = 0

            var vertices = getPossibleDestinations(actualVertice.key.y, actualVertice.key.x, axisOfChange, matriz)
                .apply {
                    remove(lastPositionVisited.key)
                }

            vertices.forEach {
                vertices[it.key] = vertices[it.key]!! + actualVertice.value
            }

            positionsWeight.mergeMap(vertices, alreadyGoneVertices)
        }

        copyMatrizVisual.print()
        return actualVertice.value
    }

    fun MutableMap<Position, Int>.mergeMap(map: MutableMap<Position, Int>, alreadyGoneVertices: HashSet<Position>){
        map.forEach { position, findedValue ->
            val possibleVertice = this[position]
            if(possibleVertice != null){
                if(possibleVertice > findedValue){
                    this[position] = findedValue
                }
            }else{
                if(!alreadyGoneVertices.contains(position)){
                    this[position] = findedValue
                }
            }
        }
    }

    fun List<MutableList<Int>>.print(){
        this.forEach { it ->
            it.forEach {char ->
              print(char)
            }
            println()
        }
        println()
    }

    private fun getPossibleDestinations(line: Int, column: Int, axisOfChange: Axis, matriz: List<List<Int>>): MutableMap<Position, Int> {
        val positionsWeights = mutableMapOf<Position, Int>()
        setOf(
            Triple(1, 0, Axis.X),
            Triple(-1, 0, Axis.X),
            Triple(0, +1, Axis.Y),
            Triple(0, -1, Axis.Y),
        ).forEach {
            val position = Position(it.first + column, it.second + line)
            if (isValidXAndY(position, matriz[0].count(), matriz.count())) {
                if (it.third != axisOfChange) { // Meaning that we do not add this possible vertice, simulating in this moment a oriented graph. Because of the limit of 3
                    positionsWeights[position] = matriz[position.y][position.x]
                }
            }
        }

        return positionsWeights
    }


    override fun puzzleTwo(input: String): Any? {
        TODO("Not yet implemented")
    }
}