//Created by Graham Duthie on 22/11/2023 16:18
package com.madebyfun.obstacleavoid.entity

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.madebyfun.obstacleavoid.config.GameConfig
import com.madebyfun.obstacleavoid.utils.circle

// No need for init, setters set x and y from constructor parameters and bounds are initialized on declaration
class Obstacle(x: Float, y: Float) {
    companion object {
        private const val BOUNDS_RADIUS = GameConfig.OBSTACLE_AND_PLAYER_RADIUS
        private const val SIZE = BOUNDS_RADIUS * 2
        private const val MAX_Y_SPEED = 0.15f
    }
    private var x: Float = x
        private set (value) {
            val rightMostExtent = GameConfig.WORLD_WIDTH - BOUNDS_RADIUS
            val leftMostExtent = BOUNDS_RADIUS
            field = if (value > rightMostExtent)
                rightMostExtent
            else if (value < leftMostExtent)
                leftMostExtent
            else
                value
        }

    private var y: Float = y
        private set(value) {
            val upperMostOutOfViewExtent = GameConfig.WORLD_HEIGHT + GameConfig.OBSTACLE_AND_PLAYER_RADIUS
            val lowerMostOutOfViewExtent = 0 - SIZE
           field = if (value > upperMostOutOfViewExtent)
               upperMostOutOfViewExtent
            else if (value < lowerMostOutOfViewExtent)
                lowerMostOutOfViewExtent
            else
                value
        }
    private var bounds = Circle(x, y, BOUNDS_RADIUS)


    // Called from GameScreen Render on delta
    fun update() {
        y-= MAX_Y_SPEED
        updateBounds()
    }

    // Extension function in GdxGraphics called from GameScreen Render on delta
    fun drawDebug(renderer: ShapeRenderer) = renderer.circle(bounds)

    private fun updateBounds() = bounds.setPosition(x, y)

}