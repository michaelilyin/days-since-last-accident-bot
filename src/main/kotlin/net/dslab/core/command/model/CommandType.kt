package net.dslab.core.command.model

interface CommandType {
}

data class UnknownCommandType(val value: String): CommandType

enum class KnownCommandType : CommandType {
    ENABLE_TRACKING,
    ADD_KEYWORD
}
