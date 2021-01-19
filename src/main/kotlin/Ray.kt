class Ray(val origin: Point3, val direction: Vec3) {
    fun at(t: Double): Point3 = origin + (direction * t)

    private fun intersectsSphere(center: Point3, radius: Double): Boolean {
        val oc = origin - center
        val a = direction dot direction
        val b = 2.0 * oc dot direction
        val c = (oc dot oc) - radius * radius
        val discriminant = (b * b) - 4 * a * c
        return discriminant > 0
    }

    fun colour(): Colour =
        if (intersectsSphere(Point3(0, 0, -1), 0.5))
            Colour(1, 0, 0)
        else {
            val unitRay = direction.unit()
            val t: Double = (unitRay.y + 1.0) * 0.5
            Vec3.UNIT * (1.0 - t) + Colour(0.5, 0.7, 1.0) * t
        }
}
