package geometry

import Hit
import Hittable
import Ray
import axisComparator
import java.lang.IllegalArgumentException

class BVHNode(
    val left: Hittable,
    val right: Hittable,
    val box: AABB
) : Hittable {

    override fun hit(ray: Ray, tMin: Double, tMax: Double): Hit? =
        if (!box.hit(ray, tMin, tMax)) {
            null
        } else {
            val hitLeft = left.hit(ray, tMin, tMax)
            val hitRight = right.hit(ray, tMin, hitLeft?.t ?: tMax)

            hitLeft ?: hitRight
        }

    override fun boundingBox(time0: Double, time1: Double): AABB = box
}

fun buildBVHNodes(
    src_objects: List<Hittable>,
    time0: Double,
    time1: Double
): BVHNode =
        axisComparator().let { comparator ->
            when (val numObjects = src_objects.size) {
                1 -> Pair(src_objects[0], src_objects[0])
                2 -> if (comparator.compare(src_objects[0], src_objects[1]) < 0) {
                    Pair(src_objects[0], src_objects[1])
                } else {
                    Pair(src_objects[1], src_objects[0])
                }
                else -> {
                    val sortedObjects = src_objects.sortedWith(comparator)
                    val mid = numObjects / 2
                    Pair(
                        buildBVHNodes(sortedObjects.subList(0, mid), time0, time1),
                        buildBVHNodes(sortedObjects.subList(mid, numObjects), time0, time1)
                    )
                }
            }.let { (left, right) ->
                val leftBox = left.boundingBox(time0, time1)
                val rightBox = right.boundingBox(time0, time1)
                if (leftBox == null || rightBox == null) {
                    throw IllegalArgumentException("No bounding box")
                }

                BVHNode(left, right, leftBox union rightBox)
            }
}
