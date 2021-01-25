class Ray(val origin: Point3, val direction: Vec3) {

    fun at(t: Double): Point3 = origin + (direction * t)

    fun colour(world: World, depth: Int = 0): Vec3 =
        when {
            depth <= 0 -> Colour.ZERO
            else -> {
                world.hit(this, 0.001, Double.POSITIVE_INFINITY).let {
                    if (it != null) {
                        val target = it.p + it.normal + Vec3.randomInHemiSphere(it.normal)
                        0.5 * Ray(it.p, target - it.p).colour(world, depth - 1)
                    } else {
                        val unitDirection = direction.unit()
                        val t = 0.5 * (unitDirection.y + 1.0)
                        ((1.0 - t) * Colour.UNIT) + (t * Colour(0.5, 0.7, 1.0))
                    }
                }
            }
        }
}
