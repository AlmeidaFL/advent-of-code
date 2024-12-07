package puzzles

import PuzzleDay
import puzzles.commons.isValidXAndY
import puzzles.commons.print

class Day_13: PuzzleDay {
    override fun puzzleOne(input: String): Any? = input
            .split("\r\n")
            .joinToString("\n")
            .split("\n\n")
            .map{
                it.lines()
            }
            .mapIndexed { index, strings ->
                getMatrizReflection(strings, index)
            }.reduce { acc, i ->  acc + i}

    fun getMatrizReflection(matriz: List<String>, index: Int): Int{

        var columnsVertical = getNumberOfColumnsBeforeReflection(matriz) //Handle matriz in normal position
        var columnsHorizontal = getNumberOfColumnsBeforeReflection(matriz.transpose()) // Handle matriz tranposed, horizontal case

        if(columnsVertical > -1){
            return columnsVertical
        }else if(columnsVertical == -1 && columnsHorizontal != -1){
            return columnsHorizontal * 100
        }

        matriz.print()
        throw Exception("Should not happen. Reflections are equal. Horizontal: $columnsHorizontal, Vertical $columnsVertical, Index $index")
    }

    private fun getNumberOfColumnsBeforeReflection(matriz: List<String>): Int{
        for (i in 0..<matriz[0].count() - 1) {
            if (checkPossibleReflection(matriz, i, i + 1)){
                return i+1
            }
        }
        return -1
    }

    fun checkPossibleReflection(matriz: List<String>, columnA: Int, columnB: Int): Boolean{
        var columnsEqual = true

        if(isValidXAndY(columnA, 0, matriz[0].count(), matriz.count())
                && isValidXAndY(columnB, 0, matriz[0].count(), matriz.count())) {
            for (i in 0..<matriz.count()) {
                if (matriz[i][columnA] != matriz[i][columnB]) {
                    columnsEqual = false
                }
            }

            if (columnsEqual) {
                columnsEqual = checkPossibleReflection(matriz, columnA - 1, columnB + 1)
            }
        }

        return columnsEqual
    }

    fun List<String>.transpose(): List<String>{
        var list = mutableListOf<String>()

        for(i in this[0].length-1 downTo 0){
            val stringBuilder = StringBuilder()
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