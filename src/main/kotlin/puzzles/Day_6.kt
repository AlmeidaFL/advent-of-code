import java.io.File
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.min
import kotlin.streams.asStream

class Day_6: PuzzleDay {
    override fun puzzleOne(input: String): Any? {
        var inp = input
            .split("\r\n")
        var times = Regex("\\d+").findAll(inp[0]).toList().map{it.value.toInt()}
        var distances = Regex("\\d+").findAll(inp[1]).toList().map{it.value.toInt()}

        var listOfBeatWays = mutableListOf<Int>()
        for ((index, time) in times.withIndex()){
            var sumOfBeatWays = 0
            for (i in 1..time){
                var speedPerMilimeter = i
                var remainingTime = time - speedPerMilimeter
                var runnedDistance = speedPerMilimeter * remainingTime
                if (runnedDistance > distances[index]){
                    sumOfBeatWays += 1
                }
            }
            listOfBeatWays += sumOfBeatWays
        }

        return  listOfBeatWays.reduce { acc, i ->  acc * i}
    }

    override fun puzzleTwo(input: String): Any? {
        var inp = input
            .split("\r\n")
        var time = Regex("\\d+").findAll(inp[0])
            .toList()
            .map{it.value}
            .reduce { acc, s ->  acc + s}
            .toLong()
        var distance = Regex("\\d+").findAll(inp[1])
            .toList()
            .map{it.value}
            .reduce{ acc, s ->  acc + s}
            .toLong()

        var sumOfBeatWays = 0L
        for (i in 1..time){
            var speedPerMilimeter = i
            var remainingTime = time - speedPerMilimeter
            var runnedDistance = speedPerMilimeter * remainingTime
            if (runnedDistance > distance){
                sumOfBeatWays += 1
            }
        }

        return  sumOfBeatWays
    }

}