package ilyin.slack.inbound

import ilyin.slack.service.installation.SlackInstallationService
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriBuilder

@Path("slack")
@ApplicationScoped
class SlackInstallCallbackResource(
    private val slackInstallationService: SlackInstallationService
) {
    @GET
    @Path("auth")
    fun authenticate(@QueryParam("code") code: String): Response {
        val result = slackInstallationService.processInstallCallback(code)

        val redirectUri = UriBuilder
            .fromUri("slack://user")
            .queryParam("team", result.teamId)
            .queryParam("id", result.botUserId)
            .build()

        return Response.temporaryRedirect(redirectUri)
            .build()
    }
}
