package uk.co.ceilingcat.rrd.monolith

import java.io.File

class OutputAnImage(imageWidth: Int, imageHeight: Int, outputLocation: File) {
    init {
        outputLocation.bufferedWriter().use {
            it.write("P3\n")
            it.write( "$imageWidth\n")
            it.write( "$imageHeight\n")
            it.write( "255\n")

            for (y in (imageHeight - 1).downTo(0)) {
                println("${y + 1}/${imageHeight} scan lines remaining.")
                for (x in 0 until imageWidth) {
                    val r = x.toDouble() / (imageWidth - 1)
                    val g = y.toDouble() / (imageHeight - 1)
                    val b = 0.25

                    val ir = (r * 255.999).toInt()
                    val ig = (g * 255.999).toInt()
                    val ib = (b * 255.999).toInt()

                    it.write("$ir $ig $ib\n")
                }
            }
            println("Done.")
        }
    }
}

fun main() {
    OutputAnImage(16, 16, File("./16x16.ppm"))
}
