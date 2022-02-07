package net.dslab.slack.api.http

import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import io.restassured.RestAssured
import net.dslab.slack.api.http.filter.verification.SlackRequestVerificationInterceptor
import net.dslab.slack.service.auth.model.InitialTokenRequestResult
import net.dslab.slack.service.installation.SlackInstallationService
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito

@QuarkusTest
@TestHTTPEndpoint(SlackInstallCallbackResource::class)
internal class SlackInstallCallbackResourceTest {

    @InjectMock
    private lateinit var slackInstallationService: SlackInstallationService

    @Test
   internal fun installCallbackOK() {
        val code = "verification-code"
        val expectedResponse = InitialTokenRequestResult("team-id", "bot-user-id")

        BDDMockito.given(slackInstallationService.processInstallCallback(code))
            .willReturn(expectedResponse)

        RestAssured.given()
            .`when`()
            .redirects().follow(false)
            .queryParam("code", code)
            .get("auth")
            .then()
            .statusCode(307)
            .header("Location", Matchers.equalTo("slack://user?team=team-id&id=bot-user-id"))
    }
}
