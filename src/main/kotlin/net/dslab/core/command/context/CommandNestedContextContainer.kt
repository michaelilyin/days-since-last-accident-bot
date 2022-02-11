package net.dslab.core.command.context

import java.lang.IllegalArgumentException
import kotlin.reflect.KClass

class CommandNestedContextContainer {
    private val map = mutableMapOf<KClass<*>, Any>()

    operator fun <T : Any> set(key: KClass<T>, value: T) {
        map[key] = value
    }

    operator fun <T : Any> contains(key: KClass<T>): Boolean {
        return key in map
    }

    operator fun <T : Any> get(key: KClass<T>): T {
        val value = map[key] ?: throw IllegalArgumentException()

        @Suppress("UNCHECKED_CAST")
        return value as T
    }
}

inline fun <reified T : Any> CommandNestedContextContainer.set(value: T) {
    this[T::class] = value
}

inline fun <reified T : Any> CommandNestedContextContainer.get(): T {
    return this[T::class]
}
