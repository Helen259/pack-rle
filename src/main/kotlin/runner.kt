import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*

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

fun pack(input:String, output:String){
print("Pack")
}
fun unpack(input:String, output:String){
print("Unpack")
}