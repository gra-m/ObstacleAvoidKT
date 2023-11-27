//Created by Graham Duthie on 27/11/2023 13:42
package com.madebyfun.obstacleavoid.config

enum class Difficulty(val obstacleSpeed: Float) {

    EASY(GameConfig.EASY_OBSTACLE_SPEED),
    MEDIUM(GameConfig.MEDIUM_OBSTACLE_SPEED),
    HARD(GameConfig.HARD_OBSTACLE_SPEED);

    fun isEasy(difficulty: Difficulty) = difficulty == EASY
    fun isMedium(difficulty: Difficulty) = difficulty == MEDIUM
    fun isHard(difficulty: Difficulty) = difficulty == HARD

}