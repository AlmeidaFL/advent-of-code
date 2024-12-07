package puzzles

import PuzzleDay

class Day_19: PuzzleDay {
    override fun puzzleOne(input: String): Any? {
        val inputSeparated = input
            .split("\r\n")

        val workflow = getWorkflow(inputSeparated)

        val partsString = getPartsAsSeparatedStrings(inputSeparated)

        val parts = getParts(partsString)

        val acceptedList = mutableListOf<Int>()
        parts.forEachIndexed { index, it ->
            if(walkThroughWorkflow(workflow, it)){
                acceptedList += index
            }
        }
        return acceptedList.map {
            Regex("\\d+").findAll(partsString[it]).map { it.value.toInt() }.reduce { acc, i ->  acc + i}
        }.reduce { acc, i ->  acc + i}
    }

    private fun getPartsAsSeparatedStrings(inputSeparated: List<String>) =
        inputSeparated.subList(inputSeparated.indexOf("") + 1, inputSeparated.count())

    private fun getWorkflow(inputSeparated: List<String>): Map<String, List<String>> {
        val workflow = inputSeparated.subList(0, inputSeparated.indexOf("")).map {
            val string = StringBuilder(Regex(".*?\\{").find(it)!!.value.removeSuffix("{"))
            val rules = Regex("\\{.*\\}").find(it)!!.value
                .removeSurrounding("{", "}")
                .split(",")
            string.toString() to rules
        }.toMap()
        return workflow
    }

    private fun getParts(partsString: List<String>): List<Part> {
        val parts = partsString
            .map {
                val splited = it
                    .removeSurrounding("{", "}")
                    .split(",")
                Part(
                    x = Regex("\\d+").find(splited[0])!!.value.toInt(),
                    m = Regex("\\d+").find(splited[1])!!.value.toInt(),
                    a = Regex("\\d+").find(splited[2])!!.value.toInt(),
                    s = Regex("\\d+").find(splited[3])!!.value.toInt(),
                )
            }
        return parts
    }

    fun walkThroughWorkflow(workflows: Map<String, List<String>>, part: Part): Boolean{
        var workflowToSearch = "in"
        do {
            val actualWorkflow = workflows[workflowToSearch]!!
            for (i in 0..<actualWorkflow.count()) {
                if(Regex(":").find(actualWorkflow[i]) == null){
                    if(actualWorkflow[i].count() == 1){
                        when(actualWorkflow[i]){
                            "A" -> return true
                            "R" -> return false
                        }
                    }
                    workflowToSearch = actualWorkflow[i]
                    continue
                }
                val valueToCompare = Regex("\\d+").find(actualWorkflow[i])!!.value.toInt()
                val argumentValue = when (actualWorkflow[i][0]) {
                    'm' -> part.m
                    'x' -> part.x
                    's' -> part.s
                    'a' -> part.a
                    else -> throw Exception("Should not happen when converting argument value ${actualWorkflow[i]}")
                }
                val construct = actualWorkflow[i][1]
                if (construct == '>') {
                    if (argumentValue > valueToCompare) {
                        when (val result = Regex(":.*").find(actualWorkflow[i])!!.value.removePrefix(":")) {
                            "A" -> return true
                            "R" -> return false
                            else -> {
                                workflowToSearch = result
                                break
                            }
                        }
                    }
                }
                else if (construct == '<'){
                    if (argumentValue < valueToCompare) {
                        when (val result = Regex(":.*").find(actualWorkflow[i])!!.value.removePrefix(":")) {
                            "A" -> return true
                            "R" -> return false
                            else -> {
                                workflowToSearch = result
                                break
                            }
                        }
                    }
                }
            }
        }while(true)
    }

    override fun puzzleTwo(input: String): Any? {
        val inputSeparated = input
            .split("\r\n")

        val workflow = getWorkflow(inputSeparated)
        val parts = getParts(getPartsAsSeparatedStrings(inputSeparated))

        return ""
    }

    data class Part(val x: Int, val m: Int, val a: Int, val s: Int)
}