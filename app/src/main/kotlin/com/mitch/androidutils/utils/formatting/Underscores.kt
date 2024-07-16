package com.mitch.androidutils.utils.formatting

import kotlin.math.*

fun Int.withUnderscores(): String {
    if (this.countDigits() <= 3) {
        println("less")
        return this.toString()
    }

    val str = this.toString()
    val length = str.length
    val sb = StringBuilder(length + (length - 1) / 3)

    var count = 0
    for (i in length - 1 downTo 0) {
        sb.append(str[i])
        count++
        if (count % 3 == 0 && i != 0) {
            sb.append('_')
        }
    }

    return sb.reverse().toString()
}

fun Long.withUnderscores(): String {
    if (this.countDigits() <= 3) {
        println("less")
        return this.toString()
    }

    val str = this.toString()
    val length = str.length
    val sb = StringBuilder(length + (length - 1) / 3)

    var count = 0
    for (i in length - 1 downTo 0) {
        sb.append(str[i])
        count++
        if (count % 3 == 0 && i != 0) {
            sb.append('_')
        }
    }

    return sb.reverse().toString()
}

fun Int.countDigits() = when (this) {
    0 -> 1
    else -> log10(abs(toDouble())).toInt() + 1
}

fun Long.countDigits() = when (this) {
    0L -> 1
    else -> log10(abs(toDouble())).toInt() + 1
}

fun Float.withUnderscores(): String {
    val long = this.toLong()
    if (long.countDigits() <= 3) {
        println("less")
        return this.toString()
    }

    println("more")
    return "${this.toLong().withUnderscores()}.${((this % 1)*100).toInt()}"
}
