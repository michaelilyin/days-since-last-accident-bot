package net.dslab.slack.model

sealed interface SlackCallbackType {
    companion object {
        fun fromString(value: String): SlackCallbackType {
            return try {
                KnownSlackCallbackType.valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                UnknownSlackCallbackType(value)
            }
        }
    }
}

data class UnknownSlackCallbackType(val value: String) : SlackCallbackType

enum class KnownSlackCallbackType : SlackCallbackType {
    EVENT_CALLBACK,
    URL_VERIFICATION
}
