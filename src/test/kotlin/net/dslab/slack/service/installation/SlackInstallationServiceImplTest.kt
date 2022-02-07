package net.dslab.slack.service.installation

import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.junit.mockito.InjectMock
import net.dslab.slack.service.auth.SlackInitialAuthenticator
import net.dslab.slack.service.auth.model.InitialTokenRequestResult
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import javax.inject.Inject

@QuarkusTest
internal class SlackInstallationServiceImplTest {

    @InjectMock
    private lateinit var slackInitialAuthService: SlackInitialAuthenticator

    @Inject
    private lateinit var slackInstallationService: SlackInstallationService

    @Test
    internal fun processInstallCallbackOK() {
        val code = "code-value"
        val expectedResult = InitialTokenRequestResult("team-id", "user-id")
        BDDMockito.given(slackInitialAuthService.performInitialTokenRequest(code))
            .willReturn(expectedResult)

        val res = slackInstallationService.processInstallCallback(code)

        assertEquals(expectedResult, res)
    }
}
