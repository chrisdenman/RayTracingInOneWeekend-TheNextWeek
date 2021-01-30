data class ScatterData(val attenuation: Colour, val ray: Ray, val isScattered: Boolean)

interface Material {
    fun scatter(ray: Ray, rec: Hit): ScatterData
}
