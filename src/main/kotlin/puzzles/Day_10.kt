package puzzles

import PuzzleDay

class Day_10: PuzzleDay {
    val chars = arrayOf('⎾','⏋','⎿', '⏌', '－', '|')
    val startingPoint = 'S'
    override fun puzzleOne(input: String): Any? {
        var matriz = input
            .split(Regex("\r\n"))
            .map{
                it.toCharArray().toMutableList()
            }

        val startPoint = Position(-1,-1)
        var y = matriz.indexOfFirst {
            var x = it.indexOfFirst { char -> char == startingPoint }
            startPoint.x = x
            x != -1
        }
        startPoint.y = y

        var steps = 0
        var pipes = listOf(
            Pipe(Position(startPoint.x, startPoint.y), 'S'),
            Pipe(Position(startPoint.x, startPoint.y), 'S')
        )

        var pathTaken: Position = Position(-1,-1)
        var pipesRunning = false
        run block@{
            while(true){
                repeat(2){
                    var x = 1
                    pipesRunning = false
                    loop@ for(x in -1..1){
                        for(y in -1..1){
                            var pipe = pipes[it]
                            var nextPosition = Position(pipe.x + x, pipe.y + y)
                            if(isValidXAndY(pipe.position, x, y, matriz[0].count(), matriz.count()))   {
                                var nextPipeChar = matriz.getChar(nextPosition)
                                if(Pipe.isItPossibleToMove(pipe.pipeChar, nextPipeChar, Position(x, y))){
                                    if(!pipe.isPrevious(nextPosition) && nextPosition != pathTaken){
                                        pipe.previousPipe = Position(pipe.x, pipe.y)
                                        pipe.position = nextPosition
                                        pipe.pipeChar = matriz.getChar(nextPosition)
                                        substituteChar(pipe.position, matriz)
                                        if(steps == 0 && it == 0){
                                            pathTaken = nextPosition
                                        }
                                        pipesRunning = true
                                        break@loop
                                    }
                                }
                            }
                        }
                    }
                }
                if(!pipesRunning) return@block
                steps++
            }
        }
        matriz.print()
        return ++steps
    }

    fun substituteChar(position: Position, matriz: List<MutableList<Char>>){
        val actualChar = matriz[position.y][position.x]
        val substitute = when(actualChar){
            'F' -> '⎾'
            '7' -> '⏋'
            'L' -> '⎿'
            'J' -> '⏌'
            '-' -> '－'
            '.' -> ' '
            else -> actualChar
        }
        matriz[position.y][position.x] = substitute
    }

    override fun puzzleTwo(input: String): Any? {
        TODO("Not yet implemented")
    }

    fun List<List<Char>>.print(){
        this.forEach { it ->
            it.forEach {char ->
                if(chars.contains(char)){
                    print("\u001B[31m$char\u001B[0m")
                }else{
                    print(char)
                }
            }
            println()
        }
        println()
    }

    fun List<List<Char>>.getChar(position: Position) = this[position.y][position.x]

    fun isValidXAndY(nextPosition: Position, x: Int, y: Int, lineLength: Int, matrizLength: Int): Boolean{
        if((x == -1 && y == -1)
            || (x == 1 && y == 1)
            || (x == -1 && y == 1)
            || (x == 1 && y == -1)
            || (x == 0 && y == 0)){
            return false
        }
        val position = Position(nextPosition.x + x, nextPosition.y + y)
        return position.x in 0..<lineLength && position.y in 0..<matrizLength
    }

    data class Position(var x: Int, var y: Int)

    class Pipe(var position: Position, var pipeChar: Char){
        var previousPipe: Position? = null
        val x get() = position.x
        val y get() = position.y

        fun isPrevious(position: Position) = previousPipe == position

        companion object{
            fun isItPossibleToMove(actualPipe: Char, nextPipe: Char, nextMoviment: Position): Boolean{
                var originalMoviment = when (nextMoviment) {
                    Position(1,0) -> arrayOf('-','J','7')
                    Position(-1,0) -> arrayOf('-','F','L')
                    Position(0,1) -> arrayOf('L', '|', 'J')
                    Position(0, -1) -> arrayOf('F','|','7')
                    else -> throw Exception("Moviment not found $nextMoviment")
                }

                var pipesThatArePossibleToMove = when (nextMoviment) {
                    Position(-1,0) -> arrayOf('-','J','7')
                    Position(1,0) -> arrayOf('-','F','L')
                    Position(0,-1) -> arrayOf('L', '|', 'J')
                    Position(0, 1) -> arrayOf('F','|','7')
                    else -> throw Exception("Moviment not found $nextMoviment")
                }

                return originalMoviment.contains(nextPipe) && (pipesThatArePossibleToMove.contains(actualPipe) || actualPipe == 'S')
            }
        }
    }
}