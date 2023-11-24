//Created by Graham Duthie on 22/11/2023 16:18
package com.madebyfun.obstacleavoid.entity

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Circle
import com.madebyfun.obstacleavoid.config.GameConfig
import com.madebyfun.obstacleavoid.utils.isKeyPressed

class Player(x: Float, y: Float) : ObjectBase(x, y) {
    companion object {
        private const val MAX_X_SPEED = 0.25f
    }

    override val bounds = Circle(x, y, boundsRadius)

    fun update() {
        var xSpeed = 0f
        val rightmostX = GameConfig.WORLD_WIDTH-boundsRadius
        val leftmostX = boundsRadius

        when {
            Input.Keys.RIGHT.isKeyPressed() -> xSpeed = MAX_X_SPEED
            Input.Keys.LEFT.isKeyPressed() -> xSpeed = -MAX_X_SPEED
        }
        x += xSpeed

        if (x < leftmostX)
            x = leftmostX
        else if (x > rightmostX )
            x = rightmostX

        updateBounds()
    }


}