import kotlin.math.tan

class Camera(lookFrom: Point3,
             lookAt: Point3,
             vUp: Vec3,
             verticalFieldOfViewDegrees: Double,
             aspectRatio: Double) {

    private val theta = Math.toRadians(verticalFieldOfViewDegrees)
    private val h = tan(theta / 2)
    private val viewportHeight = 2.0 * h
    private val viewportWidth = aspectRatio * viewportHeight

    private val w = (lookFrom - lookAt).unit
    private val u = (vUp * w).unit
    private val v = w * u

    private val origin = lookFrom

    private val horizontal = viewportWidth * u
    private val vertical = viewportHeight * v
    private val lowerLeftCorner = origin - (horizontal / 2.0) - (vertical / 2.0) - w

    fun getRay(s: Double, t: Double) = Ray(
        origin,
        lowerLeftCorner + (s * horizontal) + (t * vertical) - origin
    )
}

fun Double.clamp(minInclusive: Double, maxInclusive: Double) = when {
    this < minInclusive -> minInclusive
    this > maxInclusive -> maxInclusive
    else -> this
}
