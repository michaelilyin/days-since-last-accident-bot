package ilyin.core.exception

open class BotException(message: String, cause: Throwable? = null) :
    RuntimeException(message, cause) {
}
