package com.jyoti.core.util


/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 *
 */
open class Event<out T>(private val content: T) {

    var consumed = false
        private set // Allow external read but not write

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Event<*>

        if (content != other.content) return false
        if (consumed != other.consumed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content?.hashCode() ?: 0
        result = 31 * result + consumed.hashCode()
        return result
    }
}

fun <T> T.toEvent() = Event(this)
