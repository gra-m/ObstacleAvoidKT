//Created by Graham Duthie on 20/11/2023 17:04
package com.madebyfun.obstacleavoid.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.madebyfun.obstacleavoid.config.GameConfig
import com.madebyfun.obstacleavoid.utils.clearScreen
import com.madebyfun.obstacleavoid.utils.debug.DebugCameraController
import com.madebyfun.obstacleavoid.utils.drawGrid

class GameScreen : Screen {
    private var centeredCamera = true

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer
    private lateinit var debugCameraController: DebugCameraController


    override fun show() {
        camera = OrthographicCamera()
        camera.setToOrtho(false)
        viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
        renderer.projectionMatrix = camera.combined
        debugCameraController = DebugCameraController()
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
    }

    override fun render(delta: Float) {
        debugCameraController.handleDebugInput()
        debugCameraController.applyTo(camera)
        
        clearScreen()
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