class Dielectric(private val indexOfRefraction: Double) : Material {
    override fun scatter(ray: Ray, rec: Hit): ScatterData {
        val attenuation = Colour.ONE
        val refractionRatio =
            if (rec.frontFace) (1.0 / indexOfRefraction) else indexOfRefraction
        val unitDirection = ray.direction.unit
        val refracted = Vec3.refract(unitDirection, rec.normal, refractionRatio)
        val scattered = Ray(rec.p, refracted)
        return ScatterData(attenuation, scattered, true)
    }
}
