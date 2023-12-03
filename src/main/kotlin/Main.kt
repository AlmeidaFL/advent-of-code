fun main() {
    var puzzle = 3
    val puzzleInstance = Class
        .forName(
            puzzle.toFullClassName())
        .getConstructor()
        .newInstance() as PuzzleDay


//    println(puzzleInstance.puzzleOne(FileReader.readFile("${puzzle.toLowerClassName()}/input_1.txt")))
    println(puzzleInstance.puzzleTwo(FileReader.readFile("${puzzle.toLowerClassName()}/input_1.txt")))
}

fun Int.toFullClassName() = this.toClassName().toPackage()
fun Int.toClassName() = "Day_${this.toString()}"
fun Int.toLowerClassName() = this.toClassName().lowercase()
fun String.toPackage() = "puzzles.$this"
