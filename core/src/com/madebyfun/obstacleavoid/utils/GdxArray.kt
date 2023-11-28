//Created by Graham Duthie on 28/11/2023 10:17
package com.madebyfun.obstacleavoid.utils
// did this in different way but included these for future reference
typealias GdxArray<T> = com.badlogic.gdx.utils.Array<T>

fun <T> GdxArray<T>?.isEmpty() = this == null || this.size == 0

fun <T> GdxArray<T>?.isNotEmpty() = !isEmpty()