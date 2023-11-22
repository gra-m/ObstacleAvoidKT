//Created by Graham Duthie on 20/11/2023 17:04
package com.madebyfun.obstacleavoid.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.madebyfun.obstacleavoid.config.GameConfig
import com.madebyfun.obstacleavoid.utils.clearScreen
import com.madebyfun.obstacleavoid.utils.drawGrid

class GameScreen : Screen {
    private val centerCamera = true
    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var renderer: ShapeRenderer


    override fun show() {
        camera = OrthographicCamera()
        viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
        renderer.projectionMatrix = camera.combined
    }

    override fun render(delta: Float) {
        clearScreen()
        viewport.drawGrid(renderer)
    }

    override fun resize(width: Int, height: Int) {
        dontForgetToUpdateViewport(width, height)

    }

    private fun dontForgetToUpdateViewport(width: Int, height: Int) {
        viewport.update(width, height, centerCamera)
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