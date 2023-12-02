package puzzles

import PuzzleDay

class Day_2: PuzzleDay {

    val cubeRules: Map<String, Int> = mapOf(
        "red" to 12,
        "blue" to 14,
        "green" to 13
    )

    override fun puzzleOne(input: String): Any? {
        var lists = input.split("\r\n")
            .map{it ->
                Regex("Game (\\d+): ").replace(it, "")
            }
        var value = 0
        for((id, gameSet) in lists.withIndex()){
            var index = id + 1
            var cubeSet = gameSet.split(";")
            var passed = false
            for (cubs in cubeSet){
                var cub = cubs.split(",")
                var red = 0
                var blue = 0
                var green = 0
                cub.forEach {
                    var number = Regex("\\d+").findAll(it).first().value.toInt()
                    if(it.contains("blue")){
                        blue += number
                    }else if(it.contains("red")){
                        red += number
                    }else if(it.contains("green")){
                        green += number
                    }
                }
                passed = red <= cubeRules["red"]!!
                        && blue <= cubeRules["blue"]!!
                        && green <= cubeRules["green"]!!
                if(passed == false) break
            }
            if(passed){
                value += index
            }
        }

        return value
    }

    override fun puzzleTwo(input: String): Any? {
        var lists = input.split("\r\n")
            .map{it ->
                Regex("Game (\\d+): ").replace(it, "")
            }
        var sumOfPowers = 0
        for((id, gameSet) in lists.withIndex()){
            var index = id + 1
            var cubeSet = gameSet.split(";")
            var red = 0
            var blue = 0
            var green = 0
            for (cubs in cubeSet){
                var cub = cubs.split(",")
                cub.forEach {
                    var number = Regex("\\d+").findAll(it).first().value.toInt()
                    if(it.contains("blue")){
                        blue = increaseIfGreater(blue, number)
                    }else if(it.contains("red")){
                        red = increaseIfGreater(red, number)
                    }else if(it.contains("green")){
                        green = increaseIfGreater(green, number)
                    }
                }
            }
            sumOfPowers += red * green * blue
        }

        return sumOfPowers
    }

    fun increaseIfGreater(toBeIncreased: Int, increase: Int) : Int{
        if(increase > toBeIncreased){
            return increase
        }
        return toBeIncreased
    }
}