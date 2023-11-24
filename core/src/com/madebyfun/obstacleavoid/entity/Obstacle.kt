//Created by Graham Duthie on 22/11/2023 16:18
package com.madebyfun.obstacleavoid.entity

import com.badlogic.gdx.math.Circle

class Obstacle(x: Float, y: Float) : ObjectBase(x, y) {
    companion object {
        private const val MAX_Y_SPEED = 0.15f
    }

    override val bounds = Circle(x, y, boundsRadius)

    // Called from GameScreen Render on delta
    fun update() {
        y-= MAX_Y_SPEED
        updateBounds()
    }


}