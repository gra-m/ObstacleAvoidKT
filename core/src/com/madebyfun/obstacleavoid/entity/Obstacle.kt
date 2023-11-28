//Created by Graham Duthie on 22/11/2023 16:18
package com.madebyfun.obstacleavoid.entity

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.utils.Pool.Poolable
import com.madebyfun.obstacleavoid.config.Difficulty

class Obstacle(x: Float = 0f, y: Float = 0f, private var difficulty: Difficulty = Difficulty.MEDIUM) : ObjectBase(x, y), Poolable {


    // to make a class poolable either initialize defaults in main constructor or in an empty constructor as below
    //constructor() : this(0f, 0f, Difficulty.MEDIUM)

    override val bounds = Circle(x, y, boundsRadius)


    fun setPoolObstacle(x: Float, y: Float, difficulty: Difficulty) {
        this.x = x
        this.y = y
        this.difficulty = difficulty
    }
    // Called from GameScreen Render on delta
    fun update() {
        y-= difficulty.obstacleSpeed
        updateBounds()
    }

    override fun reset() {
        this.offOfVisibleGameScreen = false
    }


}