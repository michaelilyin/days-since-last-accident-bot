package net.dslab.common.exception

open class BotException(message: String, cause: Throwable? = null) :
    RuntimeException(message, cause) {
}
