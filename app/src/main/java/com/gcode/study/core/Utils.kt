package com.gcode.study.core

object Utils {
    fun Boolean?.orTrue() = this ?: true

    fun <T> Boolean.ifTrue(action: () -> T): T? = if (this) action() else null

    fun Int.IsThree() = this == 3

    fun Int.isUpToThree(block: (() -> Unit)? = null): Boolean? {
        return if (this in 1..3) {
            block?.invoke()
            true
        } else null
    }
}