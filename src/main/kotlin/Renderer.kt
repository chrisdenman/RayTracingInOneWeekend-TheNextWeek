import Vec3.Companion.ZERO
import java.io.File

class Renderer(private val outputLocation: File) {

    companion object {
        private const val aspectRatio: Double = 3.0 / 2.0
        private const val fieldOfViewDegrees: Double = 20.0
        private const val maxDepth = 50
        private const val samplesPerPixel = 100
        private const val imageWidth = 1000
        private const val imageHeight = (imageWidth / aspectRatio).toInt()
    }

    private val groundMaterial = Lambertian(Colour(0.8, 0.8, 0.0))
    private val centerMaterial = Lambertian(Colour(0.1, 0.2, 0.5))
    private val leftMaterial = Dielectric(1.5)
    private val rightMaterial = Metal(Colour(0.8, 0.6, 0.2), 0.0)

    private val world = World(listOf(
        Sphere(Point3(0.0, -100.5, -1.0), 100.0, groundMaterial),
        Sphere(Point3(0.0, 0.0, -1.0), 0.5, centerMaterial),
        Sphere(Point3(-1.0, 0.0, -1.0), 0.5, leftMaterial),
        Sphere(Point3(-1.0, 0.0, -1.0), -0.45, leftMaterial),
        Sphere(Point3(1.0, 0.0, -1.0), 0.5, rightMaterial),
    ))

    private val camera: Camera

    init {
        val lookFrom = Point3(3, 3, 2)
        val lookAt = Point3(0, 0, -1)
        val vup = Vec3(0, 1, 0)
        val focusDistance = (lookFrom - lookAt).magnitude
        val aperture = 2.0
        camera = Camera(
            lookFrom,
            lookAt,
            vup,
            fieldOfViewDegrees,
            aspectRatio,
            aperture,
            focusDistance)
    }

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
    Renderer(File("./results/positionable_camera_from_at_fov_defocus_blur.ppm")).render()
}
