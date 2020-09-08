package br.felipe.parrot.core.util

import java.io.Serializable

/**
 * A discriminated union that encapsulates successful outcome with a value of type [T]
 * or a failure with an arbitrary [Throwable] exception.
 */
@Suppress("NON__PRIMARY_CONSTRUCTOR_OF__CLASS")
open class ParrotResult<out T> (val value: Any?) : Serializable {

    /**
     * Returns `true` if this instance represents successful outcome.
     * In this case [isFailure] returns `false`.
     */
    val isSuccess: Boolean get() = value !is Failure

    /**
     * Returns `true` if this instance represents failed outcome.
     * In this case [isSuccess] returns `false`.
     */
    val isFailure: Boolean get() = value is Failure

    // value & exception retrieval

    /**
     * Returns the encapsulated value if this instance represents [success][ParrotResult.isSuccess] or `null`
     * if it is [failure][ParrotResult.isFailure].
     *
     */
    fun getOrNull(): T? =
        when {
            isFailure -> null
            else -> value as T
        }

    /**
     * Returns the encapsulated exception if this instance represents [failure][isFailure] or `null`
     * if it is [success][isSuccess].
     *
     */
    fun exceptionOrNull(): Throwable? =
        when (value) {
            is Failure -> value.exception
            else -> null
        }

    /**
     * Returns a string `Success(v)` if this instance represents [success][ParrotResult.isSuccess]
     * where `v` is a string representation of the value or a string `Failure(x)` if
     * it is [failure][isFailure] where `x` is a string representation of the exception.
     */
    override fun toString(): String =
        when (value) {
            is Failure -> value.toString() // "Failure($exception)"
            else -> "Success($value)"
        }

    // companion with constructors

    /**
     * Companion object for [ParrotResult] class that contains its constructor functions
     * [success] and [failure].
     */
    companion object {
        /**
         * Returns an instance that encapsulates the given [value] as successful value.
         */

        fun <T> success(value: T): ParrotResult<T> =
            ParrotResult(value)

        /**
         * Returns an instance that encapsulates the given [exception] as failure.
         */

        fun <T> failure(exception: Throwable): ParrotResult<T> =
            ParrotResult(createFailure(exception))
    }

    class Failure(@JvmField val exception: Throwable) : Serializable {
        override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
        override fun hashCode(): Int = exception.hashCode()
        override fun toString(): String = "Failure($exception)"
    }
}

/**
 * Creates an instance of internal marker [ParrotResult.Failure] class to
 * make sure that this class is not exposed in ABI.
 */
internal fun createFailure(exception: Throwable): Any =
    ParrotResult.Failure(exception)

/**
 * Throws exception if the NewResult is failure. This internal function minimizes
 * d bytecode for [getOrThrow] and makes sure that in the future we can
 * add some exception-augmenting logic here (if needed).
 */
fun ParrotResult<*>.throwOnFailure() {
    if (value is ParrotResult.Failure) throw value.exception
}

// -- extensions ---

/**
 * Returns the encapsulated value if this instance represents [success][ParrotResult.isSuccess] or throws the encapsulated exception
 * if it is [failure][ParrotResult.isFailure].
 *
 * This function is shorthand for `getOrElse { throw it }` (see [getOrElse]).
 */
@Suppress("UNCHECKED_CAST")
fun <T> ParrotResult<T>.getOrThrow(): T {
    throwOnFailure()
    return value as T
}

// transformation

/**
 * Returns the encapsulated NewResult of the given [transform] function applied to encapsulated value
 * if this instance represents [success][ParrotResult.isSuccess] or the
 * original encapsulated exception if it is [failure][ParrotResult.isFailure].
 *
 * Note, that an exception thrown by [transform] function is rethrown by this function.
 * See [mapCatching] for an alternative that encapsulates exceptions.
 */
@Suppress("UNCHECKED_CAST")
fun <R, T> ParrotResult<T>.map(transform: (value: T) -> R): ParrotResult<R> {
    return when {
        isSuccess -> ParrotResult.success(transform(value as T))
        else -> ParrotResult(value)
    }
}

fun <R> ParrotResult<*>.mapFailure(): ParrotResult<R> {
    return this as ParrotResult<R>
}

/**
 * Performs the given [action] on encapsulated exception if this instance represents [failure][ParrotResult.isFailure].
 * Returns the original `NewResult` unchanged.
 */
suspend fun <T> ParrotResult<T>.onFailure(action: suspend (exception: Throwable) -> Unit): ParrotResult<T> {
    exceptionOrNull()?.let { action(it) }
    return this
}

/**
 * Performs the given [action] on encapsulated value if this instance represents [success][ParrotResult.isSuccess].
 * Returns the original `NewResult` unchanged.
 */
@Suppress("UNCHECKED_CAST")
suspend fun <T> ParrotResult<T>.onSuccess(action: suspend (value: T) -> Unit): ParrotResult<T> {
    if (isSuccess) action(value as T)
    return this
}