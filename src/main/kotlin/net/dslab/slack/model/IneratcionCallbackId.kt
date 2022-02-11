package net.dslab.slack.model

import net.dslab.core.command.model.CommandType

sealed interface InteractionCallbackId : CommandType {
    companion object {
        fun fromString(value: String): InteractionCallbackId {
            return try {
                KnownInteractionCallbackId.valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                UnknownInteractionCallbackId(value)
            }
        }
    }
}

data class UnknownInteractionCallbackId(val value: String) : InteractionCallbackId

enum class KnownInteractionCallbackId : InteractionCallbackId {
    ADD_KEYWORD
}
