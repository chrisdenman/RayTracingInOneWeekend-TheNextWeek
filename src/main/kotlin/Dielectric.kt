class Dielectric(private val indexOfRefraction: Double) : Material {
    override fun scatter(ray: Ray, hit: Hit): ScatterData {
        val attenuation = Colour.ONE
        val refractionRatio =
            if (hit.frontFace) (1.0 / indexOfRefraction) else indexOfRefraction
        val unitDirection = ray.direction.unit()
        val refracted = Vec3.refract(unitDirection, hit.normal, refractionRatio)
        val scattered = Ray(hit.p, refracted)
        return ScatterData(attenuation, scattered, true)
    }
}
