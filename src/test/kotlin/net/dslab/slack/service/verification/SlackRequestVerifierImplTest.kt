package net.dslab.slack.service.verification

import io.quarkus.test.junit.QuarkusTest
import net.dslab.slack.api.http.filter.verification.SlackVerificationException
import org.apache.commons.codec.binary.Hex
import org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

@QuarkusTest
internal class SlackRequestVerifierImplTest {

    @Inject
    private lateinit var slackRequestVerifier: SlackRequestVerifier

    @Test
    internal fun verifyOK() {
        val dataToEncode = "data-to-encode".toByteArray()
        val signature = getTestSignature(dataToEncode)

        slackRequestVerifier.verify(dataToEncode, signature)
    }

    @Test
    internal fun verifyFail() {
        val dataToEncode = "data-to-encode".toByteArray()
        val signature = getTestSignature(dataToEncode)
        val badData = "bad-data".toByteArray()

        val e = assertThrows<SlackVerificationException> {
            slackRequestVerifier.verify(badData, signature)
        }
        assertEquals(getTestSignature(badData), e.actualSignature)
        assertEquals(signature, e.expectedSignature)
    }

    private fun getTestSignature(dataToEncode: ByteArray): String {
        val hmacSha256: Mac = Mac.getInstance("HmacSHA256")
        val secret = "test-secret".toByteArray()
        val key = SecretKeySpec(secret, "HmacSHA256")
        hmacSha256.init(key)
        val hashedBytes = hmacSha256.doFinal(dataToEncode)
        return "v0=${Hex.encodeHexString(hashedBytes)}"
    }
}
