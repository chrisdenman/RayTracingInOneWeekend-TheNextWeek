import Vec3.Companion.reflect

class Metal(val albedo: Colour) : Material {
    override fun scatter(ray: Ray, hit: Hit): ScatterData {
        val reflected = reflect(ray.direction.unit(),  hit.normal)
        val scatteredRay = Ray(hit.p, reflected)
        val scattered = scatteredRay.direction dot hit.normal > 0
        return ScatterData(
            albedo,
            scatteredRay,
            scattered
        )
    }
}
