package packrle

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import packrle.pack
import packrle.unpack
import java.io.File

class RunnerKtTest {
    @Test
    fun testPack() {
        val newFile = File("inpack.txt")
        newFile.writeText("аааагнр")
        pack("inpack.txt", "outpack.txt")
        val file2 = File("outpack.txt").readText()
        assertEquals("4а-3гнр", file2)
    }

    @Test
    fun testPack2() {
        val newFile = File("inpack2.txt")
        newFile.writeText("ппппрр\nннн")
        pack("inpack2.txt", "outpack2.txt")
        val file2 = File("outpack2.txt").readText()
        assertEquals("4п2р\n3н", file2)
    }

    @Test
    fun testPack3() {
        val newFile = File("inpack3.txt")
        newFile.writeText("аааааааааааааааааааааааааа")
        pack("inpack3.txt", "outpack3.txt")
        val file2 = File("outpack3.txt").readText()
        assertEquals("26а", file2)
    }

    @Test
    fun testPack4() {
        val newFile = File("inpack14.txt")
        newFile.writeText("абвгдббббааааппп")
        pack("inpack14.txt", "outpack14.txt")
        val file2 = File("outpack14.txt").readText()
        assertEquals("-5абвгд4б4а3п", file2)
    }

    @Test
    fun testUnpack() {
        val newFile = File("outpack4.txt")
        newFile.writeText("5а2г-4апро")
        unpack("outpack4.txt", "inpack4.txt")
        val file2 = File("inpack4.txt").readText()
        assertEquals("аааааггапро", file2)
    }

    @Test
    fun testUnpack2() {
        val newFile = File("outpack5.txt")
        newFile.writeText("5а2г\n6а")
        unpack("outpack5.txt", "inpack5.txt")
        val file2 = File("inpack5.txt").readText()
        assertEquals("ааааагг\nаааааа", file2)
    }

    @Test
    fun testUnpack3() {
        val newFile = File("outpack64.txt")
        newFile.writeText("13а")
        unpack("outpack64.txt", "inpack6.txt")
        val file2 = File("inpack6.txt").readText()
        assertEquals("ааааааааааааа", file2)
    }

    @Test
    fun testRunner() {
        val exception =
                assertThrows(IllegalArgumentException::class.java) { Runner().main(listOf("-out", "test.txt", "input.txt")) }
        assertEquals("Неправильно указан режим работы", exception.message)
    }

    @Test
    fun testRunner2() {
        val exception =
                assertThrows(IllegalArgumentException::class.java) { Runner().main(listOf("-z", "-u", "-out", "test.txt", "input.txt")) }
        assertEquals("Неправильно указан режим работы", exception.message)
    }

    @Test
    fun testRunner3() {
        val exception =
                assertThrows(IllegalArgumentException::class.java) { Runner().main(listOf("-z", "-out", "test.txt", "nonExistingFile.txt")) }
        assertEquals("Не был найден файл", exception.message)
    }
}