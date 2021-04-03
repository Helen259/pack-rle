package packrle

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.*
import java.io.File
import java.io.FileNotFoundException
import java.lang.IllegalArgumentException

class Runner : CliktCommand() {
    private val isPacked by option("-z").flag()
    private val isUnpacked by option("-u").flag()
    private val input by argument()
    private val output by option(
            "-out"
    )

    override fun run() {
        if ((isPacked && isUnpacked) || (!isPacked && !isUnpacked)) {
            throw IllegalArgumentException("Неправильно указан режим работы")
        }
        try {
            if (isPacked) pack(input, output ?: "out$input")
            if (isUnpacked) unpack(input, output ?: "out$input")
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalArgumentException("Ошибка во время обработки, возможно неправильный формат файла")
        } catch (e: FileNotFoundException) {
            throw IllegalArgumentException("Не был найден файл")
        }
    }
}

fun pack(inputName: String, outputName: String) {
    val encodedLines = mutableListOf<String>()
    File(inputName).bufferedReader().useLines { inpLines ->
        inpLines.forEach { line ->
            val res = StringBuilder()
            var i = 0
            var countRepeat = 1
            val uniqueValues = mutableListOf<Char>()
            while (i < line.length) {
                while (i < line.length && (i == line.length - 1 || line[i] != line[i + 1])) {
                    uniqueValues.add(line[i])
                    i++
                }
                if (uniqueValues.isNotEmpty()) {
                    res.append("-${uniqueValues.size}${uniqueValues.joinToString("")}")
                    uniqueValues.clear()
                }
                if (i >= line.length) break

                val repeatingCharacter = line[i]
                var isRepeating = false
                while (i < line.length && (i == line.length - 1 || line[i] == line[i + 1])) {
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
    File(outputName).bufferedWriter().use {
        it.write(encodedLines.joinToString("\n"))
    }
}

fun unpack(inputName: String, outputName: String) {
    val encodedLines = mutableListOf<String>()
    File(inputName).bufferedReader().useLines { inpLines ->
        inpLines.forEach { line ->
            val res = StringBuilder()
            val line2 = line.map { it.toString() }
            var index = 0
            while (index < line.length) {
                if (line2[index] == "-") {
                    index++
                    var deleteS = 0
                    while (line2[index].toIntOrNull() != null) {
                        deleteS = deleteS * 10 + line2[index].toInt()
                        index++
                    }
                    for (i in 1..deleteS) {
                        res.append(line2[index])
                        index++
                    }
                }
                if (index >= line.length) break
                var repeatS = 0
                while (line2[index].toIntOrNull() != null) {
                    repeatS = repeatS * 10 + line2[index].toInt()
                    index++
                }
                for (i in 1..repeatS) {
                    res.append(line2[index])
                }
                index++
            }
            encodedLines.add(res.toString())
        }
    }
    File(outputName).bufferedWriter().use {
        it.write(encodedLines.joinToString("\n"))
    }
}


