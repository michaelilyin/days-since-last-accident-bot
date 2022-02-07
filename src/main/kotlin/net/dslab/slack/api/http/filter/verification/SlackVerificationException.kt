package net.dslab.slack.api.http.filter.verification

import net.dslab.common.exception.BotException


class SlackVerificationException(
    val actualSignature: String,
    val expectedSignature: String
) : BotException("Actual signature $actualSignature but $expectedSignature was provided as expected") {
}
