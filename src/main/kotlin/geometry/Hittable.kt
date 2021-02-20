import geometry.AABB
import kotlin.random.Random

interface Hittable {
    fun hit(ray: Ray, tMin: Double, tMax: Double): Hit?
    fun boundingBox(time0: Double = 0.0, time1: Double = 0.0): AABB?
}

data class Hit(val p: Point3, val t: Double, val ray: Ray, val outwardNormal: Vec3) {
    val frontFace: Boolean = ray.direction dot outwardNormal < 0
    val normal: Vec3 = if (frontFace) outwardNormal else -outwardNormal
}

data class WorldHit(val hittable: Hittable, val hit: Hit)

fun Collection<Hittable>.boundingBox(time0: Double, time1: Double): AABB? {
    return when {
        this.isEmpty() -> null
        else -> {
            var firstBox = true
            var result: AABB? = null
            for (obj in iterator()) {
                val tempBox = obj.boundingBox(time0, time1)
                if (tempBox == null) {
                    return null
                } else {
                    result = if (firstBox) tempBox else (result?.union(tempBox) )
                    firstBox = false
                }
            }
            result;
        }
    }
}

fun axisComparator(): Comparator<Hittable> {
    val comparisonIndex = Random.nextInt(0, 3)
    return (object: Comparator<Hittable> {
        override fun compare(a: Hittable?, b: Hittable?): Int {
            val boxA= a?.boundingBox()
            val boxB = b?.boundingBox()
            if ( boxA == null || boxB == null) {
                throw IllegalArgumentException("No bounding boxes")
            } else {
                return Math.signum(boxA.min[comparisonIndex] - boxB.min[comparisonIndex]).toInt()
            }
        }
    })
}
