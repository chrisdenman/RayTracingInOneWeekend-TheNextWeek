import Vec3.Companion.randomUnit

class Lambertian(private val albedo: Colour) : Material {
    override fun scatter(ray: Ray, rec: Hit): ScatterData {
        val scatterDirection = (rec.normal + randomUnit).let {
            when {
                it.isNearZero -> rec.normal
                else -> it
            }
        }
        val scattered = Ray(rec.p, scatterDirection)
        return ScatterData(albedo, scattered, true)
    }
}
