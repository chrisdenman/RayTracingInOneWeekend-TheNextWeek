package geometry

import Point3
import Ray
import kotlin.math.max
import kotlin.math.min

class AABB(val min: Point3, val max: Point3) {

    fun hit(ray: Ray, tMin: Double, tMax: Double): Boolean {
        for (dimension in 0 until 3) {
            val t0 = min(
                (min[dimension] - ray.origin[dimension]) / ray.direction[dimension],
                (max[dimension] - ray.origin[dimension]) / ray.direction[dimension]
            )

            val t1 = max(
                (min[dimension] - ray.origin[dimension]) / ray.direction[dimension],
                (max[dimension] - ray.origin[dimension]) / ray.direction[dimension]
            )

            if (min(t1, tMax) <= max(t0, tMin)) {
                return false
            }
        }
        return true
    }

    infix fun union(other: AABB): AABB =
        AABB(
            Point3(
                min(this.min.x, other.min.x),
                min(this.min.y, other.min.y),
                min(this.min.z, other.min.z)
            ),
            Point3(
                max(this.max.x, other.max.x),
                max(this.max.y, other.max.y),
                max(this.max.z, other.max.z)
            )
        )
}
