package net.dslab.slack.integration

import com.slack.api.Slack
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces

@ApplicationScoped
class SlackConfiguration {
    @Produces
    @ApplicationScoped
    fun slack(): Slack {
        return Slack.getInstance()
    }
}
