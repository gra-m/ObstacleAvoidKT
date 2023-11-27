//Created by Graham Duthie on 20/11/2023 19:23
package com.madebyfun.obstacleavoid.config

object GameConfig {

    const val WIDTH = 480 // pix desktop only
    const val HEIGHT = 800
    const val HUD_WIDTH = 480f // wu   just 1:1 ppu normal for user interface
    const val HUD_HEIGHT = 800f
    const val WORLD_WIDTH = 6.0f     // wu
    const val WORLD_HEIGHT = 10.0f
    const val WORLD_CENTER_X = WORLD_WIDTH/2
    const val WORLD_CENTER_Y = WORLD_HEIGHT/2
    const val OBSTACLE_AND_PLAYER_RADIUS = 0.4f
    const val PLAYER_START_LIVES = 100
    const val OBSTACLE_SPAWN_TIME = 0.25f
}