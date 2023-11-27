//Created by Graham Duthie on 27/11/2023 12:38
package com.madebyfun.obstacleavoid.utils
// top level function
fun smoothScores(score: Int, displayScore: Int, delta: Float): Int  {
    return score.coerceAtMost((displayScore + delta * 60).toInt())
}
