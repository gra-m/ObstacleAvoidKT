package com.madebyfun.obstacleavoid.uti


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import com.madebyfun.obstacleavoid.utils.isKeyPressed
import com.madebyfun.obstacleavoid.utils.logger
import com.madebyfun.obstacleavoid.utils.toInternalFile

/**
 * Info/Configuration for {@link DebugCameraController}.
 * Loads configuration from file in assetsDir/debug/debug-camera.json
 */
class DebugCameraConfig {

    companion object {
        @JvmStatic
        private val log = logger<DebugCameraConfig>()
        private const val ZOOM_OUT_CONSTRAINED_TO_THIS = 5
        private const val FILE_PATH = "debug/debug-camera.json"
        private const val MAX_ZOOM_IN = "maxZoomIn"
        private const val MAX_ZOOM_OUT = "maxZoomOut"
        private const val MOVE_SPEED = "moveSpeed"
        private const val ZOOM_SPEED = "zoomSpeed"

        private const val LEFT_KEY = "leftKey"
        private const val RIGHT_KEY = "rightKey"
        private const val UP_KEY = "upKey"
        private const val DOWN_KEY = "downKey"

        private const val ZOOM_IN_KEY = "zoomInKey"
        private const val ZOOM_OUT_KEY = "zoomOutKey"
        private const val RESET_KEY = "resetKey"
        private const val LOG_KEY = "logKey"

        private const val DEFAULT_LEFT_KEY = Input.Keys.A
        private const val DEFAULT_RIGHT_KEY = Input.Keys.D
        private const val DEFAULT_UP_KEY = Input.Keys.W
        private const val DEFAULT_DOWN_KEY = Input.Keys.S

        private const val DEFAULT_ZOOM_IN_KEY = Input.Keys.COMMA
        private const val DEFAULT_ZOOM_OUT_KEY = Input.Keys.PERIOD

        private const val DEFAULT_RESET_KEY = Input.Keys.BACKSPACE
        private const val DEFAULT_LOG_KEY = Input.Keys.ENTER

        private const val DEFAULT_MOVE_SPEED = 20.0f
        private const val DEFAULT_ZOOM_SPEED = 0.0f

    }

    private var fileHandle = FILE_PATH.toInternalFile()
    var moveSpeed = DEFAULT_MOVE_SPEED
        private set
    var zoomSpeed = DEFAULT_ZOOM_SPEED
        private set
    var zoomOutConstrainedToThis = ZOOM_OUT_CONSTRAINED_TO_THIS
        private set

    private var leftKey = DEFAULT_LEFT_KEY
    private var rightKey = DEFAULT_RIGHT_KEY
    private var upKey = DEFAULT_UP_KEY
    private var downKey = DEFAULT_DOWN_KEY

    private var zoomInKey = DEFAULT_ZOOM_IN_KEY
    private var zoomOutKey = DEFAULT_ZOOM_OUT_KEY

    private var resetKey = DEFAULT_RESET_KEY
    private var logKey = DEFAULT_LOG_KEY


    init {
        fileHandle = Gdx.files.internal(FILE_PATH)
        if (fileHandle.exists()) {
            load()
        } else {
            log.debug("Using defaults\nfile @ $FILE_PATH\nnot found.")
            setupDefaults()

        }
    }

    private fun setupDefaults() {
        moveSpeed = DEFAULT_MOVE_SPEED
        zoomSpeed = DEFAULT_ZOOM_SPEED

        leftKey = DEFAULT_LEFT_KEY
        rightKey = DEFAULT_RIGHT_KEY
        upKey = DEFAULT_UP_KEY
        downKey = DEFAULT_DOWN_KEY

        zoomInKey = DEFAULT_ZOOM_IN_KEY
        zoomOutKey = DEFAULT_ZOOM_OUT_KEY
        resetKey = DEFAULT_RESET_KEY
        logKey = DEFAULT_LOG_KEY
    }

    fun isLeftPressed() = leftKey.isKeyPressed()

    fun isRightPressed() = rightKey.isKeyPressed()
    fun isUpPressed() = upKey.isKeyPressed()
    fun isDownPressed() = downKey.isKeyPressed()
    fun isZoomInPressed() = zoomInKey.isKeyPressed()
    fun isZoomOutPressed() = zoomOutKey.isKeyPressed()
    fun isResetPressed() = resetKey.isKeyPressed()
    fun isLogPressed() = logKey.isKeyPressed()

    private fun load() {
        try {
            val root =    JsonReader().parse(fileHandle)

            moveSpeed = root.getFloat(MOVE_SPEED, DEFAULT_MOVE_SPEED)
            zoomSpeed = root.getFloat(ZOOM_SPEED, DEFAULT_ZOOM_SPEED)

            leftKey = getInputKeyValue(root, LEFT_KEY, DEFAULT_LEFT_KEY)
            rightKey = getInputKeyValue(root, RIGHT_KEY, DEFAULT_RIGHT_KEY)
            upKey = getInputKeyValue(root, UP_KEY, DEFAULT_UP_KEY)
            downKey = getInputKeyValue(root, DOWN_KEY, DEFAULT_DOWN_KEY)

            zoomInKey = getInputKeyValue(root, ZOOM_IN_KEY, DEFAULT_ZOOM_IN_KEY)
            zoomOutKey = getInputKeyValue(root, ZOOM_OUT_KEY, DEFAULT_ZOOM_OUT_KEY)
            resetKey = getInputKeyValue(root, RESET_KEY, DEFAULT_RESET_KEY)
            logKey = getInputKeyValue(root, LOG_KEY, DEFAULT_LOG_KEY)
        } catch (e: Exception) {
            log.error("Error loading $FILE_PATH using defaults.")
            setupDefaults()
        }
    }

    private fun getInputKeyValue(root: JsonValue, name: String, defaultInputKey: Int ): Int {
        var keyString = root.getString(name, Input.Keys.toString(defaultInputKey))
        return Input.Keys.valueOf(keyString)
    }

    override fun toString(): String {
        return """ 
           ${DebugCameraConfig::class.java.simpleName} 
                moveSpeed= $moveSpeed
                zoomSpeed= $zoomSpeed
                leftKey=   ${Input.Keys.toString(leftKey)}
                rightKey=  ${Input.Keys.toString(rightKey)} 
                upKey=  ${Input.Keys.toString(upKey)} 
                downKey=  ${Input.Keys.toString(downKey)} 
                zoomInKey=  ${Input.Keys.toString(zoomInKey)} 
                zoomOutKey=  ${Input.Keys.toString(zoomOutKey)} 
                resetKey=  ${Input.Keys.toString(resetKey)} 
                logKey=  ${Input.Keys.toString(logKey)} 
                }""".trimIndent()

    }
}
