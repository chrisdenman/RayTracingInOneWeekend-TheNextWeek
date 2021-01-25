import Vec3.Companion.ZERO
import java.io.File

class Renderer(private val outputLocation: File) {

    companion object {
        private const val maxDepth = 50
        private const val samplesPerPixel = 100
        private const val imageWidth = 400
        private const val imageHeight = (imageWidth / Camera.aspectRatio).toInt()
    }

    private val world = World(listOf(
        Sphere(Point3(0, 0, -1), 0.5),
        Sphere(Point3(0.0, -100.5, -1.0), 100.0)
    ))

    private val camera = Camera()

    fun render() {
        outputLocation.bufferedWriter().use {
            it.run {
                write("P3\n")
                write( "$imageWidth\n")
                write( "$imageHeight\n")
                write( "255\n")
                for (y in (imageHeight - 1).downTo(0)) {
                    println("${y + 1}/${imageHeight} scan lines remaining.")
                    for (x in 0 until imageWidth) {
                        var pixelColour = ZERO
                        for (sample in 0 until samplesPerPixel) {
                            val u = (x + Math.random()) / (imageWidth - 1)
                            val v = (y + Math.random()) / (imageHeight - 1)
                            val r = camera.getRay(u, v)
                            pixelColour += r.colour(world, maxDepth)
                        }
                        writeColour(pixelColour, samplesPerPixel)
                    }
                }
                println("Done.")
            }
        }
    }
}

fun main() {
    Renderer(File("./results/diffuse_sphere_gamma_corrected_acne_hemispherical.ppm")).render()
}
