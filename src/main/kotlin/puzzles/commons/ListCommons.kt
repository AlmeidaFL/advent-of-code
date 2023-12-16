package puzzles.commons

fun List<CharSequence>.print(){
    this.forEach { it ->
        it.forEach {char ->
            print(char)
        }
        println()
    }
    println()
}
