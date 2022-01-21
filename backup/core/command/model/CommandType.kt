package ilyin.core.command.model

interface CommandType {
}

data class UnknownCommandType(val value: String): CommandType

enum class KnownCommandType : CommandType {
    ENABLE_TRACKING
}
