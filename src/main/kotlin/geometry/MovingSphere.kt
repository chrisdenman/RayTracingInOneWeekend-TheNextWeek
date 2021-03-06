package geometry

import Hit
import Hittable
import Material
import Point3
import Ray
import kotlin.math.sqrt

class MovingSphere(
    private val center0: Point3,
    private val center1: Point3,
    private val time0: Double,
    private val time1: Double,
    private val radius: Double,
    override val material: Material
) : Hittable {

    private fun center(time: Double) =
        center0 + (center1 - center0) * ((time - time0) / (time1 - time0))

    override fun hit(ray: Ray, tMin: Double, tMax: Double): Hit? {
        val oc = ray.origin - center(ray.time)
        val a = ray.direction.magnitudeSquared
        val halfB = oc dot ray.direction
        val c = oc.magnitudeSquared - (radius * radius)
        val discriminant = (halfB * halfB) - (a * c)
        if (discriminant < 0) return null
        val sqrtd = sqrt(discriminant)
        val root = ((-halfB - sqrtd) / a).let {
            when {
                (it < tMin || tMax < it) -> {
                    ((-halfB + sqrtd) / a).let {
                        when {
                            (it < tMin || tMax < it) -> null
                            else -> it
                        }
                    }
                }
                else -> it
            }
        }

        return root?.let { root ->
            val p = ray.at(root)
            val outwardNormal = (p - center(ray.time)) / radius

            return Hit(p, root, ray, outwardNormal)
        }
    }
}
