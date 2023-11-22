//Created by Graham Duthie on 22/11/2023 16:18
package com.madebyfun.obstacleavoid.entity

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.madebyfun.obstacleavoid.utils.circle

class Player {
    companion object {
        private const val BOUNDS_RADIUS = 0.4f
        private const val SIZE = BOUNDS_RADIUS * 2
    }
    val bounds: Circle
    var x: Float = 0f
    var y: Float = 0f

    init {
        bounds = Circle(x, y, BOUNDS_RADIUS)

    }

    fun setPosition(x: Float, y: Float) {
        this.x = x
        this.y = y
        updateBounds()
    }

    // Extension function in GdxGraphics
    fun drawDebug(renderer: ShapeRenderer) = renderer.circle(bounds)

    private fun updateBounds() = bounds.setPosition(x, y)


}