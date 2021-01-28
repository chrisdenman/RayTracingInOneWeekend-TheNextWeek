import Vec3.Companion.randomUnitVector

class Lambertian(private val albedo: Colour) : Material {
    override fun scatter(ray: Ray, hit: Hit): ScatterData {
        var scatterDirection = hit.normal + randomUnitVector
        if (scatterDirection.isNearZero) {
            scatterDirection = hit.normal
        }
        val scattered = Ray(hit.p, scatterDirection)
        return ScatterData(albedo, scattered, true)
    }
}
