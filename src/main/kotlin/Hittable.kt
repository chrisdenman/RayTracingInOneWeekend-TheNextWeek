interface Hittable {
    fun hit(ray: Ray, tMin: Double, tMax: Double): HitData?
}

class HitData(val p: Point3, val t: Double, ray: Ray, outwardNormal: Vec3) {
    private val frontFace: Boolean = (ray.direction dot outwardNormal) < 0
    val normal: Vec3 = if (frontFace) outwardNormal else -outwardNormal
}
