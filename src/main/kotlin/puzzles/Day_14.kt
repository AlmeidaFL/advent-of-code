package puzzles

import PuzzleDay

class Day_14: PuzzleDay {
    override fun puzzleOne(input: String): Any? {
        var matriz = input
            .split("\r\n")
            .map { it.toCharArray().toMutableList() }
            .apply {
                moveRocksToNorth(this)
//                this.print()
            }

        return countLoad(matriz)
    }

    fun countLoad(matriz: List<List<Char>>): Int{
        var sum = 0
        var numbersOfLines = matriz.count()
        loop@ for(i in 0..<matriz[0].count()){
            for(j in 0..<numbersOfLines){
                var char = matriz[j][i]
                if(char == 'O'){
                    sum += numbersOfLines - j
                }
            }
        }

        return sum
    }
    fun moveRocksToNorth(matriz: List<MutableList<Char>>){
        for(i in 0..<matriz[0].count()){
            loop@ for(j in 0..<matriz.count()){
                var char = matriz[j][i]
                if(char == 'O'){
                    var rockActualPosition = j
                    var charToFind = if(j == 0) continue@loop else matriz[j-1][i]
                    while(charToFind != '#' && charToFind != 'O'){
                        matriz[rockActualPosition][i] = '.'
                        rockActualPosition--
                        matriz[rockActualPosition][i] = 'O'
                        charToFind = if(rockActualPosition == 0) continue@loop else matriz[rockActualPosition-1][i]
                    }
                }
            }
        }
    }

    fun moveRocksToSouth(matriz: List<MutableList<Char>>){
        val numbersOfLines = matriz.count()
        val numbersOfColumns = matriz[0].count()
        for(i in 0..<numbersOfColumns){
            loop@ for(j in (numbersOfLines-1) downTo 0){
                var char = matriz[j][i]
                if(char == 'O'){
                    var rockActualPosition = j
                    var charToFind = if(j == numbersOfLines-1) continue@loop else matriz[j+1][i]
                    while(charToFind != '#' && charToFind != 'O'){
                        matriz[rockActualPosition][i] = '.'
                        rockActualPosition++
                        matriz[rockActualPosition][i] = 'O'
                        charToFind = if(rockActualPosition == numbersOfLines-1) continue@loop else matriz[rockActualPosition+1][i]
                    }
                }
            }
        }
    }

    fun moveRocksToEast(matriz: List<MutableList<Char>>){
        val numbersOfLines = matriz.count()
        val numbersOfColumns = matriz[0].count()
        for(i in 0..<numbersOfLines){
            loop@ for(j in (numbersOfLines-1) downTo 0){
                val char = matriz[i][j]
                if(char == 'O'){
                    var rockActualPosition = j
                    var charToFind = if(j == numbersOfColumns-1) continue@loop else matriz[i][j+1]
                    while(charToFind != '#' && charToFind != 'O'){
                        matriz[i][rockActualPosition] = '.'
                        rockActualPosition++
                        matriz[i][rockActualPosition] = 'O'
                        charToFind = if(rockActualPosition == numbersOfColumns-1) continue@loop else matriz[i][rockActualPosition+1]
                    }
                }
            }
        }
    }

    fun moveRocksToWest(matriz: List<MutableList<Char>>){
        val numbersOfLines = matriz.count()
        val numbersOfColumns = matriz[0].count()
        for(i in 0..<numbersOfLines){
            loop@ for(j in 0..<numbersOfColumns){
                val char = matriz[i][j]
                if(char == 'O'){
                    var rockActualPosition = j
                    var charToFind = if(j == 0) continue@loop else matriz[i][j-1]
                    while(charToFind != '#' && charToFind != 'O'){
                        matriz[i][rockActualPosition] = '.'
                        rockActualPosition--
                        matriz[i][rockActualPosition]  = 'O'
                        charToFind = if(rockActualPosition == 0) continue@loop else matriz[i][rockActualPosition-1]
                    }
                }
            }
        }
    }

    override fun puzzleTwo(input: String): Any? {
        val matriz = input
            .split("\r\n")
            .map { it.toCharArray().toMutableList() }

        val startTime = System.currentTimeMillis()

        repeat(1000000000){
            println("Iteração $it, faltam ${it-1000000000}")
            moveRocksToNorth(matriz)
            moveRocksToWest(matriz)
            moveRocksToSouth(matriz)
            moveRocksToEast(matriz)
        }

        val endTime = System.currentTimeMillis()
        val elapsedTime = endTime - startTime
        println("Tempo decorrido: $elapsedTime milissegundos, segundos: ${elapsedTime/1000.0}")

        return countLoad(matriz)
    }
}