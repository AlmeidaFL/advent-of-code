package puzzles

import PuzzleDay
import kotlinx.coroutines.*

class Day_5: PuzzleDay {

    val destination = 0
    val source = 1
    var length = 2

    override fun puzzleOne(input: String): Any? {
        var splittedInput = input
            .split(":")

        var seeds = splittedInput.subList(1, 2).map {
            Regex("\\d+").findAll(it).map {
                it.value.toLong()
            }.toList()
        }.flatten()
        var listMaps = splittedInput.subList(2, 9)
            .map {
                Regex("\\d+").findAll(it).map { it.value.toLong() }.chunked(3).toList()
            }

        var transformationList = mutableListOf<Long>()
        seeds.forEach { seed ->
            var seedTransformed = seed
            listMaps.forEach { inteireMap ->
                seedTransformed = getNumberInMap(seedTransformed, inteireMap)
            }
            transformationList.add(seedTransformed)
        }

        return transformationList.min()
    }

    fun getNumberInMap(number: Long, mapList: List<List<Long>>): Long {
        var mappedNumber = number

        run breaking@{
            mapList.forEach { line ->
                if (number in line[source]..<(line[source] + line[length])) {
                    mappedNumber = number + (line[destination] - line[source])
                    return@breaking
                }
            }
        }

        return mappedNumber
    }

    override fun puzzleTwo(input: String): Any? {
        var splittedInput = input
            .split(":")

        var seeds = splittedInput.subList(1, 2).map {
            Regex("\\d+").findAll(it).map {
                it.value.toLong()
            }.toList()
        }.flatten()

        var listMaps = splittedInput.subList(2, 9)
            .map {
                Regex("\\d+").findAll(it).map { it.value.toLong() }.chunked(3).toList()
            }
        
        runBlocking {
            getSeeds(seeds, listMaps)
        }

        return "lowest"
    }

    suspend fun getSeeds(seeds: List<Long>, listMaps: List<List<List<Long>>>) = coroutineScope {
        Array(10) { index ->
            async {
                print("Started coroutine index $index")
                var lowest = Long.MAX_VALUE
                var i = index * 2
                for (j in seeds[i]..(seeds[i] + seeds[i + 1])) {
                    var seedTransformed = j
                    listMaps.forEach { inteireMap ->
                        seedTransformed = getNumberInMap(seedTransformed, inteireMap)
                    }
                    if (seedTransformed < lowest) {
                        lowest = seedTransformed
                    }
                }
                println(lowest)
            }.await()
        }
    }
}