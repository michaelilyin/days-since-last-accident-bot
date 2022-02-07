package net.dslab.core.command

interface CommandResultBuilder<T : CommandResultBuilder<T>> {
    fun plainText(text: String): T
}
