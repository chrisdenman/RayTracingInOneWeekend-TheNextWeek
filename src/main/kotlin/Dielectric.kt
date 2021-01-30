import kotlin.math.min
import kotlin.math.sqrt

class Dielectric(private val indexOfRefraction: Double) : Material {
    override fun scatter(ray: Ray, rec: Hit): ScatterData {
        val attenuation = Colour.ONE
        val refractionRatio =
            if (rec.frontFace) (1.0 / indexOfRefraction) else indexOfRefraction
        val unitRayDirection = ray.direction.unit

        val cosTheta = min(1.0, -unitRayDirection dot rec.normal)
        val sinTheta = sqrt(1 - cosTheta * cosTheta)
        val sum = cosTheta * cosTheta + sinTheta * sinTheta
        val cannotRefract = refractionRatio * sinTheta > 1.0

        val refracted = Vec3.refract(unitRayDirection, rec.normal, refractionRatio)
        val scattered = Ray(rec.p, refracted)
        return ScatterData(attenuation, scattered, true)
    }
}
