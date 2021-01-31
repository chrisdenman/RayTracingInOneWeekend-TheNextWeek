import Vec3.Companion.ZERO
import Vec3.Companion.boundedRandomComponents
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.random.Random.Default.nextDouble

class Renderer(private val outputLocation: File) {

    companion object {
        private const val aspectRatio: Double = 3.0 / 2.0
        private const val fieldOfViewDegrees: Double = 20.0
        private const val maxDepth = 50
        private const val samplesPerPixel = 50
        private const val imageWidth = 300
        private const val imageHeight = (imageWidth / aspectRatio).toInt()

        fun makeFinalScene(): World {
            val hittables = mutableListOf<Hittable>()
            val groundMaterial = Lambertian(Colour(0.5, 0.5, 0.5))
            hittables.add(Sphere(Point3(0, -1000, 0), 1000.0, groundMaterial))

            for (a in -11 until 11)  {
                for (b in -11 until 11) {
                    val chooseMat = nextDouble()
                    val center = Point3(a + 0.9 * nextDouble(), 0.2, b + 0.9 * nextDouble())

                    if (((center - Point3(4.0, 0.2, 0.0)).magnitude) > 0.9 ) {
                        if (chooseMat < 0.8) {
                            hittables.add(
                                Sphere(
                                    center,
                                    0.2,
                                    Lambertian(Vec3.randomUnitComponents * Vec3.randomUnitComponents)
                                )
                            )
                        } else if (chooseMat < 0.95) {
                            hittables.add(Sphere(center, 0.2, Metal(boundedRandomComponents(0.5, 1.0), nextDouble(0.0, 0.5))))
                        } else {
                            hittables.add(Sphere(center, 0.2, Dielectric(1.5)))
                        }
                    }
                }
            }

            hittables.add(Sphere(Point3(0, 1, 0), 1.0, Dielectric(1.5)))
            hittables.add(Sphere(Point3(-4, 1, 0), 1.0, Lambertian(Colour(0.4, 0.2, 0.1))))
            hittables.add(Sphere(Point3(4, 1, 0), 1.0, Metal(Colour(0.7, 0.6, 0.5), 0.0)))

            return World(hittables)
        }
    }


    private val world: World

    private val camera: Camera

    init {
        val lookFrom = Point3(13, 2, 3)
        val lookAt = Point3(0, 0, 0)
        val vup = Vec3(0, 1, 0)
        val focusDistance = 10.0
        val aperture = 0.1
        camera = Camera(
            lookFrom,
            lookAt,
            vup,
            fieldOfViewDegrees,
            aspectRatio,
            aperture,
            focusDistance)

        world = makeFinalScene()
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
                    val start = System.currentTimeMillis()
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
                    val end = System.currentTimeMillis()
                    println("Elapsed time ${(end - start)} mS")
                }
                println("Done.")
            }
        }
    }
}

fun main() {
    Renderer(File("./results/finalScene.ppm")).render()
}
