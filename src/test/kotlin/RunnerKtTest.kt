import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class RunnerKtTest{
    @Test
    fun testPack(){
        val newFile = File("inpack.txt")
        newFile.writeText("аааагнр")
        pack("inpack.txt", "outpack.txt")
       val file2= File("outpack.txt").readText()
        assertEquals( "4а-3гнр", file2)
    }
    @Test
    fun testPack2(){
        val newFile = File("inpack2.txt")
        newFile.writeText("ппппрр\nннн")
        pack("inpack2.txt", "outpack2.txt")
        val file2= File("outpack2.txt").readText()
        assertEquals( "4п2р\n3н", file2)
    }
    @Test
    fun testPack3(){
        val newFile = File("inpack3.txt")
        newFile.writeText("а")
        pack("inpack3.txt", "outpack3.txt")
        val file2= File("outpack3.txt").readText()
        assertEquals( "-1а", file2)
    }
}