package net.dslab.slack.service.command

import com.slack.api.model.Message
import net.dslab.slack.api.http.model.SlackInteractiveCommandInput
import net.dslab.slack.api.http.model.SlashCommandInput
import net.dslab.slack.api.http.model.SlashOutput

interface SlackCommandExecutionService {
    fun run(slackCommand: SlashCommandInput): SlashOutput
    fun run(slackInteractiveCommand: SlackInteractiveCommandInput): Message
}
