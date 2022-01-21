package ilyin.slack.configuration.properties

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "slack.verification")
interface SlackVerificationProperties {
    fun secret(): String
}
