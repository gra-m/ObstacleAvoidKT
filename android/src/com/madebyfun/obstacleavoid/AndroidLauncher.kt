//Created by Graham Duthie on 20/11/2023 16:53
package com.madebyfun.obstacleavoid
import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

class AndroidLauncher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(ObstacleAvoidKTGame(), AndroidApplicationConfiguration())
    }
}