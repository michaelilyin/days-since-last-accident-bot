package net.dslab.core.message.builder

interface MessageBuilder<T : MessageBuilder<T>> {
    fun paragraph(configurer: ParagraphBuilder.() -> Unit)
}
