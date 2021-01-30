import Vec3.Companion.randomUnit

class Lambertian(private val albedo: Colour) : Material {
    override fun scatter(ray: Ray, rec: Hit): ScatterData {
        var scatterDirection = rec.normal + randomUnit
        if (scatterDirection.isNearZero) {
            scatterDirection = rec.normal
        }
        val scattered = Ray(rec.p, scatterDirection)
        return ScatterData(albedo, scattered, true)
    }
}
