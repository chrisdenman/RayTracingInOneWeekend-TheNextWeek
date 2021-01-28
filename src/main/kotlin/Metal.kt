import Vec3.Companion.randomInUnitSphere
import Vec3.Companion.reflect

class Metal(private val albedo: Colour, private val fuzzines: Double) : Material {
    override fun scatter(ray: Ray, hit: Hit): ScatterData {
        val reflected = reflect(ray.direction.unit(),  hit.normal)
        val scatteredRay = Ray(hit.p, reflected + fuzzines* randomInUnitSphere)
        val scattered = scatteredRay.direction dot hit.normal > 0
        return ScatterData(
            albedo,
            scatteredRay,
            scattered
        )
    }
}
