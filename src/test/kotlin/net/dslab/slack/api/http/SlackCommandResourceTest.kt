package net.dslab.slack.api.http

import com.slack.api.model.Message
import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.block.SectionBlock
import com.slack.api.model.block.composition.PlainTextObject
import com.slack.api.model.block.composition.TextObject
import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import io.restassured.RestAssured
import net.dslab.slack.api.http.model.SlashCommandInput
import net.dslab.slack.service.command.SlackCommandExecutionService
import net.dslab.slack.service.verification.SlackRequestVerifier
import net.dslab.slackHttpResource
import net.dslab.text
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat

@QuarkusTest
@TestHTTPEndpoint(SlackCommandResource::class)
internal class SlackCommandResourceTest {
    @InjectMock
    private lateinit var slackCommandExecutionService: SlackCommandExecutionService

    @InjectMock
    private lateinit var slackRequestVerifier: SlackRequestVerifier

    @Test
    internal fun enableTrackingOK() {
        val message = Message()
        message.blocks = mutableListOf<LayoutBlock>(SectionBlock.builder()
            .text(PlainTextObject.builder().text("test").build())
            .build()
        )
        BDDMockito.given(slackCommandExecutionService.run(any<SlashCommandInput>()))
            .willReturn(message)

        RestAssured
            .given()
            .header("X-Slack-Signature", "signatire")
            .header("X-Slack-Request-Timestamp", "timestamp")
            .formParam("token", "token-value")
            .formParam("team_id", "team-id-value")
            .formParam("team_domain", "team-domain-value")
            .formParam("channel_id", "channel-id-value")
            .formParam("channel_name", "channel-name-value")
            .formParam("user_id", "user-id-value")
            .formParam("user_name", "user-name-value")
            .formParam("command", "/enable-days-since-counter")
            .formParam("response_url", "https://localhost")
            .formParam("trigger_id", "trigger-id-value")
            .post("slash")
            .then()
            .statusCode(200)

        BDDMockito.verify(slackCommandExecutionService)
            .run(argThat<SlashCommandInput> { command == "/enable-days-since-counter" })
    }

    @Test
    internal fun addKeywordShortcutOK() {
        RestAssured.given()
            .header("X-Slack-Signature", "signatire")
            .header("X-Slack-Request-Timestamp", "timestamp")
            .formParam("payload", slackHttpResource("add_keyword_shortcut.json").text())
            .post("interactive")
            .then()
            .statusCode(200)

    }
}
