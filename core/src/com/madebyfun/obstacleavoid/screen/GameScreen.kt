//Created by Graham Duthie on 20/11/2023 17:04
package com.madebyfun.obstacleavoid.screen

import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.madebyfun.obstacleavoid.utils.toInternalFile

class GameScreen : Screen {

    private lateinit var batch: SpriteBatch
    private lateinit var img: Texture
    
    override fun show() {
        batch = SpriteBatch()
        img = Texture("badlogic.jpg".toInternalFile())
        //img = Texture(FileHandle("badlogic.jpg"))
    }

    override fun render(delta: Float) {
        //batch.use

    }

    override fun resize(width: Int, height: Int) {
        //Viewport
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
    }
}