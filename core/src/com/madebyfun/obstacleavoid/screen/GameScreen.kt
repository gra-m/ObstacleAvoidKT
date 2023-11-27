//Created by Graham Duthie on 27/11/2023 16:06
package com.madebyfun.obstacleavoid.screen

import com.badlogic.gdx.Screen
import com.madebyfun.obstacleavoid.game.GameController
import com.madebyfun.obstacleavoid.game.GameRenderer

class GameScreen: Screen {
    private lateinit var gameController : GameController
    private lateinit var gameRenderer : GameRenderer

    override fun show() {
        gameController = GameController()
        gameRenderer = GameRenderer(gameController)
    }

    override fun render(delta: Float) {
        gameRenderer.render()
        gameController.update(delta)
    }

    override fun resize(width: Int, height: Int) {
        gameRenderer.resize(width, height)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        gameRenderer.dispose()
    }
}