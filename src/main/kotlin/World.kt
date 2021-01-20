class World(private val objects: List<Hittable>) {

    fun hit(ray: Ray, tMin: Double, tMax: Double): HitData? {
        var currentClosest = tMax
        var result: HitData? = null
        for (hittable in objects) {
            hittable.hit(ray, tMin, currentClosest)?.let {
                currentClosest = it.t
                result = it
            }

        }

        return result
    }
}
