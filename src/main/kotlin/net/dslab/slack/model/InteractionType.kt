package net.dslab.slack.model

sealed interface InteractionType {
    companion object {
        fun fromString(value: String): InteractionType {
            return try {
                KnownInteractionType.valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                UnknownInteractionType(value)
            }
        }
    }
}

data class UnknownInteractionType(val value: String) : InteractionType

enum class KnownInteractionType : InteractionType {
    SHORTCUT
}
