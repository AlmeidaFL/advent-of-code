package puzzles

import PuzzleDay
import puzzles.commons.Position
import puzzles.commons.isValidXAndY
import kotlin.math.absoluteValue

class Day_18: PuzzleDay {
    override fun puzzleOne(input: String): Any? {
        var digPlan = input
            .split("\r\n")
            .map{
                DigStep(it[0], Regex("\\d+").find(it)!!.value.toLong())
            }

        val digsPosition = digLagoon(digPlan).apply {
            var minX = this.minBy {
                it.x
            }.x
            var minY = this.minBy {
                it.y
            }.y

            //Adjusting the coordinates to 0,0 so we can print more easily
            if(minX > 0) minX = 0
            if(minY > 0) minY = 0

            this.forEach {
                it.x += minX.absoluteValue
                it.y += minY.absoluteValue
            }
        } //Doing this to ensure that x and y will not be negative

        val maxX = digsPosition.maxOf {
            it.x
        }
        val maxY = digsPosition.maxOf{
            it.y
        }

        var listX = Array<Char>(maxX+1) {it -> '.'}
        val grid = Array<MutableList<Char>>(maxY+1) {it -> listX.toMutableList()}

        digsPosition.forEach {
            grid[it.y][it.x] = 'X'
        }

        val index = grid[0].indexOfFirst {
            it == 'X'
        }
        floodFill(index+1,1, grid)

        grid.print()

        return grid.sumOf {
            it.count{
                it == 'X'
            }
        }
    }

    fun digLagoon(digPlan: List<DigStep>): List<Position>{
        val initialPosition = Position(0,0)
        val digsPosition = mutableListOf(initialPosition)
        var lastPosition = initialPosition

        digPlan.forEach { step ->
            repeat(step.times.toInt()){
                val newPosition = when(step.direction){
                    'U' -> Position(lastPosition.x, lastPosition.y - 1)
                    'D' -> Position(lastPosition.x, lastPosition.y + 1)
                    'R' -> Position(lastPosition.x + 1, lastPosition.y)
                    'L' -> Position(lastPosition.x - 1, lastPosition.y)
                    else -> throw Exception("Should not happen")
                }
                lastPosition = newPosition
                digsPosition.add(newPosition)
            }
        }

        return digsPosition
    }


    fun floodFill(initialX: Int, initialY: Int, grid: Array<MutableList<Char>>){
        val positionQueue = mutableListOf(initialX to initialY)
        val lineLength = grid[0].count()
        val gridLength = grid.count()

        do{
            val position = positionQueue.removeFirst()
            val x = position.first
            val y = position.second
            if(isValidXAndY(x, y, lineLength, gridLength)){
                if(grid[y][x] == 'X')
                    continue

                grid[y][x] = 'X'

                positionQueue.add(x+1 to y)
                positionQueue.add(x-1 to y)
                positionQueue.add(x to y+1)
                positionQueue.add(x+1 to y-1)
            }
        }while(positionQueue.isNotEmpty())
    }


    fun Array<MutableList<Char>>.print(){
        this.forEach { it ->
            it.forEach {char ->
                print(char)
            }
            println()
        }
        println()
    }


    data class DigStep(val direction: Char, val times: Long)

    override fun puzzleTwo(input: String): Any? {
        val plan = input
            .split("\r\n")
            .map{
                val paretensis = Regex("\\((.*?)\\)").find(it)!!.value.trim('(', ')')

                DigStep(
                    getDirection(paretensis.last()),
                    paretensis.subSequence(1, paretensis.length-1).toString().toLong(16)
                )
            }

        val (vertices, perimeter) = walkThroughLongMap(plan)

        /*
        * Shoelace formula doesnt give the total area, because the points that we are getting are like the center
        * of this squares, so there's a gap between the center and right or left (1/4,2/4,3/4 depending on where the center of the coordenate drawing is).
        * I.e, space is left from this formula. This doesn't
        * mean the formula is incorret, but we are getting an area of a "circle inside of another circle bigger". To adjust this,
        * it's necessary to use Pick's formula.
        * */
        val area = calculateShoelace(vertices)
        return getInteriorPoints(area, perimeter) + perimeter
    }

    fun walkThroughLongMap(plan: List<DigStep>): Pair<List<PositionLong>, Long>{
        val initialPosition = PositionLong(0,0)
        val vertices = mutableListOf(initialPosition)
        var lastDirection = 'Z'
        var lastPosition = initialPosition
        var perimeter = 0L
        plan.forEach { digStep ->
            val times = if(digStep.direction == 'U' || digStep.direction == 'L') digStep.times * -1 else digStep.times
            val newPosition = PositionLong(lastPosition.x, lastPosition.y)
            if(digStep.direction == 'R' || digStep.direction == 'L'){
                newPosition.x += times
                if(lastDirection == 'D' || lastDirection == 'U'){
                    vertices.add(lastPosition)
                }
            }else if(digStep.direction == 'D' || digStep.direction == 'U'){
                newPosition.y += times
                if(lastDirection == 'R' || lastDirection == 'L'){
                    vertices.add(lastPosition)
                }
            }
            lastPosition = newPosition
            lastDirection = digStep.direction
            perimeter += digStep.times
        }

        return Pair(vertices, perimeter)
    }

    //https://en.wikipedia.org/wiki/Pick%27s_theorem
    fun getInteriorPoints(area: Long, perimeter: Long): Long{
        //Original formula => Area = internalPoints + boundaryPoint / 2 - 1
        //We have the internal area, so we need internal points
        return area - (perimeter/2)+1
    }

    //https://en.wikipedia.org/wiki/Shoelace_formula
    fun calculateShoelace(positions: List<PositionLong>): Long{
        var sumX = 0L
        var sumY = 0L

        for (i in 0..<positions.count()-1){
            sumX += positions[i].x * positions[i+1].y
            sumY += positions[i].y * positions[i+1].x
        }

        sumX += positions[positions.count()-1].x * positions[0].y
        sumY += positions[0].x * positions[positions.count()-1].y

        return (sumX - sumY).absoluteValue / 2L
    }

    data class PositionLong(var x: Long, var y: Long)

    fun getDirection(number: Char) = when(number){
        '0' -> 'R'
        '1' -> 'D'
        '2' -> 'L'
        '3' -> 'U'
        else -> throw Exception("Converting direction $number")
    }
}