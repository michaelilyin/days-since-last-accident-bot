package ilyin.core.command.model

data class CommandExecutionInput(
    val type: CommandType,
    val teamId: String,
    val chatId: String
)
