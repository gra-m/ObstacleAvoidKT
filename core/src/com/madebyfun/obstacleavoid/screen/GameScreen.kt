//Created by Graham Duthie on 20/11/2023 17:04
package com.madebyfun.obstacleavoid.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.madebyfun.obstacleavoid.assets.AssetPaths
import com.madebyfun.obstacleavoid.config.GameConfig
import com.madebyfun.obstacleavoid.config.GameConfig.WORLD_CENTER_Y
import com.madebyfun.obstacleavoid.entity.Obstacle
import com.madebyfun.obstacleavoid.entity.Player
import com.madebyfun.obstacleavoid.utils.*
import com.madebyfun.obstacleavoid.utils.debug.DebugCameraController

class GameScreen : Screen {
    companion object {
        private val log = logger<GameScreen>()

    }
    private var centeredCamera = true
    // Separate cameras and viewports for UI/HUD and game is usual, where hud requires 1:1 ppwu ration and game world
    // uses wu.
    private lateinit var hudCamera: OrthographicCamera
    private lateinit var hudViewport: Viewport
    private lateinit var batch: SpriteBatch
    private lateinit var font: BitmapFont
    private lateinit var layout: GlyphLayout

    // game and debug
    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: Viewport
    private lateinit var player: Player
    private lateinit var renderer: ShapeRenderer
    private lateinit var debugCameraController: DebugCameraController

    private var lives = GameConfig.PLAYER_START_LIVES
    private var obstacleTimer = 0f
    private val obstacles = Array<Obstacle>()
    private var timeSinceCollision = 0f
    private var timeToScore = 0f
    private var displayScore = 0
    private var score = 0


    override fun show() {
        camera = OrthographicCamera()
        hudCamera = OrthographicCamera()
        camera.setToOrtho(false)
        viewport = FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera)
        hudViewport = FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera)
        renderer = ShapeRenderer()
        player = Player(GameConfig.WORLD_CENTER_X, 1.0f)
        debugCameraController = DebugCameraController()
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, WORLD_CENTER_Y)
        batch = SpriteBatch()
        font = BitmapFont(AssetPaths.HUD_FONT.toInternalFile())
        layout = GlyphLayout()
    }

    override fun render(delta: Float) {
        debugCameraController.handleDebugInput()
        debugCameraController.applyTo(camera)
        clearScreen()
        if (lives > 0) {
            updatePlayerAndObstacles(delta)
            createNewObstacle(delta)
            updateScore(delta)
            displayScore = smoothScores(score, displayScore, delta)
        }

        renderer.projectionMatrix = camera.combined
        renderer.use{
            player.drawDebug(renderer)
            obstacles.forEach{it.drawDebug(renderer)}
        }

        renderUI()

        viewport.drawGrid(renderer)

    }




    private fun updateScore(delta: Float) {
        timeToScore += delta

        if(timeToScore >= GameConfig.SCORE_NOW_TIME) {
            timeToScore = 0f
            score += MathUtils.random(1, 5)
            log.debug("Score Now: $score")
        }



    }

    private fun renderUI() {

        batch.projectionMatrix = hudCamera.combined
        batch.use {
            val livesText = "LIVES: $lives"
            val scoreText = "SCORE: $displayScore"
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

    private fun updatePlayerAndObstacles(delta: Float) {
        timeSinceCollision += delta
        player.update()
        updateObstacles()
        if(playerIsCollidingWithObstacle() && timeSinceCollision >= 1f) {
            timeSinceCollision = 0f
            lives--
        }
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
        hudViewport.update(width, height, centeredCamera)

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
        batch.dispose()
        font.dispose()
    }
}