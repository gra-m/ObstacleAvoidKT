//Created by Graham Duthie on 22/11/2023 14:45
package com.madebyfun.obstacleavoid.utils.debug

//https://libgdx.com/wiki/graphics/2d/orthographic-camera
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.madebyfun.obstacleavoid.config.GameConfig
import com.madebyfun.obstacleavoid.utils.isKeyPressed
import com.madebyfun.obstacleavoid.utils.logger

class DebugCameraController {

    companion object {
        @JvmStatic
        private val log = logger<DebugCameraController>()
        private const val ZOOM_OUT_CONSTRAINED_TO_THIS = 5
        private const val DEFAULT_LEFT_KEY = Input.Keys.A
        private const val DEFAULT_RIGHT_KEY = Input.Keys.D
        private const val DEFAULT_UP_KEY = Input.Keys.W
        private const val DEFAULT_DOWN_KEY = Input.Keys.S
        private const val DEFAULT_ZOOM_IN_KEY = Input.Keys.NUMPAD_1
        private const val DEFAULT_ZOOM_OUT_KEY = Input.Keys.NUMPAD_2
        private const val DEFAULT_RESET_KEY = Input.Keys.R
        private const val DEFAULT_LOG_KEY = Input.Keys.L
        private const val DEFAULT_MOVE_SPEED = 20f
        private const val DEFAULT_ZOOM_SPEED = 0.02f
    }

    private val position = Vector2()
    private val startPosition = Vector2()
    private var cameraViewportWidth = 1f
    private var zoom = 1f
        set(value) {
            field = MathUtils.clamp(value, GameConfig.WORLD_WIDTH/GameConfig.WORLD_WIDTH,
                (ZOOM_OUT_CONSTRAINED_TO_THIS * GameConfig.WORLD_WIDTH)/cameraViewportWidth )
        }

    fun setStartPosition(x: Float, y: Float) {
        startPosition.set(x, y)
        position.set(x, y)
    }

    fun applyTo(camera: OrthographicCamera) {
        cameraViewportWidth = camera.viewportWidth
        camera.position.set(position, 0f)
        camera.zoom = zoom
        camera.update()
    }

    fun handleDebugInput() {
        val moveSpeed = DEFAULT_MOVE_SPEED * Gdx.graphics.deltaTime

        when{
            DEFAULT_LEFT_KEY.isKeyPressed() -> moveLeft(moveSpeed)
            DEFAULT_RIGHT_KEY.isKeyPressed() -> moveRight(moveSpeed)
            DEFAULT_UP_KEY.isKeyPressed() -> moveUp(moveSpeed)
            DEFAULT_DOWN_KEY.isKeyPressed() -> moveDown(moveSpeed)
            DEFAULT_ZOOM_IN_KEY.isKeyPressed() -> zoomIn()
            DEFAULT_ZOOM_OUT_KEY.isKeyPressed() -> zoomOut()
            DEFAULT_LOG_KEY.isKeyPressed() -> logPrint()
            DEFAULT_RESET_KEY.isKeyPressed() -> reset()
        }
    }

    private fun logPrint() {
       log.debug("Zoom: $zoom CamViewportWidth: $cameraViewportWidth X: $position.x Y: $position.y")
    }


    private fun moveCamera(xSpeed: Float, ySpeed: Float) = setPosition(position.x + xSpeed, position.y + ySpeed)
    private fun setPosition(x: Float, y: Float) = position.set(x, y)
    private fun moveLeft(speed: Float) = moveCamera(-speed, 0f)
    private fun moveRight(speed: Float) = moveCamera(speed, 0f)
    private fun moveUp(speed: Float) = moveCamera(0f , speed)
    private fun moveDown(speed: Float) = moveCamera(0f, -speed)
    private fun reset() {
        setPosition(startPosition.x, startPosition.y)
        zoom = 1f
    }
    
    private fun zoomIn() {
        log.debug("Zooming In == decrease to zoom")
        zoom-= DEFAULT_ZOOM_SPEED
    }
    private fun zoomOut() {
        log.debug("Zooming Out == counterintuitive increase to zoom")
        zoom+= DEFAULT_ZOOM_SPEED
    }


}

