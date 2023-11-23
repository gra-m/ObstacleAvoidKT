//Created by Graham Duthie on 22/11/2023 16:18
package com.madebyfun.obstacleavoid.entity

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.madebyfun.obstacleavoid.config.GameConfig
import com.madebyfun.obstacleavoid.utils.circle

class Obstacle(x: Float, y: Float) {
    companion object {
        private const val BOUNDS_RADIUS = 0.4f
        private const val SIZE = BOUNDS_RADIUS * 2
        private const val MAX_Y_SPEED = 0.25f
    }
    private var x: Float = 0f
        set (value) {
            val rightMostExtent = GameConfig.WORLD_WIDTH - BOUNDS_RADIUS
            val leftMostExtent = BOUNDS_RADIUS
            field = if (value > rightMostExtent)
                rightMostExtent
            else if (value < leftMostExtent)
                leftMostExtent
            else
                value
        }
    private var y: Float = 0f
        set(value) {
            val upperMostOutOfViewExtent = GameConfig.WORLD_HEIGHT + SIZE
            val lowerMostOutOfViewExtent = 0 - SIZE
           field = if (value > upperMostOutOfViewExtent)
               upperMostOutOfViewExtent
            else if (value < lowerMostOutOfViewExtent)
                lowerMostOutOfViewExtent
            else
                value
        }
    var bounds = Circle(x, y, BOUNDS_RADIUS)


    fun update() {
        y+= MAX_Y_SPEED
        updateBounds()
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