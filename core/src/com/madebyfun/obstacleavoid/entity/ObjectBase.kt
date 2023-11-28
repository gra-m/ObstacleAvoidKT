//Created by Graham Duthie on 24/11/2023 12:41
package com.madebyfun.obstacleavoid.entity

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.madebyfun.obstacleavoid.config.GameConfig
import com.madebyfun.obstacleavoid.utils.circle

abstract class ObjectBase(var x: Float, var y: Float) {
    var offOfVisibleGameScreen = false
        get() = this.y < -GameConfig.OBSTACLE_AND_PLAYER_RADIUS

    val boundsRadius = GameConfig.OBSTACLE_AND_PLAYER_RADIUS
    abstract val bounds: Circle

    // Extension function in GdxGraphics
    fun drawDebug(renderer: ShapeRenderer) = renderer.circle(bounds)
    protected fun updateBounds() = bounds.setPosition(x, y)
}