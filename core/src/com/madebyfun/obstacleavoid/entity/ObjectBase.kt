//Created by Graham Duthie on 24/11/2023 12:41
package com.madebyfun.obstacleavoid.entity

import com.badlogic.gdx.math.Circle
import com.madebyfun.obstacleavoid.config.GameConfig

abstract class ObjectBase(protected var x: Float, protected var y: Float) {
    var offOfVisibleGameScreen = false
        get() = this.y < -GameConfig.OBSTACLE_AND_PLAYER_RADIUS
    var textureX = 0f
        get() = this.x - boundsRadius
    var textureY = 0f
        get() = this.y - boundsRadius


    val boundsRadius = GameConfig.OBSTACLE_AND_PLAYER_RADIUS
    abstract val bounds: Circle

    protected fun updateBounds() = bounds.setPosition(x, y)
}