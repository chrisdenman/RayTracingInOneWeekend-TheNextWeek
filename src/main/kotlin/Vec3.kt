import java.io.Writer
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.random.Random

@Suppress("unused")
data class Vec3(val x: Double = 0.0, val y: Double = 0.0, val z: Double = 0.0) {

    constructor(x: Int, y: Int, z: Int) : this(x.toDouble(), y.toDouble(), z.toDouble())

    operator fun unaryMinus(): Vec3 = Vec3(-x, -y, -z)

    operator fun plus(other: Vec3): Vec3 = Vec3(
        x + other.x,
        y + other.y,
        z + other.z
    )

    operator fun minus(other: Vec3): Vec3 = this + -other

    operator fun times(t: Double): Vec3 = Vec3(x * t, y * t, z * t)

    val magnitudeSquared: Double = (x * x) + (y * y) + (z * z)

    val magnitude: Double = sqrt(magnitudeSquared)

    val isNearZero: Boolean = tolerable(x) && tolerable(y) && tolerable(z)

    fun scale(scalar: Vec3) = Vec3(
        scalar.x * x,
        scalar.y * y,
        scalar.z * z
    )

    operator fun div(t: Double): Vec3 = this * t.reciprocal()

    infix fun dot(v: Vec3) = (x * v.x) + (y * v.y) + (z * v.z)

    operator fun times(v: Vec3) = Vec3(
        y * v.z - z * v.y,
        z * v.x - x * v.z,
        x * v.y - y * v.x
    )

    val unit: Vec3
        get() = this / magnitude

    /*
    inline vec3 refract(const vec3& uv, const vec3& n, double etai_over_etat) {
        auto cos_theta = fmin(dot(-uv, n), 1.0);
        vec3 r_out_perp =  etai_over_etat * (uv + cos_theta*n);
        vec3 r_out_parallel = -sqrt(fabs(1.0 - r_out_perp.length_squared())) * n;
        return r_out_perp + r_out_parallel;
    }
    */
    companion object {

        private const val TOLERANCE = 1E-8
        val ONE = Vec3(1, 1, 1)
        val ZERO = Vec3(0, 0, 0)

        private fun boundedRandomComponents(minInclusive: Double, maxExclusive: Double): Vec3 =
            Vec3(Random.nextDouble(minInclusive, maxExclusive), Random.nextDouble(minInclusive, maxExclusive), Random.nextDouble(minInclusive, maxExclusive))

        fun randomInHemisphere(normal: Vec3): Vec3 =
            randomInUnitSphere.let {
                when {
                    it dot normal > 0.0 -> it
                    else -> -it
                }
            }

        val randomInUnitSphere: Vec3
            get() {
                var candidate = boundedRandomComponents(-1.0, 1.0)
                while (candidate.magnitudeSquared >= 1) {
                    candidate = boundedRandomComponents(-1.0, 1.0)
                }
                return candidate
            }

        val randomUnit: Vec3
            get() = randomInUnitSphere.unit

        val randomUnitComponents: Vec3
            get() = Vec3(Random.nextDouble(), Random.nextDouble(), Random.nextDouble())

        fun reflect(v: Vec3, normal: Vec3): Vec3 = v - 2 * (v dot normal) * normal

        fun refract(i: Vec3, n: Vec3, etai_over_etat: Double): Vec3 {
            val cosThetai = min(-i dot n, 1.0)
            val sinSquaredThetat = etai_over_etat * etai_over_etat * (1 - (cosThetai * cosThetai))
            return (etai_over_etat * i) + (etai_over_etat * cosThetai - sqrt(1 - sinSquaredThetat)) * n
        }

        private fun tolerable(e: Double): Boolean = abs(e) < TOLERANCE
    }
}

operator fun Double.times(v: Vec3) = v * this

typealias Point3 = Vec3
typealias Colour = Vec3

fun Double.reciprocal() = 1.0 / this

fun Writer.writeColour(pixelColour: Colour, samplesPerPixel: Int) {
    val scale = 1.0 / samplesPerPixel
    val scaled = (scale * pixelColour)
    val gammaCorrected = Vec3(sqrt(scaled.x), sqrt(scaled.y), sqrt(scaled.z))
    gammaCorrected.let {
        write("${(256 * it.x.clamp(0.0, 0.999)).toInt()} ${(256 * it.y.clamp(0.0, 0.999)).toInt()} ${(256 * it.z.clamp(0.0, 0.999)).toInt()}\n")
    }
}
