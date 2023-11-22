//Created by Graham Duthie on 20/11/2023 17:04
package com.madebyfun.obstacleavoid.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.madebyfun.obstacleavoid.config.GameConfig
import com.madebyfun.obstacleavoid.config.GameConfig.WORLD_CENTER_Y
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


    override fun show() {
        camera = OrthographicCamera()
        camera.setToOrtho(false)
        viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
        player = Player()
        player.setPosition(GameConfig.WORLD_CENTER_X, 1f)
        debugCameraController = DebugCameraController()
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, WORLD_CENTER_Y)
    }

    override fun render(delta: Float) {
        debugCameraController.handleDebugInput()
        debugCameraController.applyTo(camera)
        
        clearScreen()
        renderer.projectionMatrix = camera.combined
        renderer.use{ player.drawDebug(renderer)}
        viewport.drawGrid(renderer)

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