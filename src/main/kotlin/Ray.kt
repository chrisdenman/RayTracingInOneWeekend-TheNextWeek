class Ray(val origin: Point3, val direction: Vec3) {

    fun at(t: Double): Point3 = origin + (direction * t)

    fun colour(world: World) =
        world.hit(this, 0.0, Double.POSITIVE_INFINITY).let {
            if (it != null) {
                0.5 * (it.normal + Colour.UNIT)
            } else {
                val unitDirection = direction.unit()
                val t = 0.5 * (unitDirection.y + 1.0)
                ((1.0 - t) * Colour.UNIT) + (t * Colour(0.5, 0.7, 1.0))
            }
        }
}
