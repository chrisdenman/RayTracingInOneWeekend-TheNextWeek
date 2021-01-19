import kotlin.math.sqrt

class Ray(val origin: Point3, val direction: Vec3) {
    fun at(t: Double): Point3 = origin + (direction * t)

    private fun intersectsSphere(center: Point3, radius: Double): Double {
        val oc = origin - center
        val a = direction.magnitudeSquared()
        val halfB = oc dot direction
        val c = oc.magnitudeSquared() - (radius * radius)
        val discriminant = (halfB * halfB) - (a * c)
        return if (discriminant < 0)
            -1.0
        else
            -halfB - sqrt(discriminant) / a
    }

    fun colour(): Colour {
        val t = intersectsSphere(Point3(0.0, 0.0, -1.0), 0.5)
        return if (t > 0.0) {
            val normal = (at(t) - Vec3(0, 0, -1)).unit()
            0.5 * Colour(normal.x + 1, normal.y + 1, normal.z + 1)
        } else {
            val unitRay = direction.unit()
            val t: Double = 0.5 * (unitRay.y + 1.0)
            Vec3.UNIT * (1.0 - t) + t * Colour(0.5, 0.7, 1.0)
        }
    }
}
