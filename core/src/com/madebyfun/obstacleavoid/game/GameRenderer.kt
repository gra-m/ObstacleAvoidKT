//Created by Graham Duthie on 27/11/2023 16:10
package com.madebyfun.obstacleavoid.game

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
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
    private val layout = GlyphLayout()

    private val font =  BitmapFont(AssetPaths.HUD_FONT.toInternalFile())
    private val texturePlayer = Texture(AssetPaths.PLAYER_TEXTURE.toInternalFile())
    private val textureObstacle = Texture(AssetPaths.OBSTACLE_TEXTURE.toInternalFile())
    private val textureBackground = Texture(AssetPaths.BACKGROUND_TEXTURE.toInternalFile())

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
        debugCameraController.handleDebugInput()
        debugCameraController.applyTo(camera)
        clearScreen()
        renderGamePlay(viewport)
        renderUI(hudViewport)
        renderDebug(viewport)
        viewport.drawGrid(renderer)

    }

    private fun renderDebug(viewport: Viewport) {
        beforeRenderingApplyRelevantViewport(viewport)
        renderer.projectionMatrix = camera.combined

        renderer.use {
            renderer.x(controller.player.x, controller.player.y, 0.1f)
            renderer.circle(controller.player.bounds)
            controller.obstacles.forEach {
                renderer.circle(it.bounds)
            }
        }
    }

    private fun renderGamePlay(viewport: Viewport) {
        beforeRenderingApplyRelevantViewport(viewport)
        batch.projectionMatrix = camera.combined

        batch.use {
            batch.draw(textureBackground, 0f, 0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT )
            batch.draw(texturePlayer, controller.player.textureX, controller.player.textureY,
                GameConfig.OBSTACLE_AND_PLAYER_SIZE,GameConfig.OBSTACLE_AND_PLAYER_SIZE)
            controller.obstacles.forEach{
                batch.draw(textureObstacle, it.textureX, it.textureY, GameConfig.OBSTACLE_AND_PLAYER_SIZE,
                    GameConfig.OBSTACLE_AND_PLAYER_SIZE)
            }
            
        }

    }

    private fun beforeRenderingApplyRelevantViewport(viewport: Viewport) {
        viewport.apply()
    }

    private fun renderUI(hudViewport: FitViewport) {
        beforeRenderingApplyRelevantViewport(hudViewport)
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
        textureObstacle.dispose()
        textureBackground.dispose()
        texturePlayer.dispose()
    }
}