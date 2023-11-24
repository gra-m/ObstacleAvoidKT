//Created by Graham Duthie on 20/11/2023 17:04
package com.madebyfun.obstacleavoid.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.madebyfun.obstacleavoid.config.GameConfig
import com.madebyfun.obstacleavoid.config.GameConfig.WORLD_CENTER_Y
import com.madebyfun.obstacleavoid.entity.Obstacle
import com.madebyfun.obstacleavoid.entity.Player
import com.madebyfun.obstacleavoid.utils.clearScreen
import com.madebyfun.obstacleavoid.utils.debug.DebugCameraController
import com.madebyfun.obstacleavoid.utils.drawGrid
import com.madebyfun.obstacleavoid.utils.use

class GameScreen : Screen {
    private var centeredCamera = true

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var player: Player
    private lateinit var renderer: ShapeRenderer
    private lateinit var debugCameraController: DebugCameraController

    private var obstacleTimer = 0f
    private val obstacles = Array<Obstacle>()


    override fun show() {
        camera = OrthographicCamera()
        camera.setToOrtho(false)
        viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
        player = Player(GameConfig.WORLD_CENTER_X, 1.0f)
        debugCameraController = DebugCameraController()
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, WORLD_CENTER_Y)
    }

    override fun render(delta: Float) {
        debugCameraController.handleDebugInput()
        debugCameraController.applyTo(camera)
        clearScreen()

         // update game world
         player.update()
         updateObstacles()
        createNewObstacle(delta)

        renderer.projectionMatrix = camera.combined
        renderer.use{
            player.drawDebug(renderer)
            obstacles.forEach{it.drawDebug(renderer)}
        }
        viewport.drawGrid(renderer)

    }

    private fun createNewObstacle(delta: Float) {
        obstacleTimer += delta

        if (obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME) {
            obstacleTimer = 0f
            obstacles.add(Obstacle(getRandomX(), GameConfig.WORLD_HEIGHT))
        }
    }

    // previous do while with replacement limits of 0.06667 and 0.93333
    // replaced with explicit and simpler range
    private fun getRandomX(): Float {
        return MathUtils.random(0.4f, 5.6f)
    }

    private fun updateObstacles() {
        obstacles.forEach{it -> it.update()}
        
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, centeredCamera)

    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        renderer.dispose()
    }
}