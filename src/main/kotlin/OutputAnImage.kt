import java.io.File

class OutputAnImage(imageWidth: Int, imageHeight: Int, outputLocation: File) {
    init {
        outputLocation.bufferedWriter().use {
            it.run {
                write("P3\n")
                write( "$imageWidth\n")
                write( "$imageHeight\n")
                write( "255\n")

                for (y in (imageHeight - 1).downTo(0)) {
                    println("${y + 1}/${imageHeight} scan lines remaining.")
                    for (x in 0 until imageWidth) {
                        writeColour(
                            Colour(x.toDouble() / (imageWidth - 1), y.toDouble() / (imageHeight - 1), 0.25)
                        )
                    }
                }
                println("Done.")
            }
        }
    }
}

fun main() {
    OutputAnImage(16, 16, File("./16x16.ppm"))
}
