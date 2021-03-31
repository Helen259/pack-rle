import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import java.io.File

class runner:CliktCommand(){
        val mode by option().switch(
            "-u" to "unpack",
            "-z" to "pack"
        ).default("unknown")
        val input by option(
            "--input"
        ).required()
        val output by option(
        "--out"
    ).defaultLazy {input}
    override fun run() {
        if (mode == "pack") pack(input, output)
        if (mode == "unpack") unpack(input, output)
    }

}

/*fun pack (inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    File(inputName).bufferedReader().useLines { inpLines ->
        inpLines.forEach { line ->
            val res = StringBuilder()
            var i = 0
            var countRepeat = 1
            var countNon = MutableList()
            while (i < line.length) {

                while (i != line.length - 1 && line[i] == line[i + 1] && countRepeat <= 9) {
                    countRepeat++
                    i++
                }
                while (i != line.length - 1 && line[i] != line[i + 1] && countNon <= 9)
                    countNon--
                    i++
                if (countRepeat != 1) {
                    res.append(countRepeat).append(line[i])
                    countRepeat = 1
                }
                else {
                    res.append(countNon).append(line[i])
                    countNon = -1
                }
                i++
            }
            writer.write(res.toString())
            writer.close()
        }
    }
} */
fun pack(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val encodedLines = mutableListOf<String>()
    File(inputName).bufferedReader().useLines { inpLines ->
        inpLines.forEach { line ->
            val res = StringBuilder()
            var i = 0
            var countRepeat = 1
            val uniqueValues = mutableListOf<Char>()
            while (i < line.length) {
                //var isUnique = false
                while (i < line.length && (i == line.length - 1 || line[i] != line[i + 1] && uniqueValues.size < 9)) {
                    //if (!isUnique) isUnique = true
                    uniqueValues.add(line[i])
                    i++
                }
               // if (isUnique) i++
                if (uniqueValues.isNotEmpty()) {
                    res.append("-${uniqueValues.size}${uniqueValues.joinToString("")}")
                    uniqueValues.clear()
                }
                if (i >= line.length) break

                val repeatingCharacter = line[i]
                var isRepeating = false
                while (i < line.length && (i == line.length - 1 || line[i] == line[i + 1] && countRepeat < 9)) {
                    if (!isRepeating) isRepeating = true
                    countRepeat++
                    i++
                }
                if (isRepeating) i++

                if (countRepeat != 1) {
                    if (i >= line.length) countRepeat -= 1
                    res.append(countRepeat).append(repeatingCharacter)
                    countRepeat = 1
                }
            }
            encodedLines.add(res.toString())
        }
    }
    writer.write(encodedLines.joinToString("\n"))
    writer.close()
}
fun unpack(inputName: String, outputName: String) {
    var writer = File(outputName).bufferedWriter()
    var encodedLines= mutableListOf<String>()
    File(inputName).bufferedReader().useLines { inpLines ->
        inpLines.forEach { line ->
            val res = StringBuilder()
            val line2 = line.map{it.toString()}.toMutableList()
            while (line2.isNotEmpty()) {
                if (line2[0] == "-") {
                    val deleteS = line2[1].toInt()
                    line2.removeAt(0)
                    line2.removeAt(0)
                    for (i in 1..deleteS) {
                        res.append(line2[0])
                        line2.removeAt(0)
                    }
                }
                if (line2.isEmpty() ) break
                val repeatS = line2[0].toInt()
                line2.removeAt(0)
                for (i in 1..repeatS) {
                    res.append(line2[0])
                }
                line2.removeAt(0)
            }
            encodedLines.add(res.toString())
        }
    }
    writer.write(encodedLines.joinToString("\n"))
    writer.close()
}


