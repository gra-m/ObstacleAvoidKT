//Created by Graham Duthie on 20/11/2023 19:19
package com.madebyfun.obstacleavoid

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.madebyfun.obstacleavoid.screen.GameScreen

// Extending Game vs ApplicationAdapter == many vs single screen
class ObstacleAvoidKTGame: Game() {
    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        setScreen(GameScreen())

    }
}