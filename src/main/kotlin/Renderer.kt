import java.io.File

class Renderer(private val imageWidth: Int, private val outputLocation: File) {

    private val world = World(listOf(
        Sphere(Point3(0, 0, -1), 0.5),
        Sphere(Point3(0.0, -100.5, -1.0), 100.0)
    ))

    // Image
    val aspectRatio: Double = 16.0 / 9.0
    val imageHeight = (imageWidth / aspectRatio).toInt()

    // Camera
    val viewportHeight = 2.0
    val viewportWidth = aspectRatio * viewportHeight
    val focalLength = 1.0

    // Constants
    val origin = Point3.ZERO
    val horizontal = Vec3(viewportWidth, 0.0, 0.0)
    val vertical = Vec3(0.0, viewportHeight, 0.0)
    val lowerLeftCorner = origin - (horizontal / 2.0) - (vertical / 2.0) - Vec3(0.0, 0.0, focalLength)

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
                        val u = x.toDouble() / (imageWidth - 1)
                        val v = y.toDouble() / (imageHeight - 1)
                        val r = Ray(origin, lowerLeftCorner + (u * horizontal)  + (v * vertical)) // @todo this is the error line
                        writeColour(r.colour(world))
                    }
                }
                println("Done.")
            }
        }
    }
}

fun main() {
    Renderer(400, File("./results/400_sphere_normals_ground.ppm")).render()
}
