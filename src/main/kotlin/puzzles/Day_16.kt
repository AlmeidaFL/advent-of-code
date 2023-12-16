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

class Day_16: PuzzleDay {
    val beamStack = Stack<Beam>()
    val specialChars = listOf('\\', '/', '|', '-')


    override fun puzzleOne(input: String): Any? {
        val matriz = input
            .split("\r\n")
            .map { it.toList() }
        val positionsSet = mutableSetOf<Position>()
        val initialBeam = Beam(Position(0,0), Direction.RIGHT)
        beamStack.add(initialBeam)
        do{
            val beam = beamStack.pop()
            navigateBeam(beam, matriz, positionsSet)
        }while(!beamStack.empty())

        return positionsSet.count()
    }


    fun navigateBeam(beam: Beam, matriz: List<List<Char>>, positionsSet: MutableSet<Position>){
        if(isValidXAndY(beam.position, matriz[0].count(), matriz.count())){
            positionsSet.add(beam.position)
            if (hasSpecialReflector(beam.position, matriz)) {
                // verifies initial position
            }

            var continueBeamWalking = true
            while (continueBeamWalking) {
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
                positionsSet.add(beam.position)
                if (hasSpecialReflector(beam.position, matriz)) {
                    continueBeamWalking = ruleBeam(beam, matriz)
                }
            }
        }

    }

    fun ruleBeam(beam: Beam, matriz: List<List<Char>>): Boolean{
        val specialReflector = matriz[beam.line][beam.column]

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
                Beam(Position(beam.column, beam.line-1), Direction.DOWN)
            )
            return false
        }

        return true
    }

    fun hasSpecialReflector(position: Position, matriz: List<List<Char>>) =
        specialChars.contains(matriz[position.y][position.x])

    override fun puzzleTwo(input: String): Any? {
        TODO("Not yet implemented")
    }

    class Beam(var position: Position, var directionToGo: Direction = Direction.NONE) {
        val column get() = position.x
        val line get() = position.y
    }
}