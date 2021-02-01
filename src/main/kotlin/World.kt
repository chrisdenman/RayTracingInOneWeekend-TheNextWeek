class World(private val objects: List<Hittable>) {

    data class HitData(val currentClosest: Double, val worldHit: WorldHit? = null)

    fun hit(ray: Ray, tMin: Double, tMax: Double): WorldHit? {
        return objects.fold(HitData(tMax)) { acc, hittable ->
            hittable.hit(ray, tMin, acc.currentClosest)?.let { hit ->
                HitData(hit.t, WorldHit(hittable, hit))
            } ?: acc
        }.worldHit
    }
}
