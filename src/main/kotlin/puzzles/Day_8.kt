package puzzles

import PuzzleDay

class Day_8: PuzzleDay {
    val lastNode = "ZZZ"
    override fun puzzleOne(input: String): Any? {
        var splittedInput = input.split("\r\n")
        val instructions = splittedInput[0]
        val nodesString = splittedInput.subList(2, splittedInput.count())

        var nodes = mutableMapOf<String, Node>()
        nodesString.forEach {
            var text = it.split("=")
            var initialNode = text[0].trim()
            var rest = text[1].trim()
            var left = rest.substring(1..3)
            var right = rest.substring(6..8)
            nodes[initialNode] = Node(initialNode, left, right)
        }

        var initialNode = nodes["AAA"]

        var steps = 0
        while(true){
            for(direction in instructions){
                initialNode = if(direction == 'L') initialNode!!.getLeft(nodes) else initialNode!!.getRight(nodes)
                steps++
                if(initialNode.name == lastNode){
                    return steps
                }
            }
        }

        return steps
    }

    override fun puzzleTwo(input: String): Any? {
        var splittedInput = input.split("\r\n")
        val instructions = splittedInput[0]
        val nodesString = splittedInput.subList(2, splittedInput.count())

        var nodes = mutableMapOf<String, Node>()
        nodesString.forEach {
            var text = it.split("=")
            var initialNode = text[0].trim()
            var rest = text[1].trim()
            var left = rest.substring(1..3)
            var right = rest.substring(6..8)
            nodes[initialNode] = Node(initialNode, left, right)
        }

        var startingPoints = mutableListOf<Node>().apply {
            nodes.forEach {
                if(it.key[2] == 'A'){
                    add(it.value)
                }
            }
        }

        var steps = 0L
        var unitDistaces = mutableListOf<Long>()
        var removedNodes = mutableListOf<Node>()
        run bloco@{
            while(true){
                for(direction in instructions){
                    steps++
                    for ((index, node) in startingPoints.withIndex()){
                        startingPoints[index] = if(direction == 'L') node.getLeft(nodes) else node.getRight(nodes)
                        if(startingPoints[index].name.endsInZ())
                        {
                            unitDistaces += steps
                            removedNodes += startingPoints[index]
                        }
                    }
                    removedNodes.forEach {
                        startingPoints.remove(it)
                    }
                    removedNodes.clear()
                    if(startingPoints.isEmpty()) return@bloco
                }
            }
        }

        return unitDistaces.getMMC()
    }

    fun List<Long>.getMMC(): Long{
        var mmc = this[0]
        for(i in 1..<this.count()){
            mmc = mmc * this[i] / mmc.getMDC(this[i])
        }
        return mmc
    }

    fun Long.getMDC(value: Long): Long{
        var rest = this % value
        if(rest == 0L) return value
        return value.getMDC(rest)
    }

    fun String.endsInZ() = this[2] == 'Z'

    data class Node(val name: String, var left: String, var right: String){
        fun getRight(map: Map<String, Node>): Node{
            return map[right]!!
        }

        fun getLeft(map: Map<String, Node>): Node{
            return map[left]!!
        }
    }
}