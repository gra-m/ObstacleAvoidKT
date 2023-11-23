//Created by Graham Duthie on 22/11/2023 14:45
package com.madebyfun.obstacleavoid.utils.debug

//https://libgdx.com/wiki/graphics/2d/orthographic-camera
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.madebyfun.obstacleavoid.config.GameConfig
import com.madebyfun.obstacleavoid.uti.DebugCameraConfig
import com.madebyfun.obstacleavoid.utils.logger

class DebugCameraController {

    companion object {
        @JvmStatic
        private val log = logger<DebugCameraController>()
    }

    private val position = Vector2()
    private val startPosition = Vector2()
    private val config = DebugCameraConfig()
    private var cameraViewportWidth = 1f
    private var zoom = 1f
        set(value) {
            field = MathUtils.clamp(value, GameConfig.WORLD_WIDTH/GameConfig.WORLD_WIDTH,
                (config.zoomOutConstrainedToThis * GameConfig.WORLD_WIDTH)/cameraViewportWidth )
        }

    init {
        log.debug("$config")
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
        val moveSpeed = config.moveSpeed * Gdx.graphics.deltaTime

        when{
            config.isLeftPressed() -> moveLeft(moveSpeed)
            config.isRightPressed() -> moveRight(moveSpeed)
            config.isUpPressed() -> moveUp(moveSpeed)
            config.isDownPressed() -> moveDown(moveSpeed)
            config.isZoomInPressed() -> zoomIn()
            config.isZoomOutPressed() -> zoomOut()
            config.isLogPressed() -> logPrint()
            config.isResetPressed() -> reset()
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
        zoom-= config.zoomSpeed
    }
    private fun zoomOut() {
        log.debug("Zooming Out == counterintuitive increase to zoom")
        zoom+= config.zoomSpeed
    }
}

