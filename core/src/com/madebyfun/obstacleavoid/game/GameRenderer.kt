//Created by Graham Duthie on 27/11/2023 16:10
package com.madebyfun.obstacleavoid.game

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.madebyfun.obstacleavoid.assets.AssetPaths
import com.madebyfun.obstacleavoid.config.GameConfig
import com.madebyfun.obstacleavoid.screen.GameScreen
import com.madebyfun.obstacleavoid.utils.*
import com.madebyfun.obstacleavoid.utils.debug.DebugCameraController

class GameRenderer(private val controller: GameController) : Disposable {

    companion object {
        private val log = logger<GameScreen>()
    }

    private val centeredCamera = true
    // Separate cameras and viewports for UI/HUD and game is usual, where hud requires 1:1 ppwu ration and game world
    // uses wu.
    private val hudCamera = OrthographicCamera()
    private val hudViewport = FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera)
    private val batch = SpriteBatch()
    private val font =  BitmapFont(AssetPaths.HUD_FONT.toInternalFile())
    private val layout = GlyphLayout()

    // game and debug
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
    private val renderer = ShapeRenderer()
    private val debugCameraController = DebugCameraController()

    init{
        log.debug("init called")
        camera.setToOrtho(false)
        hudCamera.setToOrtho(false)
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y)
    }

    fun render() {
        log.debug("RENDER CALLED")
        debugCameraController.handleDebugInput()
        debugCameraController.applyTo(camera)
        clearScreen()

        renderer.projectionMatrix = camera.combined
        renderer.use{
            controller.player.drawDebug(renderer)
            controller.obstacles.forEach{it.drawDebug(renderer)}
        }

        renderUI()
        viewport.apply()
        viewport.drawGrid(renderer)

    }

    private fun renderUI() {

        batch.projectionMatrix = hudCamera.combined
        batch.use {
            val livesText = "LIVES: ${controller.lives}"
            val scoreText = "SCORE: ${controller.displayScore}"
            layout.setText(font, livesText)
            font.draw(
                batch,
                layout,
                20f,
                GameConfig.HUD_HEIGHT - layout.height
            )
            layout.setText(font, scoreText)
            font.draw(
                batch,
                layout,
                GameConfig.HUD_WIDTH - (layout.width + 20),
                GameConfig.HUD_HEIGHT - layout.height
            )
        }
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height, centeredCamera)
        hudViewport.update(width, height, centeredCamera)
    }

    override fun dispose() {
        renderer.dispose()
        batch.dispose()
        font.dispose()
    }
}