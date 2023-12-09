package puzzles

import PuzzleDay

class Day_9: PuzzleDay {
    override fun puzzleOne(input: String): Any? {
         return input
            .split("\r\n")
            .map {
                val sequence = Regex("-?\\d+").findAll(it).map { it.value.toLong() }.toList()
                getNextNumber(sequence)
             }.reduce { acc, l ->  acc + l}
    }

    fun getNextNumber(sequence: List<Long>): Long{
        var differenceList = mutableListOf<Long>()
        for(i in 0..<(sequence.count()-1)){
            differenceList += sequence[i+1] - sequence[i]
        }
        return if(isAllTheSame(differenceList)) differenceList[0] + sequence.last()
        else getNextNumber(differenceList) + sequence.last()
    }

    fun isAllTheSame(list: List<Long>): Boolean{
        for(i in 0..<(list.count()-1)){
            if(list[i] != list[i+1]) return false
        }
        return true
    }

    override fun puzzleTwo(input: String): Any? {
        return input
            .split("\r\n")
            .map {
                val sequence = Regex("-?\\d+").findAll(it).map { it.value.toLong() }.toList()
                getNextNumberBackwards(sequence)
            }.reduce { acc, l ->  acc + l}
    }

    fun getNextNumberBackwards(sequence: List<Long>): Long{
        var differenceList = mutableListOf<Long>()
        for(i in 0..<(sequence.count()-1)){
            differenceList += sequence[i+1] - sequence[i]
        }
        return if(isAllTheSame(differenceList)) sequence.first() - differenceList[0]
        else sequence.first() - getNextNumberBackwards(differenceList)
    }
}