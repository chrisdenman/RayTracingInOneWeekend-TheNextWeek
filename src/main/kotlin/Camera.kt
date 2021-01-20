class Camera {

    fun getRay(u: Double, v: Double) = Ray(
        origin,
        lowerLeftCorner + (u * horizontal) + (v * vertical) - origin
    )

    companion object {
        const val aspectRatio: Double = 16.0 / 9.0
        private const val viewportHeight = 2.0
        private const val viewportWidth = aspectRatio * viewportHeight
        private const val focalLength = 1.0
        private val origin = Point3.ZERO
        private val horizontal = Vec3(viewportWidth, 0.0, 0.0)
        private val vertical = Vec3(0.0, viewportHeight, 0.0)
        private val lowerLeftCorner = origin - (horizontal / 2.0) - (vertical / 2.0) - Vec3(0.0, 0.0, focalLength)
    }
}

fun Double.clamp(minInclusive: Double, maxInclusive: Double) = when {
    this < minInclusive -> minInclusive
    this > maxInclusive -> maxInclusive
    else -> this
}
