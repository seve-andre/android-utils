package com.mitch.androidutils.utils.outcome

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

fun <S, E> Outcome<S, E>.isError(): Boolean {
    contract {
        returns(false) implies (this@isError is Outcome.Success<S>)
        returns(true) implies (this@isError is Outcome.Error<E>)
    }

    return this is Outcome.Error<E>
}

inline fun <S, E> Outcome<S, E>.errorOrElse(default: (S) -> E): E {
    contract { callsInPlace(default, InvocationKind.AT_MOST_ONCE) }
    return when (this) {
        is Outcome.Success -> default(this.value)
        is Outcome.Error -> this.value
    }
}

fun <S, E> Outcome<S, E>.errorOrNull(): E? {
    contract {
        returns(null) implies (this@errorOrNull is Outcome.Success<S>)
        returnsNotNull() implies (this@errorOrNull is Outcome.Error<E>)
    }
    return (this as? Outcome.Error<E>)?.value
}

inline fun <S, E> Outcome<S, E>.onError(action: (error: E) -> Unit): Outcome<S, E> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    return also { if (it.isError()) action(it.value) }
}
