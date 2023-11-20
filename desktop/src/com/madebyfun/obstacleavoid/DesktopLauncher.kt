//Created by Graham Duthie on 20/11/2023 16:49
package com.madebyfun.obstacleavoid

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

fun main() {
    val config = Lwjgl3ApplicationConfiguration()
    config.setForegroundFPS(60)
    config.setTitle("obstacle-avoid.kt")
    Lwjgl3Application(ObstacleAvoidKTGame(), config)
}