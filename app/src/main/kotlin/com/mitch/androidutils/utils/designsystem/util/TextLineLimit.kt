package com.mitch.androidutils.utils.designsystem.util

sealed interface TextLineLimit {
    data object SingleLine : TextLineLimit
    data class MultiLine(val min: Int = 1, val max: Int = Int.MAX_VALUE) : TextLineLimit {
        init {
            require(min >= 1) { "min must be >= 1, it's $min" }
            require(min <= max) { "min must be <= max, it's min: $min > max: $max" }
        }
    }

    companion object {
        val Default: TextLineLimit = MultiLine()
    }
}
