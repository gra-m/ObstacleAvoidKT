//Created by Graham Duthie on 27/11/2023 16:09
package com.madebyfun.obstacleavoid.game

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Pools
import com.madebyfun.obstacleavoid.config.Difficulty
import com.madebyfun.obstacleavoid.config.GameConfig
import com.madebyfun.obstacleavoid.entity.Obstacle
import com.madebyfun.obstacleavoid.entity.Player
import com.madebyfun.obstacleavoid.utils.isKeyPressed
import com.madebyfun.obstacleavoid.utils.logger
import com.madebyfun.obstacleavoid.utils.smoothScores

class GameController {
    companion object {
        private val log = logger<GameController>()

    }

   val player = Player(GameConfig.WORLD_CENTER_X, 1f)

    var lives = GameConfig.PLAYER_START_LIVES
    var gameOver = false
        get() = lives <= 0
        private set
    private var obstacleTimer = 0f
    val obstaclePool = Pools.get(Obstacle::class.java, 5)
    val obstacles = Array<Obstacle>()
    private var timeSinceCollision = 0f
    private var timeToScore = 0f
    var displayScore = 0
    private var score = 0
    private var difficulty = Difficulty.HARD


    fun update(delta: Float) {
        if (!gameOver) {
            updatePlayerAndObstacles(delta)
            createNewObstacle(delta)
            updateScore(delta)
            displayScore = smoothScores(score, displayScore, delta)
        }
    }

    private fun updateScore(delta: Float) {
        timeToScore += delta
        if(timeToScore >= GameConfig.SCORE_NOW_TIME) {
            timeToScore = 0f
            score += MathUtils.random(1, 5)
        }
    }

    private fun updatePlayerAndObstacles(delta: Float) {
        timeSinceCollision += delta
        movePlayer()
        updateObstacles()
        
        when {
            playerIsCollidingWithObstacle() && timeSinceCollision >= 1f -> {
                timeSinceCollision = 0f
                lives--
                when {
                    gameOver -> log.debug("Game Over")
                    else -> restart()
                }
            }
        }
    }

    private fun movePlayer() {
        var xSpeed = 0f
        var playerMovedX = player.x

        when {
            Input.Keys.RIGHT.isKeyPressed() -> xSpeed = GameConfig.PLAYER_MAX_X_SPEED
            Input.Keys.LEFT.isKeyPressed() -> xSpeed = -GameConfig.PLAYER_MAX_X_SPEED
        }
        playerMovedX += xSpeed

        player.movePlayer(playerMovedX)
    }


    private fun restart() {
        obstaclePool.freeAll(obstacles)
        obstacles.clear()
        player.reset()

    }

    private fun playerIsCollidingWithObstacle(): Boolean {
        obstacles.forEach{
            if(it.bounds.overlaps(player.bounds))
                return true
        }
        return false
    }

    private fun createNewObstacle(delta: Float) {
        obstacleTimer += delta

        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME) {
            obstacleTimer = 0f
            val poolObstacle = obstaclePool.obtain()
            poolObstacle.setPoolObstacle(getRandomX(), GameConfig.WORLD_HEIGHT, difficulty)
            obstacles.add(poolObstacle)
        }
    }

    private fun getRandomX(): Float {
        return MathUtils.random(0.4f, 5.6f)
    }

    private fun updateObstacles() {
        obstacles.forEach{it -> it.update()}
        obstacles.forEach{it ->
            run {
                if (it.offOfVisibleGameScreen) {
                    obstaclePool.free(it)
                    obstacles.removeValue(it,true)
                }
            }
        }
    }

}