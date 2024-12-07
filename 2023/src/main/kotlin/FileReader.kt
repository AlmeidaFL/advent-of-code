import java.io.File

const val relativePath = "src/main/data/"

object FileReader {
    fun readFile(input: String): String{
        try{
            val file = File(relativePath + input)
            return file.readText()
        }catch (e: Exception){
            println("File reading failed")
        }

        throw Exception("An error occurred when reading file")
    }
}