package puzzles

import PuzzleDay

class Day_7: PuzzleDay {

    val cardMap = mapOf<Card, Int>(
        Card.FiveOfKind() to 1,
        Card.FourOfKind() to 2,
        Card.FullHouse() to 3,
        Card.ThreeOfKind() to 4,
        Card.TwoPair() to 5,
        Card.OnePair() to 6,
        Card.HighCard() to 7,
    )

    val charMap = mapOf<Char, Int>(
        '2' to 1,
        '3' to 2,
        '4' to 3,
        '5' to 4,
        '6' to 5,
        '7' to 6,
        '8' to 7,
        '9' to 8,
        'T' to 10,
        'J' to 11,
        'Q' to 12,
        'K' to 13,
        'A' to 14
    )

    val charMapJoker = mapOf<Char, Int>(
        'J' to 0,
        '2' to 1,
        '3' to 2,
        '4' to 3,
        '5' to 4,
        '6' to 5,
        '7' to 6,
        '8' to 7,
        '9' to 8,
        'T' to 10,
        'Q' to 11,
        'K' to 12,
        'A' to 13
    )

    override fun puzzleOne(input: String): Any? {
        var splittedInput = input.split("\r\n")
        var fiveList = mutableListOf<Card>()
        var fourList = mutableListOf<Card>()
        var fullList = mutableListOf<Card>()
        var threeList = mutableListOf<Card>()
        var twoList = mutableListOf<Card>()
        var oneList = mutableListOf<Card>()
        var highList = mutableListOf<Card>()

        splittedInput.forEach {
            var (cardString, bind) = it.split(" ")
            var card = Card.checkType(cardString).also {
                it.bind = bind
            }
            when {
                card is Card.FiveOfKind -> fiveList.add(card)
                card is  Card.FourOfKind -> fourList.add(card)
                card is  Card.FullHouse -> fullList.add(card)
                card is  Card.ThreeOfKind -> threeList.add(card)
                card is  Card.TwoPair -> twoList.add(card)
                card is  Card.OnePair -> oneList.add(card)
                card is  Card.HighCard -> highList.add(card)
            }
        }

        var fiveListSorted = fiveList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCard(cardOne, cardTwo)
        }

        var fourLissSorted = fourList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCard(cardOne, cardTwo)
        }

        var fullListSorted = fullList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCard(cardOne, cardTwo)
        }

        var threeListSorted = threeList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCard(cardOne, cardTwo)
        }

        var twoListSorted = twoList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCard(cardOne, cardTwo)
        }

        var oneListSorted = oneList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCard(cardOne, cardTwo)
        }

        var highListSorted = highList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCard(cardOne, cardTwo)
        }

        var index = 1
        var sum = 0
        highListSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            index += 1
        }
        oneListSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            index += 1
        }
        twoListSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            index += 1
        }
        threeListSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            index += 1
        }
        fullListSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            index += 1
        }
        fourLissSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            index += 1
        }
        fiveListSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            index += 1
        }

        return sum
    }

    fun compareCard(cardOne: Card, cardTwo: Card): Int{
        for ((index, char) in cardOne.card.withIndex()) {
            if(charMap[char]!! > charMap[cardTwo.card[index]]!!){
                return 1
            }
            if(charMap[char]!! < charMap[cardTwo.card[index]]!!){
                return -1
            }
        }
        return 1
    }

    fun compareCardJoker(cardOne: Card, cardTwo: Card): Int{
        for ((index, char) in cardOne.card.withIndex()) {
            if(charMapJoker[char]!! > charMapJoker[cardTwo.card[index]]!!){
                return 1
            }
            if(charMapJoker[char]!! < charMapJoker[cardTwo.card[index]]!!){
                return -1
            }
        }
        return 1
    }

    override fun puzzleTwo(input: String): Any? {
        var splittedInput = input.split("\r\n")
        var fiveList = mutableListOf<Card>()
        var fourList = mutableListOf<Card>()
        var fullList = mutableListOf<Card>()
        var threeList = mutableListOf<Card>()
        var twoList = mutableListOf<Card>()
        var oneList = mutableListOf<Card>()
        var highList = mutableListOf<Card>()

        splittedInput.forEach {
            var (cardString, bind) = it.split(" ")
            var card = Card.checkTypeJoker(cardString).also {
                it.bind = bind
            }
            when {
                card is Card.FiveOfKind -> fiveList.add(card)
                card is  Card.FourOfKind -> fourList.add(card)
                card is  Card.FullHouse -> fullList.add(card)
                card is  Card.ThreeOfKind -> threeList.add(card)
                card is  Card.TwoPair -> twoList.add(card)
                card is  Card.OnePair -> oneList.add(card)
                card is  Card.HighCard -> highList.add(card)
            }
        }

        var fiveListSorted = fiveList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCardJoker(cardOne, cardTwo)
        }

        var fourLissSorted = fourList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCardJoker(cardOne, cardTwo)
        }

        var fullListSorted = fullList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCardJoker(cardOne, cardTwo)
        }

        var threeListSorted = threeList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCardJoker(cardOne, cardTwo)
        }

        var twoListSorted = twoList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCardJoker(cardOne, cardTwo)
        }

        var oneListSorted = oneList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCardJoker(cardOne, cardTwo)
        }

        var highListSorted = highList.sortedWith { cardOne, cardTwo ->
            return@sortedWith compareCardJoker(cardOne, cardTwo)
        }

        var index = 1
        var sum = 0
        highListSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            println("Card: ${card.card} sum $sum")
            index += 1
        }
        oneListSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            println("Card: ${card.card} sum $sum")

            index += 1
        }
        twoListSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            println("Card: ${card.card} sum $sum")

            index += 1
        }
        threeListSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            println("Card: ${card.card} sum $sum")

            index += 1
        }
        fullListSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            println("Card: ${card.card} sum $sum")

            index += 1
        }
        fourLissSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            println("Card: ${card.card} sum $sum")

            index += 1
        }
        fiveListSorted.forEachIndexed { rank, card ->
            sum += card.bind.toInt() * (index)
            println("Card: ${card.card} sum $sum")

            index += 1
        }

        return sum
    }

    sealed class Card {
        var card: String = ""
        var bind: String = ""

        companion object {
            fun checkType(card: String): Card{
                val charWithInt = mutableMapOf<Char, Int>()
                for (value in card){
                   if (!charWithInt.containsKey(value)){
                       charWithInt[value] = 1
                   }else{
                       charWithInt[value] = charWithInt[value]!! + 1
                   }
                }
                val list = charWithInt.toList().sortedByDescending {
                    it.second
                }

                if(card == "JJQ34"){
                    var x = 1
                }
                return if(list[0].second == 5){
                    FiveOfKind().also { it.card = card }
                }else if(list[0].second == 4 && list[1].second == 1){
                    FourOfKind().also { it.card = card }
                }else if(list[0].second == 3 && list[1].second == 2){
                    FullHouse().also { it.card = card }
                }else if(list[0].second == 3){
                    ThreeOfKind().also { it.card = card }
                }else if(list[0].second == 2 && list[1].second == 2){
                    TwoPair().also { it.card = card }
                }else if(list[0].second == 2 && list[1].second == 1 && list[2].second == 1){
                    OnePair().also { it.card = card }
                }else{
                    HighCard().also { it.card = card }
                }
            }

            fun checkTypeJoker(card: String): Card{
                val charWithInt = mutableMapOf<Char, Int>()
                for (value in card){
                    if (!charWithInt.containsKey(value)){
                        charWithInt[value] = 1
                    }else{
                        charWithInt[value] = charWithInt[value]!! + 1
                    }
                }
                val list = charWithInt.toList().sortedByDescending {
                    it.second
                }.toMutableList()

                if(!charWithInt.containsKey('J')
                    || charWithInt.count() == 1){
                    return if(list[0].second == 5){
                        FiveOfKind().also { it.card = card }
                    }else if(list[0].second == 4 && list[1].second == 1){
                        FourOfKind().also { it.card = card }
                    }else if(list[0].second == 3 && list[1].second == 2){
                        FullHouse().also { it.card = card }
                    }else if(list[0].second == 3){
                        ThreeOfKind().also { it.card = card }
                    }else if(list[0].second == 2 && list[1].second == 2){
                        TwoPair().also { it.card = card }
                    }else if(list[0].second == 2 && list[1].second == 1 && list[2].second == 1){
                        OnePair().also { it.card = card }
                    }else{
                        HighCard().also { it.card = card }
                    }
                }

                var jokerValue = charWithInt['J']
                list.removeIf {
                    it.first == 'J'
                }
                list[0] = Pair(list[0].first, list[0].second + jokerValue!!)
                return if(list[0].second == 5){
                    FiveOfKind().also { it.card = card }
                }else if(list[0].second == 4 && list[1].second == 1){
                    FourOfKind().also { it.card = card }
                }else if(list[0].second == 3 && list[1].second == 2){
                    FullHouse().also { it.card = card }
                }else if(list[0].second == 3){
                    ThreeOfKind().also { it.card = card }
                }else if(list[0].second == 2 && list[1].second == 2){
                    TwoPair().also { it.card = card }
                }else if(list[0].second == 2 && list[1].second == 1 && list[2].second == 1){
                    OnePair().also { it.card = card }
                }else{
                    HighCard().also { it.card = card }
                }
            }
        }

        class FiveOfKind: Card();
        class FourOfKind: Card()
        class FullHouse: Card()
        class ThreeOfKind: Card()
        class TwoPair: Card()
        class OnePair: Card()
        class HighCard: Card()
    }
}