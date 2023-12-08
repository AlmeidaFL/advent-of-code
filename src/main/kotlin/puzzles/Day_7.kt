package puzzles

import PuzzleDay

class Day_7: PuzzleDay {

    val handMap = mapOf(
        Hand.FiveOfKind()::class.java to 1,
        Hand.FourOfKind()::class.java to 2,
        Hand.FullHouse()::class.java to 3,
        Hand.ThreeOfKind()::class.java to 4,
        Hand.TwoPair()::class.java to 5,
        Hand.OnePair()::class.java to 6,
        Hand.HighHand()::class.java to 7,
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
        var handList = mutableListOf<Hand>().apply {
            splittedInput.forEach { it ->
                var (cardString, bind) = it.split(" ")
                var hand = Hand.getHandType(cardString).also {
                    it.bind = bind
                }
                add(hand)
            }
        }

        var cardsSum = 0
        handList.apply {
            sortWith(getComparator())
            forEachIndexed { index, card ->
                cardsSum += card.bind.toInt() * (index + 1)
            }
        }

        return cardsSum
    }

    override fun puzzleTwo(input: String): Any? {
        var splittedInput = input.split("\r\n")
        var handList = mutableListOf<Hand>().apply {
            splittedInput.forEach { it ->
                var (cardString, bind) = it.split(" ")
                var upListWithJokerRule: (MutableList<Pair<Char, Int>>, Int?) -> Unit = { list, jokerValue ->
                    list.removeIf {
                        it.first == 'J'
                    }
                    list[0] = Pair(list[0].first, list[0].second + (jokerValue ?: 0))
                }
                var hand = Hand.getHandType(cardString, upListWithJokerRule).also {
                    it.bind = bind
                }
                add(hand)
            }
        }

        var cardsSum = 0
        handList.apply {
            sortWith(getComparator(withJokerRule = true))
            forEachIndexed { index, card ->
                cardsSum += card.bind.toInt() * (index + 1)
            }
        }

        return cardsSum
    }

    fun getComparator(withJokerRule: Boolean = false): Comparator<Hand>{
        var usedMap = if(!withJokerRule) charMap else charMapJoker
        val handComparator = Comparator<Hand> { cardOne, cardTwo ->
            if (cardOne::class.java == cardTwo::class.java) {
                for ((index, char) in cardOne.card.withIndex()) {
                    if (usedMap[char]!! > usedMap[cardTwo.card[index]]!!) {
                        return@Comparator 1
                    }
                    if (usedMap[char]!! < usedMap[cardTwo.card[index]]!!) {
                        return@Comparator -1
                    }
                }
            }

            if(handMap[cardOne::class.java]!! > handMap[cardTwo::class.java]!!){
                return@Comparator -1
            }else{
                return@Comparator 1
            }
        }

        return handComparator
    }

    sealed class Hand {
        var card: String = ""
        var bind: String = ""

        companion object {
            fun getHandType(card: String, jokerFunction: ((MutableList<Pair<Char, Int>>, Int?) -> Unit)? = null): Hand {
                val charWithInt = mutableMapOf<Char, Int>()
                for (value in card) {
                    if (!charWithInt.containsKey(value)) {
                        charWithInt[value] = 1
                    } else {
                        charWithInt[value] = charWithInt[value]!! + 1
                    }
                }
                val list = charWithInt.toList().sortedByDescending {
                    it.second
                }.toMutableList()

                if (charWithInt.count() > 1) {
                    jokerFunction?.invoke(list, charWithInt['J'])
                }

                return if (list[0].second == 5) {
                    FiveOfKind().also { it.card = card }
                } else if (list[0].second == 4 && list[1].second == 1) {
                    FourOfKind().also { it.card = card }
                } else if (list[0].second == 3 && list[1].second == 2) {
                    FullHouse().also { it.card = card }
                } else if (list[0].second == 3) {
                    ThreeOfKind().also { it.card = card }
                } else if (list[0].second == 2 && list[1].second == 2) {
                    TwoPair().also { it.card = card }
                } else if (list[0].second == 2 && list[1].second == 1 && list[2].second == 1) {
                    OnePair().also { it.card = card }
                } else {
                    HighHand().also { it.card = card }
                }
            }
        }

        class FiveOfKind: Hand();
        class FourOfKind: Hand()
        class FullHouse: Hand()
        class ThreeOfKind: Hand()
        class TwoPair: Hand()
        class OnePair: Hand()
        class HighHand: Hand()
    }
}