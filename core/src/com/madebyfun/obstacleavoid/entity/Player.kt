//Created by Graham Duthie on 22/11/2023 16:18
package com.madebyfun.obstacleavoid.entity

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils
import com.madebyfun.obstacleavoid.config.GameConfig

class Player(x: Float = GameConfig.WORLD_CENTER_X, y: Float) : ObjectBase(x, y) {
    companion object {
        //private const val MAX_X_SPEED = 0.25f
        // moved but left companion obj for constants
    }

    override val bounds = Circle(x, y, boundsRadius)

    fun movePlayer(x: Float) {
        this.x = withinWorld(x)
        this.y = 1.0f
        updateBounds()
    }

    fun getCurrentPlayerX() : Float{
        return this.x
    }

    fun getCurrentPlayerY() : Float{
        return this.y
    }

    fun reset() {
        this.x = GameConfig.WORLD_CENTER_X
        this.y = 1f
        updateBounds()
    }

    private fun withinWorld(playerMovedX: Float): Float {
        return MathUtils.clamp(playerMovedX, boundsRadius, GameConfig.WORLD_WIDTH - boundsRadius)
    }


}