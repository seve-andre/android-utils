package com.mitch.androidutils.utils.outcome

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

fun <S, E> Outcome<S, E>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is Outcome.Success<S>)
        returns(false) implies (this@isSuccess is Outcome.Error<E>)
    }

    return this is Outcome.Success<S>
}

inline fun <S, E> Outcome<S, E>.successOrElse(default: (E) -> S): S {
    contract { callsInPlace(default, InvocationKind.AT_MOST_ONCE) }
    return when (this) {
        is Outcome.Success -> this.value
        is Outcome.Error -> default(this.value)
    }
}

fun <S, E> Outcome<S, E>.successOrNull(): S? {
    contract {
        returnsNotNull() implies (this@successOrNull is Outcome.Success<S>)
        returns(null) implies (this@successOrNull is Outcome.Error<E>)
    }
    return (this as? Outcome.Success<S>)?.value
}

inline fun <S, E> Outcome<S, E>.onSuccess(action: (success: S) -> Unit): Outcome<S, E> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    return also { if (it.isSuccess()) action(it.value) }
}
