package net.dslab.slack.service.command

import net.dslab.slack.api.http.model.SlashCommandInput

interface SlackCommandExecutionService {
    fun run(slackCommand: SlashCommandInput): String
}
