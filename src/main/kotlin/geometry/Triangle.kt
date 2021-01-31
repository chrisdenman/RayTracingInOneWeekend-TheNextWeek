package geometry

import Hit
import Hittable
import Material
import Ray
import Vec3

class Triangle(
    private val point0: Vec3,
    private val point1: Vec3,
    private val point2: Vec3,
    override val material: Material
) : Hittable {

    private val normal = ((point1 - point0) * (point2 - point1)).unit

    override fun hit(ray: Ray, tMin: Double, tMax: Double): Hit? {


        return null;
    }
}
