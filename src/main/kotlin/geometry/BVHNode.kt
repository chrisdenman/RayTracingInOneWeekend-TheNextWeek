package geometry

import Hit
import Hittable
import Ray
import Vec3.Companion

class BVHNode(
    private val list: Collection<Hittable>,
    private val time0: Double,
    private val time1: Double) : Hittable {

    private val left: Hittable = this
    private val right: Hittable = this
    private val box: AABB = AABB(Vec3.randomUnitComponents, Vec3.randomUnitComponents)

    override fun hit(ray: Ray, tMin: Double, tMax: Double): Hit? {
        if (!box.hit(ray, tMin, tMax)) {
            return null
        } else {
            val hitLeft = left.hit(ray, tMin, tMax)
            val hitRight = right.hit(ray, tMin, hitLeft?.t ?: tMax)
            return hitLeft ?: hitRight
        }
    }

    override fun boundingBox(time0: Double, time1: Double): AABB = box
}
