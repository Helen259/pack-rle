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

fun pack (inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    File(inputName).bufferedReader().useLines { inpLines ->
        inpLines.forEach { line ->
            val res = StringBuilder()
            var i = 0
            var countRepeat = 1
            var countNon = -1
            while (i < line.length-1) {

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
}
fun unpack(inputName: String, outputName: String) {
    var writer = File(outputName).bufferedWriter()
    File(inputName).bufferedReader().useLines { inpLines ->
        inpLines.forEach { line ->
            val res = StringBuilder()
            var i = 0
            var countRepeat = 1
            var countNon = -1
            while (i < line.length - 1) {


                writer.write(res.toString())
                writer.close()
            }
        }
    }
}