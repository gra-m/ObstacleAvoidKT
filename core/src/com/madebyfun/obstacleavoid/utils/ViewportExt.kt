//Created by Graham Duthie on 21/11/2023 18:54
package com.madebyfun.obstacleavoid.utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport
// extension function adding a function to Viewport with defineable cell size defaulting at 1
@JvmOverloads
fun Viewport.drawGrid(renderer: ShapeRenderer, cellSize: Int = 1) {
    val oldColor = renderer.color.cpy()
    val doubleWorldWidth = worldWidth * 2 // these are calling Viewport getters! Mad!
    val doubleWorldHeight = worldHeight * 2

    renderer.use {
        renderer.color = Color.WHITE
        var drawVerticalLines = true
        var drawHorizontalLines = true
        var x = -doubleWorldWidth
        var y = -doubleWorldHeight
        renderer.x(0f, 0f, 2f )
        while(drawVerticalLines) {
            renderer.line(x, -doubleWorldHeight, x, doubleWorldHeight)
            x += cellSize
            drawVerticalLines = x < doubleWorldWidth
        }

        while(drawHorizontalLines) {
            renderer.line(-doubleWorldWidth , y, doubleWorldWidth, y)
            y += cellSize
            drawHorizontalLines = y < doubleWorldHeight
        }
        //0f/0f
        renderer.color = Color.RED
        renderer.line(0f, -doubleWorldHeight, 0f, doubleWorldHeight)
        renderer.line(-doubleWorldWidth, 0f, doubleWorldWidth, 0f)
        //World bounds
        renderer.color = Color.GREEN
        renderer.line(0f, 0f, 0f, worldHeight)
        renderer.line(worldWidth, 0f, worldWidth, worldHeight)




    }


   renderer.color = oldColor
    
}