package geometry

import Hit
import Hittable
import Material
import Point3
import Ray
import kotlin.math.sqrt

class Sphere(private val center: Point3, private val radius: Double, override val material: Material) : Hittable {

    override fun hit(ray: Ray, tMin: Double, tMax: Double): Hit? {
        val oc = ray.origin - center
        val a = ray.direction.magnitudeSquared
        val halfB = oc dot ray.direction
        val c = oc.magnitudeSquared - (radius * radius)
        val discriminant = (halfB * halfB) - (a * c)

        if (discriminant < 0 ) return null

        val sqrtd = sqrt(discriminant)
        var root = (-halfB - sqrtd) / a
        if (root < tMin || tMax < root) {
            root = (-halfB + sqrtd) / a
            if (root < tMin || tMax < root) {
                return null
            }
        }

        val p = ray.at(root)
        val outwardNormal = (p - center) / radius

        return Hit(p, root, ray, outwardNormal)
    }
}
