//Created by Graham Duthie on 22/11/2023 16:18
package com.madebyfun.obstacleavoid.entity

import com.badlogic.gdx.math.Circle
import com.madebyfun.obstacleavoid.config.GameConfig

class Player(x: Float, y: Float) : ObjectBase(x, y) {
    companion object {
        //private const val MAX_X_SPEED = 0.25f
        // moved but left companion obj for constants
    }

    override val bounds = Circle(x, y, boundsRadius)

    fun movePlayer(x: Float) {
        this.x = x
        this.y = 1.0f
        updateBounds()
    }

    fun reset() {
        this.x = GameConfig.WORLD_CENTER_X
        this.y = 1f
        updateBounds()
    }


}