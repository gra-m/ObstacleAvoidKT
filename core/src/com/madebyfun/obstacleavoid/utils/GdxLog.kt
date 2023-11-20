//Created by Graham Duthie on 15/11/2023 16:57 -1h
package com.madebyfun.obstacleavoid.utils

import com.badlogic.gdx.utils.Logger

// Using generics
// fun <T : Any> logger(clazz: Class<T>): Logger = Logger(clazz.simpleName, Logger.DEBUG)
// Using reified parameters
inline fun <reified T : Any> logger(): Logger = Logger(T::class.java.simpleName, Logger.DEBUG)