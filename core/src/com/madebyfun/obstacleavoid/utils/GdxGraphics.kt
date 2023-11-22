//Created by Graham Duthie on 17/11/2023 09:35
package com.madebyfun.obstacleavoid.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

@JvmOverloads // makes java recognise these as overloaded 
fun clearScreen(color: Color = Color.BLACK) = clearScreen(0.0f, 0.0f, 0.0f, 1.0f)

fun clearScreen(red: Float, green: Float, blue: Float, alpha: Float) {
    Gdx.gl.glClearColor(red, green, blue, alpha)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
}

// In Kotlin it is simple to set up Higher order functions that accept a function as a parameter and carry it out where required
// inline the code below is not duplicated, it is just placed where required when called.
inline fun Batch.use(action: () -> Unit) {
    begin()
    action()
    end()
}

inline fun ShapeRenderer.use(action: () -> Unit) {
    begin(ShapeRenderer.ShapeType.Line)
    action()
    end()
}
