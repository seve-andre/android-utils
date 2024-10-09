package com.mitch.androidutils.utils.outcome

// add compilerOptions -> freeCompilerArgs opt in for ExperimentalContracts
sealed class Outcome<out S, out E> {
    data class Success<out S>(val value: S) : Outcome<S, Nothing>()
    data class Error<out E>(val value: E) : Outcome<Nothing, E>()
}
