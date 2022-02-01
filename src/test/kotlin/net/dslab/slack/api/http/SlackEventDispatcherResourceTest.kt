package net.dslab.slack.api.http

import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import net.dslab.slackHttpResource
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

@QuarkusTest
@TestHTTPEndpoint(SlackEventDispatcherResource::class)
class SlackEventDispatcherResourceTest {
    @Test
    internal fun urlVerificationOK() {
        given()
            .`when`()
            .contentType(ContentType.JSON)
            .body(slackHttpResource("url_verification_callback.json"))
            .post("event")
            .then()
            .statusCode(200)
            .body("challenge", Matchers.equalTo("challenge-value"))
    }

    @Test
    internal fun eventCallbackOK() {
        given()
            .`when`()
            .contentType(ContentType.JSON)
            .body(slackHttpResource("event_callback.json"))
            .post("event")
            .then()
            .statusCode(200)
    }
}
