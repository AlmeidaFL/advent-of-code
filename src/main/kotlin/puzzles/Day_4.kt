package puzzles

import PuzzleDay

class Day_4: PuzzleDay {
    override fun puzzleOne(input: String): Any? {
        var totalSum = 0
        var cardLists = input
            .split("\r\n")
            .map{it ->
                removeUntil(it)
            }
            .forEach {line ->
                var (winningNumber, numbersObtained) = getDivisionList(line)

                var double = false
                var sumD = 0
                numbersObtained.forEach {
                    if(winningNumber.contains(it) && !double){
                        sumD += 1
                        double = true
                    }else if(winningNumber.contains(it) && double){
                        sumD *= 2
                    }
                }
                totalSum += sumD
            }

        return totalSum
    }

    override fun puzzleTwo(input: String): Any? {
        var map = mutableMapOf<String, Int>()

        var cardLists = input
            .split("\r\n")
            .map{it ->
                removeUntil(it)
            }
            .forEachIndexed { index, line ->
                var (winningNumber, numbersObtained) = getDivisionList(line)

                var numbersOfMatch = 0
                numbersObtained.forEach {
                    if(winningNumber.contains(it)){
                        numbersOfMatch += 1
                    }
                }

                var realIndex = index + 1
                var cardName = "Card ${realIndex}"
                if(!map.containsKey(cardName)){
                    map.put(cardName, 0)
                }else if(map.containsKey(cardName)){
                    for(j in 1..map[cardName]!!){
                        for(i in (realIndex+1)..(realIndex+numbersOfMatch)){
                            var newCardName = "Card ${i}"
                            if(!map.containsKey(newCardName)){
                                map[newCardName] = 1
                            }else if(map.containsKey(newCardName)){
                                map[newCardName] = map[newCardName]!! + 1
                            }
                        }
                    }
                }

                for(i in (realIndex+1)..(realIndex+numbersOfMatch)){
                    var newCardName = "Card ${i}"
                    if(!map.containsKey(newCardName)){
                        map[newCardName] = 1
                    }else if(map.containsKey(newCardName)){
                        map[newCardName] = map[newCardName]!! + 1
                    }
                }
            }

        var sum = 0
        map.forEach { t, u ->
            sum += u
        }

        sum += map.count()
        return sum
    }

    fun removeUntil(cardList: String): String{
        var list = cardList.toMutableList()
        do{
            var char = list[0]
            list.removeFirst()
        }while(char != ':')

        return String(list.toCharArray())
    }

    fun removeUntilWithCardName(cardList: String): Pair<String,String>{
        var list = cardList.toMutableList()
        var cardName = mutableListOf<Char>()
        do{
            var char = list[0]
            cardName.add(list.removeFirst())
        }while(char != ':')

        cardName.removeLast()

        return Pair(String(cardName.toCharArray()), String(list.toCharArray()))
    }

    fun getDivisionList(cardLists: String): Pair<List<String>, List<String>>{
        var list = cardLists.split("|").toMutableList()

        var list_1 = Regex("\\d+").findAll(list[0]).map { it.value }.toList()
        var list_2 = Regex("\\d+").findAll(list[1]).map { it.value }.toList()

        return Pair(list_1, list_2)
    }

    class Node(var cardName: String) {
        var copies = 1

        var listOfChild = mutableListOf<Node>()

        override fun equals(other: Any?): Boolean {
            if(other !is Node) return false
            return other.cardName == this.cardName
        }
    }

}