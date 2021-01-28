// if ray then scattered, else absorbed
data class ScatterData(val attenuation: Colour, val ray: Ray, val scattered: Boolean)

interface Material {
    fun scatter(ray: Ray, hit: Hit): ScatterData
}
