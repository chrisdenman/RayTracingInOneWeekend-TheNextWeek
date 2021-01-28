class World(private val objects: List<Hittable>) {

    fun hit(ray: Ray, tMin: Double, tMax: Double): WorldHit? {
        var currentClosest = tMax
        var result: WorldHit? = null
        for (hittable in objects) {
            hittable.hit(ray, tMin, currentClosest)?.let { hit ->
                currentClosest = hit.t
                result = WorldHit(hittable, hit)
            }
        }

        return result
    }
}
