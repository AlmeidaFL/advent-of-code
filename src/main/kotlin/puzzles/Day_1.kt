package puzzles

import PuzzleDay

class Day_1: PuzzleDay {
    init{
        println("Ola");
    }

    override fun puzzleOne(input: String): Any? {
        val number = input
            .split("\r\n")
            .map {
                it ->
                Regex("[1-9]+").findAll(it).map { x -> x.value }.reduce { acc, s ->  acc + s}
            }
            .sumOf { s ->
                val number = if(s.length > 1) "${s.first()}${s.last()}" else "${s.first()}${s.first()}"
                number.toInt()
            }

        return number
    }
//
//    override fun puzzleTwo(input: String): Any {
//        val number = input
//            .split("\r\n")
//            .map {
//                    it ->
//                var digitsWords = Regex("(one|two|three|four|five|six|seven|eight|nine)")
//                var a = Regex("([1-9]*|${digitsWords.pattern}*)*").findAll(it).toList()
//                    var x = a.map { x -> x.value }.reduce { acc, s ->  acc + s}
//                x
//            }
//            .sumOf { s ->
//                val number = if(s.length > 1) "${s.first()}${s.last()}" else "${s.first()}${s.first()}"
//                number.toInt()
//            }
//
//        return number
//    }


    override fun puzzleTwo(input: String): Any? {
        val number = input
            .split("\r\n")
            .map { it ->
                var a = replaceAll(it)
                a
            }
            .map {
                    it ->
                Regex("[1-9]").findAll(it).toList().map { x -> x.value }.reduce { acc, s ->  acc + s}
            }
            .sumOf { s ->
                val number = if(s.length > 1) "${s.first()}${s.last()}" else "${s.first()}${s.first()}"
                number.toInt()
            }

        return number
    }
}
fun replaceAll(word: String): String{
    var newWord = word
    val numberMap = mapOf(
        "one" to "one1one",
        "two" to "two2two",
        "three" to "three3three",
        "four" to "four4four",
        "five" to "five5five",
        "six" to "six6six",
        "seven" to "seven7seven",
        "eight" to "eight8eight",
        "nine" to "nine9nine"
    )
    for ((key, value) in numberMap){
        newWord = newWord.replace(key, value.toString())
    }
    return newWord
}
fun substituteWordNumber(word: String) = when(word){
    "one" -> 1
    "two" -> 2
    "three" -> 3
    "four" -> 4
    "five" -> 5
    "six" -> 6
    "seven" -> 7
    "eight" -> 8
    "nine" -> 9
    else -> -1
}