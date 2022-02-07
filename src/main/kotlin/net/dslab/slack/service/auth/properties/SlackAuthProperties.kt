package net.dslab.slack.service.auth.properties

import io.smallrye.config.ConfigMapping
import java.util.*

@ConfigMapping(prefix = "slack.auth")
interface SlackAuthProperties {

    fun token(): Optional<SlackTokenProperties>

    fun clientId(): String

    fun clientSecret(): String

    fun redirectUri(): String

}
