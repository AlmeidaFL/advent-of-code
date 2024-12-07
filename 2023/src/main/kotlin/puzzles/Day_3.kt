package puzzles

import PuzzleDay

class Day_3: PuzzleDay{

    val notSimbol = '.'
    val gearSymbol = '*'

    val numbersAlreadyFound = mutableSetOf<NumberPosition>()

    override fun puzzleOne(input: String): Any? {
        var matriz = input
            .split(Regex("\r\n"))
            .map{
                it.toCharArray()
            }

        var sum = 0
        matriz.forEachIndexed { y, line ->
            var simbolsPosition = getSimbolsPosition(String(line), y)

            simbolsPosition.forEach{
                sum += getAdjecentNumbersSum(matriz, it)
            }
        }


        return sum
    }

    override fun puzzleTwo(input: String): Any? {
        var matriz = input
            .split(Regex("\r\n"))
            .map{
                it.toCharArray()
            }

        var sum = 0
        matriz.forEachIndexed { y, line ->
            var simbolsPosition = getGearSimbolsPosition(String(line), y)

            simbolsPosition.forEach{
                sum += getAdjecentGearNumbersSum(matriz, it)
            }
        }


        return sum
    }

    data class PositionRange(val extension: IntRange, val y: Int)

    data class Position(val x: Int, val y: Int)

    data class NumberPosition(val value: Int, val position: PositionRange)

    fun getSimbolsPosition(line: String, y: Int): List<Position>{
        var list = mutableListOf<Position>()
        for ((index, char) in line.withIndex()){
            if((char.code < 48 || char.code > 57) && char != notSimbol){
                list.add(Position(index, y))
            }
        }
        return list
    }

    fun getGearSimbolsPosition(line: String, y: Int): List<Position>{
        var list = mutableListOf<Position>()
        for ((index, char) in line.withIndex()){
            if(char == gearSymbol){
                list.add(Position(index, y))
            }
        }
        return list
    }

    fun getAdjecentNumbersSum(matriz: List<CharArray>, simbol:Position): Int{
        var sum = 0
        for(lineIndex in -1..1){
            for(columnIndex in -1..1){
                var digitPosition = Position(columnIndex + simbol.x, lineIndex + simbol.y)
                if(isValidXAndY(digitPosition, matriz[0].count(), matriz.count())){
                    val char = matriz[digitPosition.y][digitPosition.x]
                    if(char.isDigit()){
                        var number = getNumberInLine(String(matriz[digitPosition.y]), digitPosition)
                        if(!numbersAlreadyFound.contains(number)){
                            sum += number.value
                            numbersAlreadyFound.add(number)
                        }
                    }
                }
            }
        }

        return sum
    }

    fun getAdjecentGearNumbersSum(matriz: List<CharArray>, simbol:Position): Int{
        var sum = 0
        var listOfNumbers = mutableListOf<NumberPosition>()
        for(lineIndex in -1..1){
            for(columnIndex in -1..1){
                var digitPosition = Position(columnIndex + simbol.x, lineIndex + simbol.y)
                if(isValidXAndY(digitPosition, matriz[0].count(), matriz.count())){
                    val char = matriz[digitPosition.y][digitPosition.x]
                    if(char.isDigit()){
                        var number = getNumberInLine(String(matriz[digitPosition.y]), digitPosition)
                        if(!listOfNumbers.contains(number)){
                            listOfNumbers.add(number)
                        }
                    }
                }
            }
        }

        if(listOfNumbers.count() == 2){
            sum = listOfNumbers[0].value * listOfNumbers[1].value
        }

        return sum
    }

    fun isValidXAndY(position: Position, lineLength: Int, matrizLength: Int): Boolean{
        return position.x in 0..<lineLength && position.y in 0..<matrizLength
    }

    fun getNumberInLine(line: String, digitPosition: Position): NumberPosition{
        var indexInLine = digitPosition.x
        var startNumberIndex = digitPosition.x
        var endNumberIndex = digitPosition.x
        while(indexInLine >= 0 && line[indexInLine].isDigit()){
            startNumberIndex = indexInLine
            indexInLine--
        }
        indexInLine = digitPosition.x
        while(indexInLine < line.length && line[indexInLine].isDigit()){
            endNumberIndex = indexInLine
            indexInLine++
        }
        var charList = mutableListOf<Char>()
        for(i in startNumberIndex..endNumberIndex){
            charList.add(line[i])
        }

        return NumberPosition(
            String(charArrayOf(*(charList.toCharArray()))).toInt(),
            PositionRange(startNumberIndex..endNumberIndex,digitPosition.y))
    }
}