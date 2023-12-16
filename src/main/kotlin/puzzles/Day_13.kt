package puzzles

import PuzzleDay
import puzzles.commons.print
import kotlin.math.ceil

class Day_13: PuzzleDay {
    override fun puzzleOne(input: String): Any? = input
            .split("\r\n")
            .joinToString("\n")
            .split("\n\n")
            .map{it.lines()}
            .map {
                getNumberOfPastIndexes(it)
            }.reduce { acc, i ->  acc + i}

    fun getNumberOfPastIndexes(matriz: List<String>): Int{
        for(line in matriz){
           val ceil = ceil(line.count()/2.0).toInt()
            repeat(ceil){ index ->
                val endIndexStart = if(index == 0) 1 else 0
                val sum = if(index == 0) 0 else 1
                for(column in index..<ceil){
                    val leftReflection = line.subSequence(index, column+1)
                    val rightReflection = line.subSequence(column+1, (column+endIndexStart)*2+sum).reversed().toString()
                    if(leftReflection == rightReflection){
                        val numbersOfSteps = checkPossibleReflection(matriz, index, column+1, leftReflection.length)
                        if(numbersOfSteps != -1) return numbersOfSteps
                    }
                }
            }
        }

        matriz.print()
        return -1
    }

    private fun checkPossibleReflection(matriz: List<String>, leftInit: Int, rightInit: Int, length: Int): Int{
        for(i in 0..<matriz.count()){
            for(j in 0..<length){
                if(matriz[i][leftInit+j] != matriz[i][rightInit+j]){
                    return -1
                }
            }
        }

        return leftInit
    }

    fun List<String>.transpose(): List<String>{
        var list = mutableListOf<String>()

        for(i in this[0].length-1..0){
            var stringBuilder = StringBuilder()
            for(j in 0..<this.count()){
                stringBuilder.append(this[j][i])
            }
            list += stringBuilder.toString()
        }

        return list
    }

    override fun puzzleTwo(input: String): Any? {
        TODO("Not yet implemented")
    }
}