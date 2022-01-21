package ilyin.core.command

interface CommandResultBuilder<T : CommandResultBuilder<T>> {
    fun plainText(text: String): T
}
