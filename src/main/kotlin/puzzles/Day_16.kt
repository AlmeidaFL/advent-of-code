package puzzles

import PuzzleDay
import puzzles.commons.Position
import puzzles.commons.isValidXAndY
import java.util.*

enum class Direction{
    RIGHT,
    LEFT,
    UP,
    DOWN,
    NONE
}
data class SpecialDirection(val position: Position, val direction: Direction){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SpecialDirection) return false

        return other.position == this.position && other.direction == this.direction
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + direction.hashCode()
        return result
    }
}

class Day_16: PuzzleDay {
    val beamStack = Stack<Beam>()
    val specialChars = listOf('\\', '/', '|', '-')
    val specialCharDirectionFlux = mutableSetOf<SpecialDirection>()
    var copyMatrizVisual: List<MutableList<Char>>? = null

    override fun puzzleOne(input: String): Any? {
        val matriz = input
            .split("\r\n")
            .map { it.toMutableList() }

        copyMatrizVisual = matriz.map {
            it.toMutableList()
        }

        val positionsSet = mutableSetOf<Position>()
        val initialBeam = Beam(Position(0,0), Direction.RIGHT)
        beamStack.add(initialBeam)
        do{
            val beam = beamStack.pop()
            navigateBeam(beam, matriz, positionsSet, copyMatrizVisual!!)
        }while(!beamStack.empty())


        copyMatrizVisual!!.print()
        return copyMatrizVisual!!.sumOf { subList ->
            subList.count{ it == 'X'}
        }
    }

    fun List<MutableList<Char>>.print(){
        this.forEach { it ->
            it.forEach {char ->
                if(specialChars.contains(char)){
                    print('.')
                }else{
                    print(char)
                }
            }
            println()
        }
        println()
    }

    fun directionAlreadyTooked(position: Position, direction: Direction)
        = specialCharDirectionFlux.contains(SpecialDirection(position, direction))

    fun navigateBeam(beam: Beam, matriz: List<MutableList<Char>>, positionsSet: MutableSet<Position>, matrizCopy: List<MutableList<Char>>){
            var continueBeamWalking = true
            while (continueBeamWalking) {
                if(isValidXAndY(beam.position, matriz[0].count(), matriz.count())){ //Constraints of matriz
                    positionsSet.add(beam.position)
                    matrizCopy!![beam.line][beam.column] = 'X'
                    if (hasSpecialReflector(beam.position, matriz)) {
                        if(directionAlreadyTooked(beam.position, beam.directionToGo)) break //Avoid beam loops
                        continueBeamWalking = ruleBeam(beam, matriz)
                        if(!continueBeamWalking) break // If reflector is '-' and direction is right for example will continue on loop
                    }
                    var columnToIncrement = 0
                    var lineToIncrement = 0
                    when (beam.directionToGo) {
                        Direction.UP -> lineToIncrement--
                        Direction.DOWN -> lineToIncrement++
                        Direction.LEFT -> columnToIncrement--
                        Direction.RIGHT -> columnToIncrement++
                        Direction.NONE -> throw Exception("Direction none shouldn't happen")
                    }
                    beam.position.apply {
                        this.x += columnToIncrement
                        this.y += lineToIncrement
                    }
                }else break
            }
    }

    fun ruleBeam(beam: Beam, matriz: List<List<Char>>): Boolean{
        val specialReflector = matriz[beam.line][beam.column]

        specialCharDirectionFlux.add(SpecialDirection(beam.position, beam.directionToGo))

        if((beam.directionToGo == Direction.RIGHT || beam.directionToGo == Direction.LEFT)
            && specialReflector == '|') {
            beamStack.apply {
                add(
                    Beam(Position(beam.column, beam.line-1), Direction.UP)
                )
                add(
                    Beam(Position(beam.column, beam.line+1), Direction.DOWN)
                )
            }
            return false
        }
        else if((beam.directionToGo == Direction.UP || beam.directionToGo == Direction.DOWN)
            && specialReflector == '-') {
            beamStack.apply {
                add(
                    Beam(Position(beam.column+1, beam.line), Direction.RIGHT)
                )
                add(
                    Beam(Position(beam.column-1, beam.line), Direction.LEFT)
                )
            }
            return false
        }
        //Handling mirror cases
        else if(beam.directionToGo == Direction.RIGHT && specialReflector == '/'){
            beamStack.add(
                    Beam(Position(beam.column, beam.line-1), Direction.UP)
                )
            return false
        }
        else if(beam.directionToGo == Direction.DOWN && specialReflector == '/'){
            beamStack.add(
                Beam(Position(beam.column-1, beam.line), Direction.LEFT)
            )
            return false
        }
        else if(beam.directionToGo == Direction.UP && specialReflector == '/'){
            beamStack.add(
                Beam(Position(beam.column+1, beam.line), Direction.RIGHT)
            )
            return false
        }
        else if(beam.directionToGo == Direction.LEFT && specialReflector == '/'){
            beamStack.add(
                Beam(Position(beam.column, beam.line+1), Direction.DOWN)
            )
            return false
        }
        // '\' case
        else if(beam.directionToGo == Direction.RIGHT && specialReflector == '\\'){
            beamStack.add(
                Beam(Position(beam.column, beam.line+1), Direction.DOWN)
            )
            return false
        }
        else if(beam.directionToGo == Direction.UP && specialReflector == '\\'){
            beamStack.add(
                Beam(Position(beam.column-1, beam.line), Direction.LEFT)
            )
            return false
        }
        else if(beam.directionToGo == Direction.DOWN && specialReflector == '\\'){
            beamStack.add(
                Beam(Position(beam.column+1, beam.line), Direction.RIGHT)
            )
            return false
        }
        else if(beam.directionToGo == Direction.LEFT && specialReflector == '\\'){
            beamStack.add(
                Beam(Position(beam.column, beam.line-1), Direction.UP)
            )
            return false
        }

        return true
    }

    fun hasSpecialReflector(position: Position, matriz: List<List<Char>>) =
        specialChars.contains(matriz[position.y][position.x])

    override fun puzzleTwo(input: String): Any? {
        val matriz = input
            .split("\r\n")
            .map { it.toMutableList() }


        val listOfEnergy = mutableListOf<Int>()

        //FirstLine to down
        for(i in 0..<matriz[0].count()) {
            specialCharDirectionFlux.clear()
            copyMatrizVisual = matriz.map {
                it.toMutableList()
            }
            val positionsSet = mutableSetOf<Position>()
            val initialBeam = Beam(Position(i, 0), Direction.DOWN)
            beamStack.add(initialBeam)
            do {
                val beam = beamStack.pop()
                navigateBeam(beam, matriz, positionsSet, copyMatrizVisual!!)
            } while (!beamStack.empty())

            listOfEnergy += copyMatrizVisual!!.sumOf { subList ->
                subList.count { it == 'X' }
            }
        }

        //Last Line to up
        for(i in 0..<matriz[0].count()) {
            specialCharDirectionFlux.clear()
            copyMatrizVisual = matriz.map {
                it.toMutableList()
            }
            val positionsSet = mutableSetOf<Position>()
            val initialBeam = Beam(Position(i, matriz.count()-1), Direction.UP)
            beamStack.add(initialBeam)
            do {
                val beam = beamStack.pop()
                navigateBeam(beam, matriz, positionsSet, copyMatrizVisual!!)
            } while (!beamStack.empty())

            listOfEnergy += copyMatrizVisual!!.sumOf { subList ->
                subList.count { it == 'X' }
            }
        }

        //First column to right
        for(i in 0..<matriz.count()) {
            specialCharDirectionFlux.clear()
            copyMatrizVisual = matriz.map {
                it.toMutableList()
            }
            val positionsSet = mutableSetOf<Position>()
            val initialBeam = Beam(Position(0, i), Direction.RIGHT)
            beamStack.add(initialBeam)
            do {
                val beam = beamStack.pop()
                navigateBeam(beam, matriz, positionsSet, copyMatrizVisual!!)
            } while (!beamStack.empty())

            listOfEnergy += copyMatrizVisual!!.sumOf { subList ->
                subList.count { it == 'X' }
            }
        }

        //Last column to left
        for(i in 0..<matriz.count()) {
            specialCharDirectionFlux.clear()
            copyMatrizVisual = matriz.map {
                it.toMutableList()
            }
            val positionsSet = mutableSetOf<Position>()
            val initialBeam = Beam(Position(matriz[0].count()-1, i), Direction.LEFT)
            beamStack.add(initialBeam)
            do {
                val beam = beamStack.pop()
                navigateBeam(beam, matriz, positionsSet, copyMatrizVisual!!)
            } while (!beamStack.empty())

            listOfEnergy += copyMatrizVisual!!.sumOf { subList ->
                subList.count { it == 'X' }
            }
        }

        return listOfEnergy.max()
    }

    class Beam(var position: Position, var directionToGo: Direction = Direction.NONE) {
        val column get() = position.x
        val line get() = position.y
    }
}