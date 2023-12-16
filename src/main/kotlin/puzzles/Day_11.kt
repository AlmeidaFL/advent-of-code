package puzzles

import PuzzleDay
import kotlin.math.abs

class Day_11: PuzzleDay {

    var linesToAdd: List<Pair<Int,Long>>? = null
    var columnsToAdd: List<Pair<Int,Long>>? = null
    override fun puzzleOne(input: String): Any? {
        val matriz = input
            .split("\r\n")
            .map {
                it.toMutableList()
            }
            .toMutableList()
            .apply {
                linesToAdd = needsLines(this).toList()
                columnsToAdd = needsColumns(this).toList()
            }
        val galaxies = getGalaxies(matriz)
        var steps = 0L

        for(i in 0..<galaxies.count()){
            for(j in (i+1)..<galaxies.count()){
                steps += distanceBetweenPositions(galaxies[i].position, galaxies[j].position)
            }
        }

        
        return steps
    }

    private fun getGalaxies(matriz: MutableList<MutableList<Char>>): List<Galaxy> {
        val galaxies = mutableListOf<Galaxy>()
        for(y in 0..<matriz.count()){
            for(x in 0..<matriz[0].count()){
                if(matriz[y][x] == '#'){
                    val linesToExpand = linesToAdd!!.filter { it.first <= y }.sumOf { it.second }
                    val columnsToExpand = columnsToAdd!!.filter { it.first <= x }.sumOf { it.second }
                    galaxies += Galaxy(Position(x + columnsToExpand, y + linesToExpand))
                }
            }
        }
        return galaxies
    }

    fun distanceBetweenPositions(p1: Position, p2: Position): Long {
        return abs(p1.x - p2.x) + abs(p1.y - p2.y)
    }

    fun needsColumns(matriz: MutableList<MutableList<Char>>, quantityToExpand: Long = 1): HashMap<Int, Long>{
        val indexToAdd = hashMapOf<Int, Long>()
        for(i in 0..<matriz[0].count()){
            var needColumn = true
            for(j in 0..<matriz.count()){
                if(matriz[j][i] == '#'){
                    needColumn = false
                    break
                }
            }
            if(needColumn){
                indexToAdd[i] = quantityToExpand
            }
        }
        return indexToAdd
    }

    fun needsLines(matriz: MutableList<MutableList<Char>>, quantityToExpand: Long = 1): HashMap<Int, Long>{
        val indexToAdd = hashMapOf<Int, Long>()
        for(i in 0..<matriz.count()){
            var needLine = true
            for(j in 0..<matriz[0].count()){
                if(matriz[i][j] == '#'){
                    needLine = false
                    break
                }
            }
            if(needLine){
                indexToAdd[i] = quantityToExpand
            }
        }
        return indexToAdd
    }

    override fun puzzleTwo(input: String): Any? {
        val matriz = input
            .split("\r\n")
            .map {
                it.toMutableList()
            }
            .toMutableList()
            .apply {
                linesToAdd = needsLines(this, 999999).toList()
                columnsToAdd = needsColumns(this, 999999).toList()
            }
        val galaxies = getGalaxies(matriz)
        var steps = 0L

        for(i in 0..<galaxies.count()){
            for(j in (i+1)..<galaxies.count()){
                steps += distanceBetweenPositions(galaxies[i].position, galaxies[j].position)
            }
        }

        return steps
    }

    data class Position(var x: Long, var y: Long)

    class Galaxy(val position: Position) {
        val x get() = position.x
        val y get() = position.y
    }
}
