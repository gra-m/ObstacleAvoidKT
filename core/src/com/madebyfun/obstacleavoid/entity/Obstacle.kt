//Created by Graham Duthie on 22/11/2023 16:18
package com.madebyfun.obstacleavoid.entity

import com.badlogic.gdx.math.Circle
import com.madebyfun.obstacleavoid.config.Difficulty

class Obstacle(x: Float, y: Float, private val difficulty: Difficulty) : ObjectBase(x, y) {

    
    override val bounds = Circle(x, y, boundsRadius)

    // Called from GameScreen Render on delta
    fun update() {
        y-= difficulty.obstacleSpeed
        updateBounds()
    }


}